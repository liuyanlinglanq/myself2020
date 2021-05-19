package com.lyl.study.img;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by HuangYanfei on 2018/11/8.
 */
public interface CaptureImgService {

    String captureImg(HttpServletRequest re, String url, String size) throws IOException;
} 