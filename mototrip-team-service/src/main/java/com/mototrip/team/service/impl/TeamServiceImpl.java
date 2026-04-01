package com.mototrip.team.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mototrip.common.exception.*;
import com.mototrip.common.response.PageResult;
import com.mototrip.team.dto.request.CreateTeamRequest;
import com.mototrip.team.dto.request.UpdateTeamRequest;
import com.mototrip.team.entity.Team;
import com.mototrip.team.entity.TeamMember;
import com.mototrip.team.mapper.TeamMapper;
import com.mototrip.team.mapper.TeamMemberMapper;
import com.mototrip.team.service.TeamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamServiceImpl implements TeamService {

    private final TeamMapper teamMapper;
    private final TeamMemberMapper teamMemberMapper;

    @Override
    public PageResult<Team> findAll(int page, int pageSize) {
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Team::getStatus, "open").orderByDesc(Team::getCreatedAt);
        Page<Team> pageResult = teamMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public PageResult<Team> findMyTeams(Long userId, int page, int pageSize) {
        // 查找用户作为成员的所有车队ID
        List<TeamMember> memberships = teamMemberMapper.selectList(
                new LambdaQueryWrapper<TeamMember>()
                        .eq(TeamMember::getUserId, userId)
                        .eq(TeamMember::getStatus, "approved")
        );
        if (memberships.isEmpty()) {
            return PageResult.of(List.of(), 0, page, pageSize);
        }
        List<Long> teamIds = memberships.stream().map(TeamMember::getTeamId).toList();
        LambdaQueryWrapper<Team> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Team::getId, teamIds).orderByDesc(Team::getCreatedAt);
        Page<Team> pageResult = teamMapper.selectPage(new Page<>(page, pageSize), wrapper);
        return PageResult.of(pageResult.getRecords(), pageResult.getTotal(), page, pageSize);
    }

    @Override
    public Team findById(Long id) {
        Team team = teamMapper.selectById(id);
        if (team == null) throw new NotFoundException("车队不存在");
        return team;
    }

    @Override
    public List<TeamMember> getMembers(Long teamId) {
        return teamMemberMapper.selectList(
                new LambdaQueryWrapper<TeamMember>()
                        .eq(TeamMember::getTeamId, teamId)
                        .eq(TeamMember::getStatus, "approved")
                        .orderByAsc(TeamMember::getCreatedAt)
        );
    }

    @Override
    @Transactional
    public Team create(Long userId, CreateTeamRequest request) {
        Team team = new Team();
        team.setName(request.getName());
        team.setDescription(request.getDescription());
        team.setDestination(request.getDestination());
        team.setStartTime(request.getStartTime());
        team.setEndTime(request.getEndTime());
        team.setMaxMembers(request.getMaxMembers() != null ? request.getMaxMembers() : 10);
        team.setStatus("open");
        team.setCoverImage(request.getCoverImage());
        team.setLatitude(request.getLatitude());
        team.setLongitude(request.getLongitude());
        team.setCreatorId(userId);
        teamMapper.insert(team);

        // 创建者自动成为队长
        TeamMember leader = new TeamMember();
        leader.setTeamId(team.getId());
        leader.setUserId(userId);
        leader.setRole("leader");
        leader.setStatus("approved");
        teamMemberMapper.insert(leader);

        return team;
    }

    @Override
    @Transactional
    public void joinTeam(Long userId, Long teamId) {
        Team team = teamMapper.selectById(teamId);
        if (team == null) throw new NotFoundException("车队不存在");
        if (!"open".equals(team.getStatus())) throw new BadRequestException("车队当前不可加入");

        // 检查是否已经是成员
        Long count = teamMemberMapper.selectCount(
                new LambdaQueryWrapper<TeamMember>()
                        .eq(TeamMember::getTeamId, teamId)
                        .eq(TeamMember::getUserId, userId)
        );
        if (count > 0) throw new ConflictException("已经是车队成员");

        TeamMember member = new TeamMember();
        member.setTeamId(teamId);
        member.setUserId(userId);
        member.setRole("member");
        member.setStatus("pending");
        teamMemberMapper.insert(member);
    }

    @Override
    @Transactional
    public void leaveTeam(Long userId, Long teamId) {
        Team team = teamMapper.selectById(teamId);
        if (team == null) throw new NotFoundException("车队不存在");

        TeamMember member = teamMemberMapper.selectOne(
                new LambdaQueryWrapper<TeamMember>()
                        .eq(TeamMember::getTeamId, teamId)
                        .eq(TeamMember::getUserId, userId)
        );
        if (member == null) throw new NotFoundException("不是车队成员");

        // Leader 不能离开车队
        if ("leader".equals(member.getRole())) {
            throw new BadRequestException("队长不能离开车队");
        }

        teamMemberMapper.deleteById(member.getId());
    }

    @Override
    @Transactional
    public void approveMember(Long leaderId, Long memberId) {
        TeamMember member = teamMemberMapper.selectById(memberId);
        if (member == null) throw new NotFoundException("成员记录不存在");

        Team team = teamMapper.selectById(member.getTeamId());
        if (team == null) throw new NotFoundException("车队不存在");

        // 验证操作者是队长
        if (!team.getCreatorId().equals(leaderId)) {
            throw new ForbiddenException("只有队长可以审批成员");
        }

        member.setStatus("approved");
        teamMemberMapper.updateById(member);

        // 检查是否满员
        Long approvedCount = teamMemberMapper.selectCount(
                new LambdaQueryWrapper<TeamMember>()
                        .eq(TeamMember::getTeamId, team.getId())
                        .eq(TeamMember::getStatus, "approved")
        );
        if (approvedCount >= team.getMaxMembers()) {
            team.setStatus("full");
            teamMapper.updateById(team);
        }
    }

    @Override
    public Team update(Long userId, Long id, UpdateTeamRequest request) {
        Team team = teamMapper.selectById(id);
        if (team == null) throw new NotFoundException("车队不存在");
        if (!team.getCreatorId().equals(userId)) throw new ForbiddenException("无权修改此车队");
        if (request.getName() != null) team.setName(request.getName());
        if (request.getDescription() != null) team.setDescription(request.getDescription());
        if (request.getDestination() != null) team.setDestination(request.getDestination());
        if (request.getStartTime() != null) team.setStartTime(request.getStartTime());
        if (request.getEndTime() != null) team.setEndTime(request.getEndTime());
        if (request.getMaxMembers() != null) team.setMaxMembers(request.getMaxMembers());
        if (request.getCoverImage() != null) team.setCoverImage(request.getCoverImage());
        if (request.getLatitude() != null) team.setLatitude(request.getLatitude());
        if (request.getLongitude() != null) team.setLongitude(request.getLongitude());
        if (request.getStatus() != null) team.setStatus(request.getStatus());
        teamMapper.updateById(team);
        return team;
    }

    @Override
    public void delete(Long userId, Long id) {
        Team team = teamMapper.selectById(id);
        if (team == null) throw new NotFoundException("车队不存在");
        if (!team.getCreatorId().equals(userId)) throw new ForbiddenException("无权删除此车队");
        teamMapper.deleteById(id);
    }
}
