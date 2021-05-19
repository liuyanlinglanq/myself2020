package com.lyl.study.business;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
/**
 * @author pibigstar
 * @desc 网页截图工具类
 **/
public class CutPictureUtil {
    public static void main(String[] args) throws IOException, URISyntaxException, AWTException {
        // 控制浏览器打开网页，仅适用于JdK1.6及以上版本
        Desktop.getDesktop().browse(new URL("https://mp.weixin.qq.com/s?__biz=MzI4OTc5NzQ1Mw==&mid=100016981&idx=2&sn=e03cce00ee8d533fd5f27da3a3b5669d&chksm=6c2b27775b5cae61f8dbf4bdfe88fffac4082e6aa9325fb6dfaacab80827acf0fe6043a93b08#rd").toURI());
        Robot robot = new Robot();
        // 延迟一秒
        robot.delay(1000);
        // 获取屏幕宽度和高度
        Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
        int width = (int) d.getWidth();
        int height = (int) d.getHeight();
        // 最大化浏览器
        robot.keyRelease(KeyEvent.VK_F11);
        robot.delay(1000);
        // 对屏幕进行截图
        Image image = robot.createScreenCapture(new Rectangle(0, 0, width, height));
        // 通过图形绘制工具类将截图保存
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = img.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        // 保存图片
        ImageIO.write(img, "jpg", new File("/Users/liuyanling/Downloads/swt/img/"+System.currentTimeMillis()+".jpg"));
        System.out.println("done!");
    }
}