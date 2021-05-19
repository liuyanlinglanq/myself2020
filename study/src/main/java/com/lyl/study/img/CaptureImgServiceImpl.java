package com.lyl.study.img;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by HuangYanfei on 2018/11/8.
 */
@Service
public class CaptureImgServiceImpl implements CaptureImgService {

//    @Value("${temp.path}")
    private String pdfPath = "E:/pdfpath/";

    @Autowired
    private ResourceUtil resourceUtil;

    @Autowired
    private ScreenCaptureService phantomSer;

    @Override
    /**
     * 截屏
     **/
    public String captureImg(HttpServletRequest re, String url, String size) throws IOException {
        String img = "";
        String plugin = resourceUtil.getFilePath("files/sysplugins/phantomjs");
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("windows")) {
            plugin = resourceUtil.getFilePath("files/sysplugins/phantomjs.exe");
        }
        String js = resourceUtil.getFilePath("files/sysplugins/rasterize.js");

        File file = new File(this.pdfPath);
        if (!file.exists()) {
            file.mkdirs();
        }

//        img = this.pdfPath + DateUtils.format(new Date(), "yyyyMMddHHmmss") + System.nanoTime() + ".png";

        File pluginFile = new File(plugin);
        if (!pluginFile.canExecute()) {
            pluginFile.setExecutable(true);
        }

        File jsFile = new File(js);

        if (!phantomSer.exec(plugin, jsFile.getAbsolutePath(), url, img, size)) {
            return null;
        }

        return img;
    }
} 