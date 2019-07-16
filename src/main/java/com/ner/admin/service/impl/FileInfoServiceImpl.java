package com.ner.admin.service.impl;

import com.ner.admin.entity.Rescource;
import com.ner.admin.oss.UploadService;
import com.ner.admin.service.IFileInfoService;
import com.ner.admin.service.RescourceService;
import com.ner.admin.util.RestResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 文件信息表 服务实现类
 * </p>
 *
 * @author jll
 * @since 2019-03-19
 */
@Service
public class FileInfoServiceImpl implements IFileInfoService {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private RescourceService rescourceService;

    @Override
    public RestResponse check(String md5, String name) throws Exception {
        Rescource myFile = rescourceService.getRescourceByHash(md5);
        if (myFile != null) {
            return RestResponse.successDate(md5);
        }
        return RestResponse.failure("");
    }

    @Override
    public Rescource upload(MultipartFile file, String md5) throws Exception {
        if (file == null) {
            return null;
        }
        if (StringUtils.isEmpty(md5)) {
            md5 = DigestUtils.md5Hex(file.getInputStream());
        }
        Rescource myFile = rescourceService.getRescourceByHash(md5);
        if (myFile != null) {
            return myFile;
        }
        //上传文件
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.lastIndexOf("."));
        String url = uploadService.uploadFile(file.getInputStream());
        Rescource rescource = new Rescource();

        rescource.setFileName(filename.replace(suffix, ""));
        rescource.setFileSize(new java.text.DecimalFormat("#.##").format(file.getSize() / 1024) + "kb");
        rescource.setHash(md5);
        rescource.setFileType(suffix);
        rescource.setWebUrl(url);
        rescource.setSource("qiniu");
        if (filename.replace(suffix, "").length() > 50) {
            rescource.setFileName(filename.substring(0, 50));
        }
        rescourceService.insert(rescource);
        return rescource;
    }

    @Override
    public RestResponse checkIsUploaded(String fileIds) throws Exception {
        String[] arr = fileIds.split(",");
        List<Rescource> myFiles = rescourceService.getRescourceByHashs(Arrays.asList(arr));
        Map result = new HashMap();
        if (!CollectionUtils.isEmpty(myFiles)) {
            for (Rescource item : myFiles) {
                if (fileIds.contains(item.getHash())) {
                    result.put(item.getHash(), item);
                }
            }
            return RestResponse.successDate(result);
        }
        return RestResponse.failure("");
    }
}
