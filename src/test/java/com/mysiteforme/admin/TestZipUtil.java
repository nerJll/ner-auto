package com.mysiteforme.admin;

import com.xiaoleilu.hutool.util.ZipUtil;
import org.junit.Test;

/**
 * Created by jll on 2018/1/14.
 * todo:
 */
public class TestZipUtil {

    @Test
    public void testZip(){
        String srcPat = "D://mysite";
        String zipPath = "D://test.zip";
        ZipUtil.zip(srcPat,zipPath);
    }
}
