package com.ner.admin.util;

import com.ner.admin.entity.Menu;

import java.util.Comparator;

/**
 * Created by jll on 2017/12/3.
 * todo:菜单排序
 */
public class MyCompare implements Comparator<Menu> {
    @Override
    public int compare(Menu a, Menu b) {
        if(a.getParentIds().contains(b.getParentIds())){
            return -1;
        }else{
            return 1;
        }
    }
}
