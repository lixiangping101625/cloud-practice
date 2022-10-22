package com.hlkj.user.pojo;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Lixiangping
 * @createTime 2022年10月17日 16:42
 * @decription: 用户
 */
@Table(name = "user")
@Data
public class User implements Serializable {

    @Id
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "mobile")
    private String mobile;
}