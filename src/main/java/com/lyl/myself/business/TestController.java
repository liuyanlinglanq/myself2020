package com.lyl.myself.business;

import chrriis.dj.nativeswing.swtimpl.NativeInterface;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.*;
import java.awt.*;

@RequestMapping(value = "/app")
@Configuration
public class TestController {
//    public static boolean printUrlScreen2jpg(final String file, final String url, final int width, final int height) {
//        NativeInterface.open();
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                String withResult = "var width = " + width + ";var height = " + height + ";return width +':' + height;";
//                if (width == 0 || height == 0)
//                    withResult = DjNativeSwingUtil.getScreenWidthHeight();
//
//                JFrame frame = new JFrame("网页截图");
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                // 加载指定页面，最大保存为640x480的截图
//                frame.getContentPane().add(new DjNativeSwingUtil(file, url, withResult), BorderLayout.CENTER);
//                frame.setSize(640, 800);
//                // 仅初始化，但不显示
//                frame.invalidate();
//
//                frame.pack();
//                frame.setVisible(false);
//            }
//        });
//        NativeInterface.runEventPump();
//        return true;
//    }
//
//    @RequestMapping(value = "/htmltest", produces = "application/json;charset=UTF-8")
//    @ResponseBody
//    public String htmlGenerator() throws Exception {
//        TestController.printUrlScreen2jpg("G:/网页截图.png", "G:/index.html", 1000, 1300);
//        return null;
//    }



}