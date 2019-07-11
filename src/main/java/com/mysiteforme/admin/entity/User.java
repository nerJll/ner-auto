package com.mysiteforme.admin.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.FieldStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * <p>
 * <p>
 * </p>
 *
 * @author jll
 * @since 2017-10-31
 */
@TableName("sys_user")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class User extends DataEntity<User> {

    private static final long serialVersionUID = 1L;

    /**
     * 租户id
     */
    @TableField("tenant_id")
    private Long tenantId;
    /**
     * 登录名
     */
    @TableField("login_name")
    private String loginName;
    /**
     * 昵称
     */
    @TableField(value = "nick_name", strategy = FieldStrategy.IGNORED)
    private String nickName;
    /**
     * 密码
     */
    @JSONField(serialize = false)
    private String password;
    /**
     * shiro加密盐
     */
    @JSONField(serialize = false)
    private String salt;
    /**
     * 手机号码
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String tel;
    /**
     * 邮箱地址
     */
    @TableField(strategy = FieldStrategy.IGNORED)
    private String email;

    /**
     * 账户是否锁定
     */
    private Boolean locked;

    @TableField(strategy = FieldStrategy.IGNORED)
    private String icon;

    @TableField(exist = false)
    private Set<Role> roleLists = Sets.newHashSet();

    @TableField(exist = false)
    private Set<Menu> menus = Sets.newHashSet();
}
