package com.lyl.db.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author liuyanling
 * @since 2021-05-11
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @GetMapping("/common")
    public String checkGetBlank(@RequestParam("title") String title) {
        System.out.println(title);
        return title;
    }

    @Validated
    @GetMapping("/validate")
    public String checkGetBlankValidate(@RequestParam("title") @NotBlank(message = "标题不能为空") String title) {
        System.out.println(title);
        return title;
    }
}

