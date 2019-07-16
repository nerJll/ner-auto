package com.ner.admin.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ner.admin.dao.RescourceDao;
import com.ner.admin.entity.Rescource;
import com.ner.admin.service.RescourceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 系统资源 服务实现类
 * </p>
 *
 * @author jll
 * @since 2018-01-14
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
public class RescourceServiceImpl extends ServiceImpl<RescourceDao, Rescource> implements RescourceService {

    @Override
    public int getCountByHash(String hash) {
        EntityWrapper<Rescource> wrapper = new EntityWrapper<>();
        wrapper.eq("hash", hash);
        return selectCount(wrapper);
    }

    @Override
    public Rescource getRescourceByHash(String hash) {
        EntityWrapper<Rescource> wrapper = new EntityWrapper<>();
        wrapper.eq("hash", hash);
        return selectOne(wrapper);
    }

    @Override
    public List<Rescource> getRescourceByHashs(List<String> hashs) {
        EntityWrapper<Rescource> wrapper = new EntityWrapper<>();
        wrapper.in("hash", hashs);
        return selectList(wrapper);
    }
}
