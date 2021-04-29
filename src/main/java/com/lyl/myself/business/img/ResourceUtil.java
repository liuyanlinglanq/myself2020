package com.lyl.myself.business.img;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

/**
 * Created by HuangYanfei on 2018/9/29.
 */
@Component
public class ResourceUtil {
    @Autowired
    private ResourceLoader resourceLoader = new FileSystemResourceLoader();

    /**
     * 根据文件名字获取路径
     *
     * @param fileNameAndPath
     * @return
     */
    public String getFilePath(String fileNameAndPath) throws IOException {
        File file = resourceLoader.getResource("file:"+ fileNameAndPath).getFile();
        if(!file.exists()) {
            file = resourceLoader.getResource("classpath:"+ fileNameAndPath).getFile();
        }
        return file.getAbsolutePath();
    }
} 