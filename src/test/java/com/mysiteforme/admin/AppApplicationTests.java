package com.ner.admin;

import com.alibaba.fastjson.JSONObject;
import com.ner.admin.dao.MenuDao;
import com.ner.admin.entity.Menu;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class AppApplicationTests {

    @Autowired
    private MenuDao menuDao;

    @Test
    public void contextLoads() {
        List<Menu> list = menuDao.getMenus(null);
        log.info(JSONObject.toJSONString(list));
    }

}
