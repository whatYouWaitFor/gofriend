package com.greg.gofriend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greg.gofriend.model.UserTeam;
import com.greg.gofriend.service.UserTeamService;
import com.greg.gofriend.mapper.UserTeamMapper;
import org.springframework.stereotype.Service;

/**
* @author dgj
* @description 针对表【user_team(用户队伍关系)】的数据库操作Service实现
* @createDate 2026-04-28 10:22:25
*/
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
    implements UserTeamService{

}




