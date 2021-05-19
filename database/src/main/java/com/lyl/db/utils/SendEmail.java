package com.lyl.db.utils;

import com.lyl.db.domain.DemoData;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.springframework.util.CollectionUtils;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Slf4j
public class SendEmail {
    public static boolean sendEmail(String to, ByteArrayOutputStream baos) {
        // 发件人电子邮箱
        String from = "**@qq.com";
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器 ->QQ 邮件服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("**@qq.com", "**"); //发件人邮件用户名、授权码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));
            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            // Set Subject: 头部头字段
            message.setSubject("This is the Subject Line!");

//           message.setText(buildContent());

//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setTo(new String[]{to});
////            helper.setCc("抄送人邮箱");
//            helper.setFrom(from);
//            helper.setSubject("This is the Subject Line!");
//            helper.setText(buildContent(), true);



            /*添加附件*/
            Multipart multipart = new MimeMultipart();
            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(buildContent(), "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            if (baos != null) {
                MimeBodyPart fileBody = new MimeBodyPart();
                DataSource source = new ByteArrayDataSource(baos.toByteArray(), "application/msexcel");
                fileBody.setDataHandler(new DataHandler(source));
                // 中文乱码问题
                fileBody.setFileName(MimeUtility.encodeText("ww.xlsx"));
                multipart.addBodyPart(fileBody);
            }
            message.setContent(multipart);

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    public static boolean sendKnowledgeEmail(String to, ByteArrayOutputStream baos) {

        List<String> fileNameList = Arrays.asList("表20210509.xls", "20210509.xls", "详情表20210509.xls");
        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        // 发件人电子邮箱
        String from = "**@qq.com";
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器 ->QQ 邮件服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        properties.put("mail.smtp.auth", "true");
        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("**@qq.com", "**"); //发件人邮件用户名、授权码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("信息汇总");

//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setTo(new String[]{to});
//            helper.setFrom(new InternetAddress(from));
//            helper.setSubject("信息汇总");
//            helper.setText(buildKnowledgeContent(), true);
//            // Set To: 头部头字段
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            /*添加附件*/
            Multipart multipart = new MimeMultipart();

            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(buildKnowledgeContent(), "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            if (baos != null) {
                for (String fileName : fileNameList) {
                    MimeBodyPart fileBody = new MimeBodyPart();
                    DataSource source = new ByteArrayDataSource(baos.toByteArray(), "application/msexcel");
                    fileBody.setDataHandler(new DataHandler(source));
                    // 中文乱码问题
                    fileBody.setFileName(MimeUtility.encodeText(fileName));
                    multipart.addBodyPart(fileBody);
                }
            }
            message.setContent(multipart);

            message.saveChanges();
            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;


    }

    public static boolean sendKnowledgeExcelEmail(String to, List<ByteArrayOutputStream> baosList,
                                                  List<String> fileNameList) {

        System.getProperties().setProperty("mail.mime.splitlongparameters", "false");
        // 发件人电子邮箱
        String from = "**@qq.com";
        // 获取系统属性
        Properties properties = System.getProperties();
        // 设置邮件服务器 ->QQ 邮件服务器
        properties.setProperty("mail.smtp.host", "smtp.qq.com");
        properties.setProperty("mail.smtp.auth", "true");
        properties.setProperty("mail.smtp.port", "25");
        properties.setProperty("mail.smtp.timeout", "5000");

        // 获取默认session对象 zz
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("**@qq.com", "***"); //发件人邮件用户名、授权码
            }
        });

        try {
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("信息汇总");

//            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//            helper.setTo(new String[]{to});
//            helper.setFrom(new InternetAddress(from));
//            helper.setSubject("信息汇总");
//            helper.setText(buildKnowledgeContent(), true);
//            // Set To: 头部头字段
//            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            /*添加附件*/
            Multipart multipart = new MimeMultipart();

            BodyPart contentPart = new MimeBodyPart();
            contentPart.setContent(buildKnowledgeContent(), "text/html;charset=utf-8");
            multipart.addBodyPart(contentPart);

            if (CollectionUtils.isEmpty(baosList)) {
                return false;
            }

            for (int i = 0; i < baosList.size(); i++) {
                ByteArrayOutputStream baos = baosList.get(i);
                if (baos != null) {
                    MimeBodyPart fileBody = new MimeBodyPart();
                    DataSource source = new ByteArrayDataSource(baos.toByteArray(), "application/msexcel");
                    fileBody.setDataHandler(new DataHandler(source));
                    // 中文乱码问题
                    fileBody.setFileName(MimeUtility.encodeText(fileNameList.get(i)));
                    multipart.addBodyPart(fileBody);
                }
            }

            message.setContent(multipart);

            message.saveChanges();
            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....from runoob.com");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;


    }


    private static String buildContent() throws IOException {

        //加载邮件html模板
        String fileName = "html/pod-scale-alarm.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.error("读取文件失败，fileNameList:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }


        String contentText = "以下是服务实例伸缩信息, 敬请查看.<br>below is the information of service instance scale, please check. ";
        //邮件表格header
        String header = "<td>分区(Namespace)</td><td>服务(Service)</td><td>伸缩结果(Scale Result)</td><td>伸缩原因(Scale Reason)</td><td>当前实例数(Pod instance number)</td>";
        StringBuilder linesBuffer = new StringBuilder();
        linesBuffer.append("<tr><td>" + "myNamespace" + "</td><td>" + "myServiceName" + "</td><td>" + "myscaleResult" + "</td>" +
                "<td>" + "mReason" + "</td><td>" + "my4" + "</td></tr>");

        //绿色
        String emailHeadColor = "#10fa81";
        String date = DateFormatUtils.format(new Date(), "yyyy/MM/dd HH:mm:ss");
        //填充html模板中的五个参数
        String htmlText = MessageFormat.format(buffer.toString(), emailHeadColor, contentText, date, header, linesBuffer.toString());

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }


    private static String buildKnowledgeContent() throws IOException {
        List<String> fileNameList = Arrays.asList("20210509.xls", "表20210509.xls", "详情表20210509.xls");

        //加载邮件html模板
        String fileName = "html/knowledge_template.html";
        InputStream inputStream = ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuffer buffer = new StringBuffer();
        String line = "";
        try {
            while ((line = fileReader.readLine()) != null) {
                buffer.append(line);
            }
        } catch (Exception e) {
            log.error("读取文件失败，fileNameList:{}", fileName, e);
        } finally {
            inputStream.close();
            fileReader.close();
        }

        //xx年xx月xx日
        String date = DateFormatUtils.format(DateTime.now().minusDays(1).toDate(), "yyyy年MM月dd日");
        String dateTime =
                DateFormatUtils.format(DateTime.now().minusDays(1).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).toDate(),
                        "yyyy年MM月dd日 HH:mm:ss");
        String param1 = date;
        String param2 = dateTime;
        Integer param3 = 1000;
        String param4 = date;
        Integer param5 = 20;
        Integer param6 = 100;
        Integer param7 = 30;
        Integer param8 = 60;
        String param9 = fileNameList.get(0);
        String param10 = fileNameList.get(1);
        String param11 = fileNameList.get(2);

        String param12 = "<td>分区(Namespace)</td><td>服务(Service)</td><td>伸缩结果(Scale Result)</td><td>伸缩原因(Scale Reason)</td><td>当前实例数(Pod instance number)</td>";
        StringBuilder linesBuffer = new StringBuilder();
        linesBuffer.append("<tr><td>" + "myNamespace" + "</td><td>" + "myServiceName" + "</td><td>" + "myscaleResult" + "</td>" +
                "<td>" + "mReason" + "</td><td>" + "my4" + "</td></tr>");
        String param13 = linesBuffer.toString();


        String param14 = param12;
        String param15 = param13;

        String param16 = param12;
        String param17 = param13;


        //绿色
        String emailHeadColor = "#10fa81";

        //填充html模板中的15个参数
        String htmlText = MessageFormat.format(buffer.toString(), param1, param2, param3, param4, param5, param6,
                param7, param8,
                param9, param10, param11, param12, param13, param14, param15, param16, param17);

        //改变表格样式
        htmlText = htmlText.replaceAll("<td>", "<td style=\"padding:6px 10px; line-height: 150%;\">");
        htmlText = htmlText.replaceAll("<tr>", "<tr style=\"border-bottom: 1px solid #eee; color:#666;\">");
        return htmlText;
    }

    private static List<DemoData> data() {
        List<DemoData> list = new ArrayList<DemoData>();
        for (int i = 0; i < 10; i++) {
            DemoData data = new DemoData();
            data.setString("字符串" + i);
            data.setDate(new Date());
            data.setDoubleData(0.56);
            list.add(data);
        }
        return list;
    }

    public static void main(String[] args) {
        List<String> fileNameList = Arrays.asList("20210509.xls", "表20210509.xls", "详情表20210509.xls");

//        JSONArray all = new JSONArray();
//        String[] title = {"哈哈", "hahahah"};
//        ByteArrayOutputStream baos = CreatExcel.creatExcel(title, all);
//        SendEmail.sendEmail("**@qq.com", baos);
//        SendEmail.sendKnowledgeEmail("**@qq.com", baos);

//        ByteArrayOutputStream baos = CreatExcel.simpleWrite(fileNameList.get(0), data());
//        ByteArrayOutputStream baos1 = CreatExcel.simpleWrite(fileNameList.get(1), data());
//        ByteArrayOutputStream baos2 = CreatExcel.simpleWrite(fileNameList.get(2), data());
//        SendEmail.sendKnowledgeExcelEmail("**@qq.com", baos, baos1, baos2);


        ByteArrayOutputStream baos = CreatExcel.simpleWrite(data(), DemoData.class);
        ByteArrayOutputStream baos1 = CreatExcel.simpleWrite(data(), DemoData.class);
        ByteArrayOutputStream baos2 = CreatExcel.simpleWrite(data(), DemoData.class);
        SendEmail.sendKnowledgeExcelEmail("**@qq.com", Arrays.asList(baos, baos1, baos2), fileNameList);
    }
}