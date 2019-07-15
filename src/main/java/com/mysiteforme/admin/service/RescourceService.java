package com.mysiteforme.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.mysiteforme.admin.entity.Rescource;

import java.util.List;

/**
 * <p>
 * 系统资源 服务类
 * </p>
 *
 * @author jll
 * @since 2018-01-14
 */
public interface RescourceService extends IService<Rescource> {

    int getCountByHash(String hash);

    Rescource getRescourceByHash(String hash);

    List<Rescource> getRescourceByHashs(List<String> hashs);
}
