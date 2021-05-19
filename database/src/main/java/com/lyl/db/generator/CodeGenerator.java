package com.lyl.db.generator;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import io.micrometer.core.instrument.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * description CodeGenerator
 *
 * @author liuyanling
 * @date 2021-05-10 20:19
 */
public class CodeGenerator {

    public static void main(String[] args) {
        //moduleName
        String moduleName = "database";
        String packageName = "com.lyl.db";

        //代码生成器
        AutoGenerator mpg = new AutoGenerator();

        //全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/" + moduleName + "/src/main/java")
                .setFileOverride(true)
                .setActiveRecord(false).setEnableCache(false)
                .setServiceName("I%sService")
                .setServiceImplName("%sServiceImpl")
                .setAuthor("liuyanling")
                .setOpen(true)
                //实体属性,Swagger2注解
                .setSwagger2(true);

        //数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL)
                .setUrl("jdbc:mysql://localhost:3306/project_test?useUnicode=true&characterEncoding=UTF-8" +
                        "&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=Asia/Shanghai")
                .setDriverName("com.mysql.cj.jdbc.Driver")
                .setUsername("root")
                .setPassword("azx159jmn*A");


        //包配置
        PackageConfig pc = new PackageConfig();
        pc.setParent("")
                .setModuleName("")
                .setEntity(packageName + ".domain")
                .setController(packageName + ".controller")
                .setService(packageName + ".service")
                .setServiceImpl(packageName + ".service.impl")
                .setMapper(packageName + ".mapper")
                .setXml("mapper");


        //策略配置
        StrategyConfig sc = new StrategyConfig();
        sc.setTablePrefix("")
//                .setSuperEntityClass("com.lyl.db.domain.BaseEntity")
//                .setSuperEntityColumns("creatorId", "creatorName", "createDate", "modifierId", "modifierName", "modifyDate")
                .setInclude(scanner("student").split(","))
                .setCapitalMode(true)
                .setSkipView(true)
                .setNaming(NamingStrategy.underline_to_camel)
                .setColumnNaming(NamingStrategy.underline_to_camel)
                .setEntitySerialVersionUID(true)
                .setEntityColumnConstant(false)
                .setEntityBuilderModel(true)
                .setEntityLombokModel(true)
                .setEntityBooleanColumnRemoveIsPrefix(false)
                .setRestControllerStyle(true)
                .setControllerMappingHyphenStyle(false)
                .setEntityTableFieldAnnotationEnable(true);

        //自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                //to do nothing
                System.out.println("这是做什么的?");
                Map<String, String> map = new HashMap<>();
                map.put("可爱的", "学生");
            }
        };

        mpg.setDataSource(dsc).
                setGlobalConfig(gc)
                .setPackageInfo(pc)
                .setStrategy(sc)
                .setCfg(cfg)
                .execute();


    }


    /**
     * 读取控制台内容
     *
     * @param tip
     * @return
     */
    public static String scanner(String tip) {
//        Scanner scanner = new Scanner(System.in);
//        StringBuilder help = new StringBuilder();
//        help.append("请输入" + tip + ":");
//        System.out.println(help.toString());
//        if (scanner.hasNext()) {
//            String ipt = scanner.next();
//            if (StringUtils.isNotBlank(ipt)) {
//                return ipt;
//            }
//        }
//        throw new MybatisPlusException("请输入正确的" + tip + "!");
        return tip;
    }
}


