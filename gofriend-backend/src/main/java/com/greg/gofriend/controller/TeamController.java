package com.greg.gofriend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greg.gofriend.common.BaseResponse;
import com.greg.gofriend.common.DeleteRequest;
import com.greg.gofriend.common.ErrorCode;
import com.greg.gofriend.common.ResultUtils;
import com.greg.gofriend.exception.BusinessException;
import com.greg.gofriend.model.Team;
import com.greg.gofriend.model.User;
import com.greg.gofriend.model.UserTeam;
import com.greg.gofriend.model.dto.TeamDto;
import com.greg.gofriend.model.request.TeamJoinRequest;
import com.greg.gofriend.model.request.TeamQuitRequest;
import com.greg.gofriend.model.request.TeamUpdateRequest;
import com.greg.gofriend.model.vo.TeamUserVo;
import com.greg.gofriend.service.TeamService;
import com.greg.gofriend.service.UserService;
import com.greg.gofriend.service.UserTeamService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Resource
    private TeamService teamService;
    @Resource
    private UserService userService;
    @Resource
    private UserTeamService userTeamService;

    @PostMapping("/add")
    public BaseResponse<Long> addTeam(@RequestBody Team team, HttpServletRequest request) {
        if (team == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        long teamId = teamService.addTeam(team, loginUser);
        return ResultUtils.success(teamId);
    }

    @PatchMapping("/update")
    public BaseResponse<Boolean> updateTeam(@RequestBody TeamUpdateRequest teamUpdateRequest, HttpServletRequest request) {
        if (teamUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.updateTeam(teamUpdateRequest, loginUser);
        if (!result) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "更新失败");
        }
        return ResultUtils.success(true);
    }

    @GetMapping("/get")
    public BaseResponse<Team> getTeam(long teamId) {
        if (teamId <= 0){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = teamService.getById(teamId);
        if (team == null){
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return ResultUtils.success(team);
    }

    @GetMapping("/list")
    public BaseResponse<List<TeamUserVo>> listTeam(TeamDto teamDto, HttpServletRequest request) {
        if (teamDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean isAdmin = userService.isAdmin(request);
        // 1、查询队伍列表
        List<TeamUserVo> teamList = teamService.listTeams(teamDto, isAdmin);
        if (CollectionUtils.isNotEmpty(teamList)) {
            final List<Long> teamIdList = teamList.stream().map(TeamUserVo::getId).collect(Collectors.toList());
            // 2、判断当前用户是否已加入队伍
            QueryWrapper<UserTeam> userTeamQueryWrapper = new QueryWrapper<>();
            try {
                User loginUser = userService.getLoginUser(request);
                userTeamQueryWrapper.eq("user_id", loginUser.getId());
                userTeamQueryWrapper.in("team_id", teamIdList);
                List<UserTeam> userTeamList = userTeamService.list(userTeamQueryWrapper);
                // 已加入的队伍 id 集合
                Set<Long> hasJoinTeamIdSet = userTeamList.stream().map(UserTeam::getTeamId).collect(Collectors.toSet());
                teamList.forEach(team -> {
                    boolean hasJoin = hasJoinTeamIdSet.contains(team.getId());
                    team.setHasJoin(hasJoin);
                });
            } catch (Exception e) {
            }
            /*// 3、查询已加入队伍的人数
            QueryWrapper<UserTeam> userTeamJoinQueryWrapper = new QueryWrapper<>();
            userTeamJoinQueryWrapper.in("team_id", teamIdList);
            List<UserTeam> userTeamList = userTeamService.list(userTeamJoinQueryWrapper);
            // 队伍 id => 加入这个队伍的用户列表
            Map<Long, List<UserTeam>> teamIdUserTeamList = userTeamList.stream().collect(Collectors.groupingBy(UserTeam::getTeamId));
            teamList.forEach(team -> team.setHasJoinNum(teamIdUserTeamList.getOrDefault(team.getId(), new ArrayList<>()).size()));*/
        }
        return ResultUtils.success(teamList);
    }

    @GetMapping("/list/page")
    public BaseResponse<Page<Team>> listTeamByPage(TeamDto teamDto) {
        if (teamDto == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Team team = new Team();
        BeanUtils.copyProperties(teamDto, team);
        QueryWrapper<Team> queryWrapper = new QueryWrapper<>(team);
        Page<Team> page = new Page<>(teamDto.getPageNum(), teamDto.getPageSize());
        Page<Team> result = teamService.page(page, queryWrapper);
        return ResultUtils.success(result);
    }

    @PostMapping("/join")
    public BaseResponse<Boolean> joinTeam(@RequestBody TeamJoinRequest teamJoinRequest, HttpServletRequest request){
        if (teamJoinRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.joinTeam(teamJoinRequest, loginUser);
        return ResultUtils.success(result);
    }

    @PostMapping("/quit")
    public BaseResponse<Boolean> quitTeam(@RequestBody TeamQuitRequest teamQuitRequest, HttpServletRequest request){
        if (teamQuitRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.quitTeam(teamQuitRequest, loginUser);
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteTeam(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null){
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = deleteRequest.getId();
        User loginUser = userService.getLoginUser(request);
        boolean result = teamService.deleteTeam(id, loginUser);
        return ResultUtils.success(result);
    }

    @GetMapping("/list/my/create")
    public BaseResponse<List<TeamUserVo>> listMyCreateTeams(TeamDto teamDto, HttpServletRequest request) {
        if (teamDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        teamDto.setUserId(loginUser.getId());
        List<TeamUserVo> teamList = teamService.listTeams(teamDto, true);
        return ResultUtils.success(teamList);
    }

    /**
     * 获取我加入的队伍
     *
     * @param teamDto
     * @param request
     * @return
     */
    @GetMapping("/list/my/join")
    public BaseResponse<List<TeamUserVo>> listMyJoinTeams(TeamDto teamDto, HttpServletRequest request) {
        if (teamDto == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User loginUser = userService.getLoginUser(request);
        QueryWrapper<UserTeam> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", loginUser.getId());
        List<UserTeam> userTeamList = userTeamService.list(queryWrapper);
        if (CollectionUtils.isEmpty(userTeamList)){
            return ResultUtils.success(new ArrayList<>());
        }
        // 取出不重复的队伍 id
        // teamId userId
        // 1, 2
        // 1, 3
        // 2, 3
        // result
        // 1 => 2, 3
        // 2 => 3
        Map<Long, List<UserTeam>> listMap = userTeamList.stream()
                .collect(Collectors.groupingBy(UserTeam::getTeamId));
        List<Long> idList = new ArrayList<>(listMap.keySet());
        teamDto.setIdList(idList);
        List<TeamUserVo> teamUserVoList = teamService.listTeams(teamDto, true);
        teamUserVoList.forEach(teamUserVo -> teamUserVo.setHasJoin(true));
        return ResultUtils.success(teamUserVoList);
    }
}
