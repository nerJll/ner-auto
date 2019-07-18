package com.ner.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ner.admin.entity.Log;

import java.util.List;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author jll
 * @since 2018-01-13
 */
public interface LogService extends IService<Log> {

    List<Integer> selectSelfMonthData();

}