package com.lyl.study.business;

import chrriis.dj.nativeswing.swtimpl.NativeComponent;
import chrriis.dj.nativeswing.swtimpl.components.JWebBrowser;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserAdapter;
import chrriis.dj.nativeswing.swtimpl.components.WebBrowserEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 功能：根据URL能截取整个网页的缩略图（保存为一个图片）
 * @author
 *
 */
public class ScreenshotUtils extends JPanel {  
    /** 
     *  
     */  
    private static final long serialVersionUID = 1L;  
    // 行分隔符  
    final static public String LS = System.getProperty("line.separator", "\n");  
    // 文件分割符  
    final static public String FS = System.getProperty("file.separator", "\\");  
    //以javascript脚本获得网页全屏后大小  
    final static StringBuffer jsDimension;  
      
    static {  
        jsDimension = new StringBuffer();  
        jsDimension.append("var width = 0;").append(LS);  
        jsDimension.append("var height = 0;").append(LS);  
        jsDimension.append("if(document.documentElement) {").append(LS);  
        jsDimension.append(  
                        "  width = Math.max(width, document.documentElement.scrollWidth);")  
                .append(LS);  
        jsDimension.append(  
                        "  height = Math.max(height, document.documentElement.scrollHeight);")  
                .append(LS);  
        jsDimension.append("}").append(LS);  
        jsDimension.append("if(self.innerWidth) {").append(LS);  
        jsDimension.append("  width = Math.max(width, self.innerWidth);")  
                .append(LS);  
        jsDimension.append("  height = Math.max(height, self.innerHeight);")  
                .append(LS);  
        jsDimension.append("}").append(LS);  
        jsDimension.append("if(document.body.scrollWidth) {").append(LS);  
        jsDimension.append(  
                "  width = Math.max(width, document.body.scrollWidth);")  
                .append(LS);  
        jsDimension.append(  
                "  height = Math.max(height, document.body.scrollHeight);")  
                .append(LS);  
        jsDimension.append("}").append(LS);  
        jsDimension.append("return width + ':' + height;");  
    }  
  //DJNativeSwing组件请于http://djproject.sourceforge.net/main/index.html下载  
    public ScreenshotUtils(final String url, final int maxWidth, final int maxHeight) {  
        super(new BorderLayout());  
        JPanel webBrowserPanel = new JPanel(new BorderLayout());  
        final String fileName = System.currentTimeMillis() + ".jpg";  
        final JWebBrowser webBrowser = new JWebBrowser(null);
        webBrowser.setBarsVisible(false);  
        webBrowser.navigate(url);  
        webBrowserPanel.add(webBrowser, BorderLayout.CENTER);  
        add(webBrowserPanel, BorderLayout.CENTER);  
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 4, 4));  
        webBrowser.addWebBrowserListener(new WebBrowserAdapter() {
            // 监听加载进度  
            public void loadingProgressChanged(WebBrowserEvent e) {
                // 当加载完毕时  
                if (e.getWebBrowser().getLoadingProgress() == 100) {  
                    String result = (String) webBrowser  
                            .executeJavascriptWithResult(jsDimension.toString());  
                    int index = result == null ? -1 : result.indexOf(":");  
                    NativeComponent nativeComponent = webBrowser
                            .getNativeComponent();  
                    Dimension originalSize = nativeComponent.getSize();  
                    Dimension imageSize = new Dimension(Integer.parseInt(result  
                            .substring(0, index)), Integer.parseInt(result  
                            .substring(index + 1)));  
                    imageSize.width = Math.max(originalSize.width,  
                            imageSize.width + 50);  
                    imageSize.height = Math.max(originalSize.height,  
                            imageSize.height + 50);  
                    nativeComponent.setSize(imageSize);  
                    BufferedImage image = new BufferedImage(imageSize.width,
                            imageSize.height, BufferedImage.TYPE_INT_RGB);  
                    nativeComponent.paintComponent(image);  
                    nativeComponent.setSize(originalSize);  
                    // 当网页超出目标大小时  
                    if (imageSize.width > maxWidth  
                            || imageSize.height > maxHeight) {  
                        //截图部分图形  
                        image = image.getSubimage(0, 0, maxWidth, maxHeight);  
                        /*此部分为使用缩略图 
                        int width = image.getWidth(), height = image 
                            .getHeight(); 
                         AffineTransform tx = new AffineTransform(); 
                        tx.scale((double) maxWidth / width, (double) maxHeight 
                                / height); 
                        AffineTransformOp op = new AffineTransformOp(tx, 
                                AffineTransformOp.TYPE_NEAREST_NEIGHBOR); 
                        //缩小 
                        image = op.filter(image, null);*/  
                    }  
                    try {  
                        // 输出图像  
                        ImageIO.write(image, "jpg", new File(fileName));
                    } catch (IOException ex) {
                        ex.printStackTrace();  
                    }  
                    // 退出操作  
                    System.exit(0);  
                }  
            }  
        }  
        );  
        add(panel, BorderLayout.SOUTH);
    }  



}  