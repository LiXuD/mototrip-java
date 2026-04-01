package com.mototrip.team.service;

import com.mototrip.common.response.PageResult;
import com.mototrip.team.dto.request.CreateTeamRequest;
import com.mototrip.team.dto.request.UpdateTeamRequest;
import com.mototrip.team.entity.Team;
import com.mototrip.team.entity.TeamMember;

import java.util.List;

public interface TeamService {
    PageResult<Team> findAll(int page, int pageSize);
    PageResult<Team> findMyTeams(Long userId, int page, int pageSize);
    Team findById(Long id);
    List<TeamMember> getMembers(Long teamId);
    Team create(Long userId, CreateTeamRequest request);
    void joinTeam(Long userId, Long teamId);
    void leaveTeam(Long userId, Long teamId);
    void approveMember(Long leaderId, Long memberId);
    Team update(Long userId, Long id, UpdateTeamRequest request);
    void delete(Long userId, Long id);
}
