package com.mysiteforme.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mchange.lang.LongUtils;
import com.mysiteforme.admin.entity.BaseTenant;
import com.mysiteforme.admin.dao.BaseTenantDao;
import com.mysiteforme.admin.entity.User;
import com.mysiteforme.admin.exception.BizException;
import com.mysiteforme.admin.service.BaseTenantService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.mysiteforme.admin.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 租户表 服务实现类
 * </p>
 *
 * @author jll
 * @since 2019-07-11
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BaseTenantServiceImpl extends ServiceImpl<BaseTenantDao, BaseTenant> implements BaseTenantService {
    @Autowired
    private UserService userService;

    /**
     * 更新租户信息
     */
    @Transactional(rollbackFor = Exception.class)
    public Long update(BaseTenant tenant) {
        if (null == tenant.getId()) throw new BizException("参数不全");
        BaseTenant oldTenant = selectById(tenant.getId());
        if (oldTenant == null) throw new BizException("租户不存在或以被删除");
        updateById(tenant);
        //更新管理员
        if (null != tenant.getManagerId()) {
            User newUser = userService.selectById(tenant.getManagerId());
            if (newUser != null) {
                newUser.setTenantId(tenant.getId());
                userService.updateById(newUser);
            }
        }
        if (null != oldTenant.getManagerId()) {
            User oldUser = userService.selectById(oldTenant.getManagerId());
            if (oldUser != null) {
                oldUser.setTenantId(null);
                userService.updateAllColumnById(oldUser);
            }
        }
        return tenant.getId();
    }

}
