package com.mysiteforme.admin.controller.system;

import com.mysiteforme.admin.base.BaseController;
import com.mysiteforme.admin.entity.User;
import com.mysiteforme.admin.service.UserService;
import com.xiaoleilu.hutool.date.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.mysiteforme.admin.entity.BaseTenant;
import com.mysiteforme.admin.service.BaseTenantService;
import com.baomidou.mybatisplus.plugins.Page;
import com.mysiteforme.admin.util.LayerData;
import com.mysiteforme.admin.util.RestResponse;
import com.mysiteforme.admin.annotation.SysLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.WebUtils;

import javax.servlet.ServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 租户表  前端控制器
 * </p>
 *
 * @author jll
 * @since 2019-07-11
 */
@Controller
@RequestMapping("/admin/baseTenant")
public class BaseTenantController extends BaseController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTenantController.class);

    private final BaseTenantService baseTenantService;
    private final UserService userService;

    @Autowired
    public BaseTenantController(BaseTenantService baseTenantService, UserService userService) {
        this.baseTenantService = baseTenantService;
        this.userService = userService;
    }

    @GetMapping("list")
    @SysLog("跳转租户表列表")
    public String list() {
        return "/admin/system/baseTenant/list";
    }

    @RequiresPermissions("sys:tenant:list")
    @PostMapping("list")
    @ResponseBody
    public LayerData<BaseTenant> list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                      ServletRequest request) {
        Map map = WebUtils.getParametersStartingWith(request, "s_");
        LayerData<BaseTenant> layerData = new LayerData<>();
        EntityWrapper<BaseTenant> wrapper = new EntityWrapper<>();
        wrapper.eq("del_flag", false);
        if (!map.isEmpty()) {
            String name = (String) map.get("name");
            if (StringUtils.isNotBlank(name)) {
                wrapper.like("name", name);
            } else {
                map.remove("name");
            }
        }
        Page<BaseTenant> pageData = baseTenantService.selectPage(new Page<>(page, limit), wrapper);
        layerData.setData(pageData.getRecords());
        layerData.setCount(pageData.getTotal());
        return layerData;
    }

    @GetMapping("add")
    @SysLog("跳转新增租户表页面")
    public String add(Model model) {
        model.addAttribute("ms", userService.getIdNameMap(null));
        return "/admin/system/baseTenant/add";
    }

    @RequiresPermissions("sys:tenant:add")
    @PostMapping("add")
    @SysLog("保存新增租户表数据")
    @ResponseBody
    public RestResponse add(BaseTenant baseTenant) {
        if (StringUtils.isBlank(baseTenant.getName())) {
            return RestResponse.failure("名称不能为空");
        }
        baseTenantService.insert(baseTenant);
        return RestResponse.success();
    }

    @GetMapping("edit")
    @SysLog("跳转编辑租户表页面")
    public String edit(Long id, Model model) {
        BaseTenant baseTenant = baseTenantService.selectById(id);
        model.addAttribute("ms", userService.getIdNameMap(id));
        model.addAttribute("baseTenant", baseTenant);
        return "/admin/system/baseTenant/edit";
    }

    @RequiresPermissions("sys:tenant:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存编辑租户表数据")
    public RestResponse edit(BaseTenant baseTenant) {
        if (null == baseTenant.getId() || 0 == baseTenant.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (StringUtils.isBlank(baseTenant.getName())) {
            return RestResponse.failure("名称不能为空");
        }
        return RestResponse.successDate(baseTenantService.update(baseTenant));
    }

    @RequiresPermissions("sys:tenant:delete")
    @PostMapping("delete")
    @ResponseBody
    @SysLog("删除租户表数据")
    public RestResponse delete(@RequestParam(value = "id", required = false) Long id) {
        if (null == id || 0 == id) {
            return RestResponse.failure("ID不能为空");
        }
        BaseTenant baseTenant = baseTenantService.selectById(id);
        baseTenant.setDelFlag(true);
        baseTenantService.updateById(baseTenant);
        return RestResponse.success();
    }

}