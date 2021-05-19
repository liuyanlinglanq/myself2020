package com.lyl.db.domain;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.TableName;
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
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
@ApiModel(value="User对象", description="")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键ID")
    @ExcelIgnore
    private Long id;

    @ApiModelProperty(value = "姓名")
    @ExcelProperty("姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    @ExcelProperty("年龄")
    private Integer age;

    @ApiModelProperty(value = "邮箱")
    @ExcelProperty("邮箱")
    private String email;


}
