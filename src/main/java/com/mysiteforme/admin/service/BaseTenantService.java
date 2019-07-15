package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.BaseTenant;
/**
 * <p>
 * 租户表 服务类
 * </p>
 *
 * @author jll
 * @since 2019-07-11
 */
public interface BaseTenantService extends IService<BaseTenant> {

    Long update(BaseTenant tenant);
}
