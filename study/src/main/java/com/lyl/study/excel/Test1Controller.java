package com.lyl.study.excel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping(value = "/test")
@RestController
@Validated
public class Test1Controller {

    @PostMapping(value = "/import")
    public String excelImportContribution(@RequestParam(value = "file") MultipartFile file) {

        ExcelDataListener<DemoData> dataListener = new ExcelDataListener<>();
        try {
            EasyExcel.read(file.getInputStream(), DemoData.class, dataListener).sheet().doRead();
        } catch (Exception e) {
            System.out.println("excel import contribution read exception!.");
        }

        List<DemoData> excelContent = dataListener.getData();
        return JSONObject.toJSONString(excelContent);

    }


}