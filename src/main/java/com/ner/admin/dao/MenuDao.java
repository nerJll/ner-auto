package com.ner.admin.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ner.admin.entity.Menu;
import com.ner.admin.entity.VO.ShowMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
  *  Mapper 接口
 * </p>
 *
 * @author jll
 * @since 2017-10-31
 */

public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> showAllMenusList(Map map);

    List<Menu> getMenus(Map map);

    List<ShowMenu> selectShowMenuByUser(Map<String,Object> map);
}