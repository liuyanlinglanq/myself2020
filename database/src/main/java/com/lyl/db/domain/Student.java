package com.lyl.db.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author liuyanling
 * @since 2021-05-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("student")
@ApiModel(value = "Student对象", description = "")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键ID")
    @TableId("student_id")
    @ExcelProperty("学生ID")
    private String studentId;

    @ApiModelProperty(value = "姓名")
    @TableField("name")
    @ExcelProperty("学生名")
    private String name;

    @ApiModelProperty(value = "年龄")
    @TableField("age")
    @ExcelProperty("学生年龄")
    private Integer age;

    @ApiModelProperty(value = "邮箱")
    @TableField("email")
    @ExcelProperty("邮箱")
    private String email;


}
