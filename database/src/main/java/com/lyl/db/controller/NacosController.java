package com.lyl.db.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * description NacosController
 *
 * @author liuyanling
 * @date 2021-05-19 20:33
 */
@RestController
@RequestMapping("/nacos")
@RefreshScope
public class NacosController {

    @NacosValue(value = "${email.toList}", autoRefreshed = true)
    private String toList;

    @Value(value = "${email.toList}")
    private String toListValue;

    @GetMapping("/config")
    public String checkGetBlankValidate() {
        System.out.println(toList + "  " + toListValue);
        return toList + "  " + toListValue;
    }
}
