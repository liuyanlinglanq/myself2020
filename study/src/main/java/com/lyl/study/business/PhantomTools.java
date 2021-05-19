package com.lyl.study.business;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 网页转图片处理类，使用外部CMD
 */
@Slf4j
public class PhantomTools {
    private static final Logger log = LogManager.getLogger(PhantomTools.class);

    private static final String _tempPath = "/Users/liuyanling/Downloads/swt/img/";
    private String basePath;
    private static final String _shellCommand1 = "/bin/phantomjs ";
    private static final String _shellCommand2 = "/examples/rasterize.js ";
    private static final String _shellCommand3 = "/examples/render_multi_url.js ";
    private static final String _shellCommand4 = "/examples/responsive-screenshot.js ";

    private String _file;
    private String _size;

    /**
     * 构造截图类
     *
     * @param hash     用于临时文件的目录唯一化
     * @param basePath phantomJs所在路径
     */
    public PhantomTools(Long hash, String basePath) {
        _file = _tempPath + hash + ".png";
        this.basePath = basePath;
    }

    /**
     * 构造截图类
     *
     * @param hash     用于临时文件的目录唯一化
     * @param size     图片的大小，如800px*600px（此时高度会裁切），或800px（此时 高度最少=宽度*9/16，高度不裁切）
     * @param basePath phantomJs所在路径
     */
    public PhantomTools(Long hash, String size, String basePath) {
        _file = _tempPath + hash + ".png";
        if (size != null)
            _size = " " + size;
        this.basePath = basePath;
    }

    /**
     * 将目标网页转为图片字节流
     *
     * @param url 目标网页地址
     * @return 字节流
     */
    public byte[] getByteImg(String url) throws IOException {
        BufferedInputStream in = null;
        ByteArrayOutputStream out = null;
        File file = null;
        byte[] ret = null;
        try {
            String cmd = basePath + _shellCommand1 + basePath + _shellCommand2 + url + " " + _file + (_size != null ?
                    _size : "");
//            String cmd = basePath + _shellCommand1 + basePath + _shellCommand3 + url + " " + _file + (_size != null ?_size : "");
//            String cmd = basePath + _shellCommand1 + basePath + _shellCommand4 + url + " png true";

            if (exeCmd(cmd)) {
                file = new File(_file);
                if (file.exists()) {
                    out = new ByteArrayOutputStream();
                    byte[] b = new byte[5120];
                    in = new BufferedInputStream(new FileInputStream(file));
                    int n;
                    while ((n = in.read(b, 0, 5120)) != -1) {
                        out.write(b, 0, n);
                    }
//                    file.delete();
//                    ret = out.toByteArray();
                }
            } else {
                ret = new byte[]{};
            }
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                log.error(e);
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                log.error(e);
            }
            if (file != null && file.exists()) {
//                file.delete();
            }
        }
        return ret;
    }

    /**
     * 执行CMD命令
     */
    private static boolean exeCmd(String commandStr) {
        System.out.println(commandStr);
        BufferedReader br = null;
        try {
            Process p = Runtime.getRuntime().exec(commandStr);
            if (p.waitFor() != 0 && p.exitValue() == 1) {
                return false;
            }
        } catch (Exception e) {
            log.error(e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e) {
                    log.error(e);
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        PhantomTools tools = new PhantomTools(System.currentTimeMillis(), "1920px", "/usr/local/Caskroom" +
                "/phantomjs/2.1.1/phantomjs-2.1.1-macosx");
        String url = "https://mp.weixin.qq.com/s?__biz=MzI4OTc5NzQ1Mw==&mid=100016981&idx=2&sn=e03cce00ee8d533fd5f27da3a3b5669d&chksm=6c2b27775b5cae61f8dbf4bdfe88fffac4082e6aa9325fb6dfaacab80827acf0fe6043a93b08#rd";
        byte[] im = new byte[0];
        try {
            im = tools.getByteImg(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        ByteArrayInputStream inputStream = new ByteArrayInputStream(im);
//
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        //创建一个Buffer字符串
//        byte[] buffer = new byte[1024];
//        //每次读取的字符串长度，如果为-1，代表全部读取完毕
//        int len = 0;
//        //使用一个输入流从buffer里把数据读取出来
//        while ((len = inputStream.read(buffer)) != -1) {
//            //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
//            outStream.write(buffer, 0, len);
//        }
//        //关闭输入流
//        inputStream.close();
//        //把outStream里的数据写入内存
//
//        //得到图片的二进制数据，以二进制封装得到数据，具有通用性
//        byte[] data = outStream.toByteArray();
//        //new一个文件对象用来保存图片，默认保存当前工程根目录
//        File imageFile = new File("/Users/liuyanling/Downloads/swt/img/" + System.currentTimeMillis() + ".jpg");
//        //创建输出流
//        FileOutputStream fileOutStream = new FileOutputStream(imageFile);
//        //写入数据
//        fileOutStream.write(data);
    }
} 