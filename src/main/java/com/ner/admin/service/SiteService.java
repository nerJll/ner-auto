package com.ner.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ner.admin.entity.Site;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jll
 * @since 2017-12-30
 */
public interface SiteService extends IService<Site> {

    Site getCurrentSite();

    void updateSite(Site site);
	
}
