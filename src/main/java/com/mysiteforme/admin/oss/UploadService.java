package com.mysiteforme.admin.oss;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.StringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.UUID;

/**
 * @desc 文件上传
 * @date 2019/6/30
 */
@Service
public class UploadService {
    @Autowired
    private QiniuConfiguration qiniuConfigration;
    /**
     * 定义七牛云上传的相关策略
     **/
    private StringMap putPolicy;

    /**
     * 以文件的形式上传
     */
    public String uploadFile(File file) throws Exception {
        Response response = qiniuConfigration.uploadManager().put(file, null, getUploadToken());
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = qiniuConfigration.uploadManager().put(file, null, getUploadToken());
            retry++;
        }
        // 解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String fileUrl = qiniuConfigration.getPath() + "/" + putRet.key;
        return fileUrl;
    }

    /**
     * 以流的形式上传
     */
    public String uploadFile(InputStream inputStream) throws Exception {
        Response response = qiniuConfigration.uploadManager().put(inputStream, null, getUploadToken(), null, null);
        int retry = 0;
        while (response.needRetry() && retry < 3) {
            response = qiniuConfigration.uploadManager().put(inputStream, null, getUploadToken(), null, null);
            retry++;
        }
        // 解析上传成功的结果
        DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
        String fileUrl = qiniuConfigration.getPath() + "/" + putRet.key;
        return fileUrl;
    }

    /**
     * 删除七牛云上的相关文件
     */
    public Response delete(String key) throws QiniuException {
        Response response = qiniuConfigration.bucketManager().delete(qiniuConfigration.getBucket(), key);
        int retry = 0;
        while (response.needRetry() && retry++ < 3) {
            response = qiniuConfigration.bucketManager().delete(qiniuConfigration.getBucket(), key);
        }
        return response;
    }

    /**
     * 上传后操作
     *
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception {
        this.putPolicy = new StringMap();
        putPolicy.put("returnBody", "{\"key\":\"$(key)\",\"hash\":\"$(etag)\",\"bucket\":\"$(bucket)\",\"width\":$(imageInfo.width), \"height\":${imageInfo.height}}");
        //自定义名称
        putPolicy.put("saveKey", UUID.randomUUID().timestamp());
    }

    /**
     * 获取上传凭证
     */
    public String getUploadToken() {
        return qiniuConfigration.auth().uploadToken(qiniuConfigration.getBucket(), null, 3600, putPolicy);
    }
}
