package com.ner.admin.controller.system;


import com.ner.admin.annotation.SysLog;
import com.ner.admin.base.BaseController;
import com.ner.admin.entity.Site;
import com.ner.admin.entity.UploadInfo;
import com.ner.admin.service.SiteService;
import com.ner.admin.service.UploadInfoService;
import com.ner.admin.service.UploadService;
import com.ner.admin.util.RestResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author jll
 * @since 2017-12-30
 */
@Controller
@RequestMapping("/admin/system/site")
public class SiteController extends BaseController {
    @Autowired
    private UploadInfoService uploadInfoService;
    @Autowired
    @Qualifier("qiniuService")
    private UploadService qiniuService;
    @Autowired
    @Qualifier("ossService")
    private UploadService ossService;
    @Autowired
    private SiteService siteService;

    @RequiresPermissions("sys:site:list")
    @GetMapping("show")
    public String show(Model model) {
        Site site = siteService.getCurrentSite();
        model.addAttribute("site", site);
        return "admin/system/site/show";
    }

    @RequiresPermissions("sys:site:list")
    @GetMapping("oss")
    public String oss(Model model) {
        UploadInfo uploadInfo = uploadInfoService.getOneInfo();
        model.addAttribute("info", uploadInfo);
        return "admin/system/site/oss";
    }

    @RequiresPermissions("sys:site:editOss")
    @PostMapping("editOss")
    @ResponseBody
    @Transactional
    public RestResponse editOss(UploadInfo uploadInfo, HttpServletRequest httpServletRequest) {
        Site site = (Site) httpServletRequest.getAttribute("site");
        if (site == null) {
            return RestResponse.failure("加载信息错误");
        }
        if (null == uploadInfo.getId() || 0 == uploadInfo.getId()) {
            return RestResponse.failure("ID不能为空");
        }
        if (!ossService.testAccess(uploadInfo)) {
            return RestResponse.failure("阿里云上传测试检测失败");
        }
        UploadInfo oldInfo = uploadInfoService.selectById(uploadInfo.getId());
        uploadInfo.setQiniuDir(oldInfo.getQiniuDir());
        uploadInfoService.updateInfo(uploadInfo);
        site.setFileUploadType("oss");
        siteService.updateSite(site);
        return RestResponse.success();
    }

    @RequiresPermissions("sys:site:list")
    @GetMapping("qiniu")
    public String qiniu(Model model) {
        UploadInfo uploadInfo = uploadInfoService.getOneInfo();
        model.addAttribute("info", uploadInfo);
        return "admin/system/site/qiniu";
    }

    @RequiresPermissions("sys:site:editQiniu")
    @PostMapping("editQiniu")
    @ResponseBody
    @Transactional
    public RestResponse editQiniu(UploadInfo uploadInfo, HttpServletRequest httpServletRequest) {
        Site site = (Site) httpServletRequest.getAttribute("site");
        if (site == null) {
            return RestResponse.failure("加载信息错误");
        }
        if (null == uploadInfo.getId() || 0 == uploadInfo.getId()) {
            uploadInfo.setId(1l);
        }
        if (!qiniuService.testAccess(uploadInfo)) {
            return RestResponse.failure("七牛上传测试检测失败");
        }
        UploadInfo oldInfo = uploadInfoService.selectById(uploadInfo.getId());
        uploadInfoService.updateInfo(uploadInfo);
        site.setFileUploadType("qiniu");
        siteService.updateSite(site);
        return RestResponse.success();
    }

    @RequiresPermissions("sys:site:edit")
    @PostMapping("edit")
    @ResponseBody
    @SysLog("保存网站基本数据")
    public RestResponse edit(Site site, HttpServletRequest httpServletRequest) {
        if (site.getId() == null) {
            return RestResponse.failure("ID不能为空");
        }
        if (site.getId() != 1) {
            return RestResponse.failure("ID不正确");
        }
        if (StringUtils.isBlank(site.getName())) {
            return RestResponse.failure("站点名称不能为空");
        }
        if (StringUtils.isNotBlank(site.getRemarks())) {
            site.setRemarks(site.getRemarks().replace("\"", "\'"));
        }
        /*if ("oss".equals(site.getFileUploadType())) {
            UploadInfo uploadInfo = uploadInfoService.getOneInfo();
            if (StringUtils.isBlank(uploadInfo.getOssKeySecret()) ||
                    StringUtils.isBlank(uploadInfo.getOssKeyId()) ||
                    StringUtils.isBlank(uploadInfo.getOssEndpoint())) {
                return RestResponse.failure("阿里云存储信息不能为空");
            }
        }
        if ("qiniu".equals(site.getFileUploadType())) {
            UploadInfo uploadInfo = uploadInfoService.getOneInfo();
            if (uploadInfo != null
                    && StringUtils.isBlank(uploadInfo.getQiniuAccessKey()) ||
                    StringUtils.isBlank(uploadInfo.getQiniuSecretKey()) ||
                    StringUtils.isBlank(uploadInfo.getQiniuBucketName())) {
                return RestResponse.failure("七牛云存储信息不能为空");
            }
        }*/
        siteService.updateSite(site);
        return RestResponse.success();
    }
}
