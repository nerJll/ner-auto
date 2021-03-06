package com.ner.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ner.admin.entity.Log;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 系统日志 Mapper 接口
 * </p>
 *
 * @author jll
 * @since 2018-01-14
 */
public interface LogDao extends BaseMapper<Log> {

    List<Map> selectSelfMonthData();
}
