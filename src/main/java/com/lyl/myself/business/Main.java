package com.lyl.myself.business;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;

public class Main {

    public static void main(String[] args) {
        try {
            // 参数
            String token = "607554a875d30";
            String url = URLEncoder.encode("http://mp.weixin.qq.com/s?__biz=MzI4OTc5NzQ1Mw==&mid=100016981&idx=2&sn=e03cce00ee8d533fd5f27da3a3b5669d&chksm=6c2b27775b5cae61f8dbf4bdfe88fffac4082e6aa9325fb6dfaacab80827acf0fe6043a93b08#rd");
            int width = 1280;
            int height = 800;
            int full_page = 1;

            // 构造URL
            String query = "https://www.screenshotmaster.com/api/v1/screenshot";
            query += String.format("?token=%s&url=%s&width=%d&height=%d&full_page=%s&device=%s",
                    token, url, width, height, full_page, "mobile");
            URL apiUrl = new URL(query);

            // 调用API并将结果保存进screenshot.png
            InputStream inputStream = apiUrl.openStream();
//            OutputStream outputStream = new FileOutputStream("./screenshot.png");
//            inputStream.transferTo(outputStream);
//            inputStream.close();
//            outputStream.close();

            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inputStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inputStream.close();
            //把outStream里的数据写入内存

            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = outStream.toByteArray();
            //new一个文件对象用来保存图片，默认保存当前工程根目录
            File imageFile = new File("/Users/liuyanling/Downloads/swt/img/" + System.currentTimeMillis() + ".jpg");
            //创建输出流
            FileOutputStream fileOutStream = new FileOutputStream(imageFile);
            //写入数据
            fileOutStream.write(data);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}