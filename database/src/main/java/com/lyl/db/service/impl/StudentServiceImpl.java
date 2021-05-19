package com.lyl.db.service.impl;

import com.lyl.db.domain.Student;
import com.lyl.db.mapper.StudentMapper;
import com.lyl.db.service.IStudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liuyanling
 * @since 2021-05-11
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements IStudentService {

}
