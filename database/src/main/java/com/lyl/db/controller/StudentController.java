package com.lyl.db.controller;


import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
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

    /**
     * 这个和这个之前可以生效;
     *   <parent>
     *         <groupId>org.springframework.boot</groupId>
     *         <artifactId>spring-boot-starter-parent</artifactId>
     *         <version>2.2.7.RELEASE</version>
     *         <relativePath/>
     *     </parent>
     * @param title
     * @return
     */
    @GetMapping("/common")
    public String checkGetBlank(@RequestParam("title") @Valid @NotBlank(message = "标题不能为空") String title) {
        System.out.println(title);
        return title;
    }


    @GetMapping("/validate")
    public String checkGetBlankValidate(@RequestParam("title") @Validated @NotBlank(message = "标题不能为空") String title) {
        System.out.println(title);
        return title;
    }
}


