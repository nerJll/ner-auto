package com.mysiteforme.admin.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.mysiteforme.admin.base.DataEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * <p>
 * 租户表
 * </p>
 *
 * @author jll
 * @since 2019-07-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("base_tenant")
public class BaseTenant extends DataEntity<BaseTenant> {

    private static final long serialVersionUID = 1L;

    /**
     * 名称
     */
    private String name;
    /**
     * 到期时间
     */
    @TableField("limit_date")
    private Date limitDate;
    /**
     * 管理员id
     */
    @TableField("manager_id")
    private Long managerId;
}
