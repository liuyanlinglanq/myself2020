package com.lyl.database;

import cn.hutool.json.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyl.db.DatabaseApplication;
import com.lyl.db.domain.DemoData;
import com.lyl.db.domain.Student;
import com.lyl.db.domain.User;
import com.lyl.db.mapper.UserMapper;
import com.lyl.db.service.IStudentService;
import com.lyl.db.service.IUserService;
import com.lyl.db.utils.CreatExcel;
import com.lyl.db.utils.SendEmail;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseApplication.class)
public class EmailTest {


    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentService studentService;


    @Test
    public void testEmail() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userService.list(null);

        List<Student> studentList = studentService.list(null);
        String date = DateFormatUtils.format(DateTime.now().minusDays(1).toDate(), "yyyyMMdd");

        String fileNameUser = "用户维护详情表" + date + ".xlsx";
        String fileNameStudent = "学生维护详情表" + date + ".xlsx";
        String fileNameOther = "其他维护详情表" + date + ".xlsx";

        List<String> fileNameList = Arrays.asList(fileNameUser, fileNameStudent, fileNameOther);

        ByteArrayOutputStream baos = CreatExcel.simpleWrite(userList, User.class);
        ByteArrayOutputStream baos1 = CreatExcel.simpleWrite(studentList, Student.class);

        JSONArray all = new JSONArray();
        String[] title = {"哈哈", "hahahah"};
        ByteArrayOutputStream baos2 = new ByteArrayOutputStream();
        CreatExcel.creatExcel(title, all, baos2);


//        ByteArrayOutputStream baos2 = CreatExcel.simpleWrite(fileNameList.get(2), data());
//        SendEmail.sendKnowledgeExcelEmail("**@qq.com", Arrays.asList(baos, baos1, baos2), fileNameList);
        SendEmail.sendKnowledgeExcelEmail("**@qq.com", Arrays.asList(baos, baos1, baos2), fileNameList);
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


}