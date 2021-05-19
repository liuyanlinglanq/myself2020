package com.lyl.db.controller;


import io.swagger.annotations.ApiParam;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liuyanling
 * @since 2021-05-10
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @GetMapping("/validate")
    public String checkGetBlankValidate(@ApiParam(value = "title", required = true)
                                        @RequestParam(value = "title",required = false)
                                        @NotNull(message = "title 不能为空")
                                        @NotBlank(message = "title 不能为空1") String title) {
        System.out.println(title);
        return title;
    }
}

