package com.mototrip.team.controller;

import com.mototrip.common.context.UserContext;
import com.mototrip.common.response.PageResult;
import com.mototrip.common.response.Result;
import com.mototrip.team.dto.request.CreateTeamRequest;
import com.mototrip.team.dto.request.UpdateTeamRequest;
import com.mototrip.team.entity.Team;
import com.mototrip.team.entity.TeamMember;
import com.mototrip.team.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "车队管理", description = "骑行车队 CRUD 和成员管理")
@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamController {
    private final TeamService teamService;

    @Operation(summary = "获取车队列表")
    @GetMapping
    public Result<PageResult<Team>> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(teamService.findAll(page, pageSize));
    }

    @Operation(summary = "获取我的车队列表")
    @GetMapping("/my")
    public Result<PageResult<Team>> findMyTeams(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.success(teamService.findMyTeams(UserContext.getUserId(), page, pageSize));
    }

    @Operation(summary = "获取车队详情")
    @GetMapping("/{id}")
    public Result<Team> findById(@PathVariable Long id) {
        return Result.success(teamService.findById(id));
    }

    @Operation(summary = "获取车队成员列表")
    @GetMapping("/{id}/members")
    public Result<List<TeamMember>> getMembers(@PathVariable Long id) {
        return Result.success(teamService.getMembers(id));
    }

    @Operation(summary = "创建车队")
    @PostMapping
    public Result<Team> create(@Valid @RequestBody CreateTeamRequest request) {
        return Result.success(teamService.create(UserContext.getUserId(), request));
    }

    @Operation(summary = "加入车队")
    @PostMapping("/{id}/join")
    public Result<Void> joinTeam(@PathVariable Long id) {
        teamService.joinTeam(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "离开车队")
    @PostMapping("/{id}/leave")
    public Result<Void> leaveTeam(@PathVariable Long id) {
        teamService.leaveTeam(UserContext.getUserId(), id);
        return Result.success();
    }

    @Operation(summary = "审批成员加入")
    @PostMapping("/members/{memberId}/approve")
    public Result<Void> approveMember(@PathVariable Long memberId) {
        teamService.approveMember(UserContext.getUserId(), memberId);
        return Result.success();
    }

    @Operation(summary = "更新车队信息")
    @PutMapping("/{id}")
    public Result<Team> update(@PathVariable Long id, @Valid @RequestBody UpdateTeamRequest request) {
        return Result.success(teamService.update(UserContext.getUserId(), id, request));
    }

    @Operation(summary = "解散车队")
    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        teamService.delete(UserContext.getUserId(), id);
        return Result.success();
    }
}
