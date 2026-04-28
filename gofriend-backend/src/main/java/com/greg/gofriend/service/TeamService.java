package com.greg.gofriend.service;

import com.greg.gofriend.model.Team;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greg.gofriend.model.User;
import com.greg.gofriend.model.request.TeamUpdateRequest;

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
}
