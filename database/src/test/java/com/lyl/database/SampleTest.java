package com.lyl.database;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.lyl.db.DatabaseApplication;
import com.lyl.db.domain.Student;
import com.lyl.db.domain.User;
import com.lyl.db.mapper.UserMapper;
import com.lyl.db.service.IStudentService;
import com.lyl.db.service.IUserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DatabaseApplication.class)
public class SampleTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private IUserService userService;
    @Autowired
    private IStudentService studentService;


    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }


    @Test
    public void testSql() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);

        User userInsert = new User();
        userInsert.setAge(13).setEmail("qq2@qq.com").setId(7L).setName("小可爱2");
        userMapper.insert(userInsert);

        User userById = userMapper.selectById(7L);
        System.out.println(userById);

        //使用updateById更新数据，正常
        userById.setAge(14);
        userMapper.updateById(userById);

        //使用QueryWrapper做删除条件，使用eq，user的id=6，生成的sql DELETE FROM user WHERE id = ?
        QueryWrapper<User> qw = new QueryWrapper<>();
        qw.lambda().eq(User::getId, 6L);
        userMapper.delete(qw);

        //使用UpdateWrapper做更新条件，使用set，user的id=5，错误，有重复的主键
        //使用UpdateWrapper做更新条件，使用set，user的age=15，指定具体的user记录，正常
        UpdateWrapper<User> uw = new UpdateWrapper<>();
        uw.lambda().eq(User::getId, 5L);
        userMapper.update(userById, uw);

        uw.lambda().set(User::getAge, 15);
        userMapper.update(userById, uw);

        //使用UpdateWrapper做删除条件，使用eq，user的id=5，生成的SQL为： DELETE FROM user WHERE id = ?
        uw.lambda().eq(User::getId, 5L);
        userMapper.delete(qw);

        //使用UpdateWrapper做删除条件，使用set，user的id=7，删除所有记录，生成的SQL为：DELETE FROM user
        uw.lambda().set(User::getId, 7L);
        userMapper.delete(qw);

        userList = userMapper.selectList(null);
        userList.forEach(System.out::println);


    }

    @Test
    public void testService() {
        //saveOrUpdate一个id为5的记录,该id在数据库中存在,照理应该是走更新逻辑;
//        User userNoTableId = new User();
//        userNoTableId.setId(5L).setName("我是5啊").setEmail("5@360.com").setAge(5);
//        userService.saveOrUpdate(userNoTableId);
//
//        //saveOrUpdate一个id为6的记录,该id在数据库中不存在,照理应该是走插入逻辑;
//        userNoTableId.setId(6L).setName("我是6啊").setEmail("6@360.com").setAge(6);
//        userService.saveOrUpdate(userNoTableId);


        //saveOrUpdate一个 studentId 为5的记录,该id在数据库中存在,照理应该是走更新逻辑;实际:提示错误:can not execute. because can not find column
        // for id from entity! 因为找不到主键id,而student表的主键是student_id; 所以对于主键id,不是默认的"id"的,使用saveOrUpdate一定要
        // 配合@TableId  saveOrUpdate:TableId 注解存在更新记录，否插入一条记录
//        Student studentNoTableId = new Student();
//        studentNoTableId.setStudentId("5").setName("我是5啊").setEmail("5@360.com").setAge(5);
//        studentService.saveOrUpdate(studentNoTableId);


        //saveOrUpdate一个id为5的记录,该id在数据库中存在,照理应该是走更新逻辑;实际:也是
//        Student studentHasTableId = new Student();
//        studentHasTableId.setStudentId("5").setName("我是5啊").setEmail("5@360.com").setAge(5);
//        studentService.saveOrUpdate(studentHasTableId);
//
//        //saveOrUpdate一个id为6的记录,该id在数据库中不存在,照理应该是走插入逻辑;实际:也是
//        studentHasTableId.setStudentId("6").setName("我是6啊").setEmail("6@360.com").setAge(6);
//        studentService.saveOrUpdate(studentHasTableId);


//        //saveOrUpdateBatch的batchSize的作用
//        //新增user,2个,更新1个;batchSize为2,则生成的sql为
//        //1. 先查询id为5的记录,SELECT id,name,age,email FROM user WHERE id=?
//        //2. 发现有记录,更新id=5的记录,UPDATE user SET name=?, age=?, email=? WHERE id=?
//        //3. 查询id=7,SELECT id,name,age,email FROM user WHERE id=?
//        //4. 没有记录,插入,INSERT INTO user ( id, name, age, email ) VALUES ( ?, ?, ?, ? )
//        //5. 查询id=8, SELECT id,name,age,email FROM user WHERE id=?
//        //6. 没有记录,这次没有insert语句,只有insert 8的参数; (所以可以认为7,8的insert一起执行的)
//        User userOne = new User();
//        userOne.setId(5L).setName("我是新的5啊").setEmail("5@360.com").setAge(5);
//        User userTwo = new User();
//        userTwo.setId(7L).setName("我是7啊").setEmail("7@360.com").setAge(7);
//        User userThree = new User();
//        userThree.setId(8L).setName("我是8啊").setEmail("8@360.com").setAge(8);
//        userService.saveOrUpdateBatch(Arrays.asList(userOne, userTwo, userThree), 5);
//
//        //先查询id=7,SELECT student_id,name,age,email FROM student WHERE student_id=?
//        //没有,INSERT INTO student ( student_id, name, age, email ) VALUES ( ?, ?, ?, ? )
//        //再查询id=8,SELECT student_id,name,age,email FROM student WHERE student_id=?
//        //没有,也只有参数
//        Student studentOne = new Student();
//        studentOne.setStudentId("7").setName("我是7啊").setEmail("7@360.com").setAge(7);
//        Student studentTwo = new Student();
//        studentTwo.setStudentId("8").setName("我是8啊").setEmail("8@360.com").setAge(8);
//
//        studentService.saveOrUpdateBatch(Arrays.asList(studentOne, studentTwo), 5);

//
//        //链式查询 eq("id",7) SELECT id,name,age,email FROM user WHERE id = ?
//        //lt("id", 7) org.apache.ibatis.exceptions.TooManyResultsException: Expected one result (or null) to be
//        // returned by selectOne(), but found: 5
////        User user = userService.query().lt("id", 7).one();
////        System.out.println(user);
//        // gt(User::getId, 8) list: SELECT id,name,age,email FROM user WHERE id = ?
//        //lt(User::getId, 8) SELECT id,name,age,email FROM user WHERE id < ?
//        List<User> userList = userService.lambdaQuery().lt(User::getId, 8).list();
//        userList.forEach(System.out::println);


        //update的user是空的，比如只是new User(),则报，sql语法错误， check the manual that corresponds to your MySQL server version for the right syntax to use near 'WHERE id = 8' at line 3
        User user = new User();
        user.setAge(71);
        //链式更新
        userService.lambdaUpdate().eq(User::getId, 8).update(user);
        userService.update().eq("id", 7).remove();

    }

}