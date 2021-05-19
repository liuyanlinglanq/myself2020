package com.lyl.db.utils;

import cn.hutool.json.JSONArray;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.lyl.db.domain.DemoData;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreatExcel {

    public static ByteArrayOutputStream creatExcel(String[] title, JSONArray all) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        //创建一个表格
        Workbook workbook = new XSSFWorkbook();
        // 创建一个工作薄对象
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet("sheet1");
        //设置首行
        XSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            cell.setCellValue(title[i]);
        }
        try {
            workbook.write(baos); // write excel data to a byte array
            baos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("创建成功 office excel");
        return baos;
    }


    public static <T> ByteArrayOutputStream simpleWrite(List<T> dataList, Class<T> clazz) {
//		// 写法1
//		// 这里 需要指定写用哪个class去写，然后写到第一个sheet，名字为模板 然后文件流会自动关闭
//		// 如果这里想使用03 则 传入excelType参数即可
//		EasyExcel.write(fileName, DemoData.class).sheet("模板").doWrite(data());
//
//		// 写法2
//		// 这里 需要指定写用哪个class去写
//		ExcelWriter excelWriter = EasyExcel.write(fileName, DemoData.class).build();
//		WriteSheet writeSheet = EasyExcel.writerSheet("模板").build();
//		excelWriter.write(data(), writeSheet);
//		// 千万别忘记finish 会帮忙关闭流
//		excelWriter.finish();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        EasyExcel.write(baos, clazz).sheet("模板").doWrite(dataList);
        return baos;


    }


    public static void creatExcel(String[] title, JSONArray all,ByteArrayOutputStream baos) {
        //创建一个表格
        Workbook workbook = new XSSFWorkbook();
        // 创建一个工作薄对象
        XSSFSheet sheet = (XSSFSheet) workbook.createSheet("sheet1");
        //设置首行
        XSSFRow row0 = sheet.createRow(0);
        for (int i = 0; i < title.length; i++) {
            XSSFCell cell = (XSSFCell) row0.createCell(i);
            cell.setCellValue(title[i]);
        }
        try {
            workbook.write(baos); // write excel data to a byte array
            baos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("创建成功 office excel");
    }


}