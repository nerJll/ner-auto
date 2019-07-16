package com.ner.admin.service;

import com.ner.admin.entity.Rescource;
import com.ner.admin.util.RestResponse;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 文件信息表 服务类
 * </p>
 *
 * @author jll
 * @since 2019-03-19
 */
public interface IFileInfoService {
    RestResponse checkIsUploaded(String fileIds) throws Exception;

    RestResponse check(String md5, String name) throws Exception;

    Rescource upload(MultipartFile file, String md5) throws Exception;
}
