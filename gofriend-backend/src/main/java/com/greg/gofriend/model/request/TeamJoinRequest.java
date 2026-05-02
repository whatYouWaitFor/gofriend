package com.greg.gofriend.model.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamJoinRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    private Long teamId;

    /**
     * 密码
     */
    private String password;
}
