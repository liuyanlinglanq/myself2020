package com.lyl.myself.business.img;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

/**
 * Created by HuangYanfei on 2018/11/8.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CaptureImgServiceImplTest {

    @Autowired
    CaptureImgService captureImgService;
    @Test
    public void test(){
        try {
            captureImgService.captureImg(null, "https://www.baidu.com/" , "800px*800px");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} 