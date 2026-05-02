package com.greg.gofriend.service;

import com.greg.gofriend.common.DeleteRequest;
import com.greg.gofriend.model.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greg.gofriend.model.User;
import com.greg.gofriend.model.dto.TeamDto;
import com.greg.gofriend.model.request.TeamJoinRequest;
import com.greg.gofriend.model.request.TeamQuitRequest;
import com.greg.gofriend.model.request.TeamUpdateRequest;
import com.greg.gofriend.model.vo.TeamUserVo;

import java.util.List;

/**
* @author dgj
* @description 针对表【team(队伍)】的数据库操作Service
* @createDate 2026-04-28 10:16:28
*/
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 更新队伍
     * @param teamUpdateRequest
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateRequest teamUpdateRequest, User loginUser);

    /**
     * 加入队伍
     * @param teamJoinRequest
     * @param loginUser
     * @return
     */
    boolean joinTeam(TeamJoinRequest teamJoinRequest, User loginUser);

    /**
     * 退出队伍
     * @param teamQuitRequest
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitRequest teamQuitRequest, User loginUser);

    /**
     * 删除队伍
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);

    /**
     * 获取队伍列表
     * @param teamDto
     * @param isAdmin
     * @return
     */
    List<TeamUserVo> listTeams(TeamDto teamDto, boolean isAdmin);
}
