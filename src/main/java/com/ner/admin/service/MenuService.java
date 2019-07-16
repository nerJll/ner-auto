package com.ner.admin.service;

import com.baomidou.mybatisplus.service.IService;
import com.ner.admin.entity.Menu;
import com.ner.admin.entity.VO.ShowMenu;
import com.ner.admin.entity.VO.ZtreeVO;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author jll
 * @since 2017-10-31
 */
public interface MenuService extends IService<Menu> {

    List<Menu> selectAllMenus(Map<String,Object> map);

    List<ZtreeVO> showTreeMenus();

    List<ShowMenu> getShowMenuByUser(Long id);

    void saveOrUpdateMenu(Menu menu);

    int getCountByPermission(String permission);

    int getCountByName(String name);

}
