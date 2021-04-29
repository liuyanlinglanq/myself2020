package com.lyl.myself;

import com.lyl.myself.business.TestController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;

@Slf4j
@SpringBootApplication
public class DjnativeswingApplication {

    public static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {

        return builder.sources(DjnativeswingApplication.class).bannerMode(Banner.Mode.CONSOLE);
    }

    public static void main(String[] args) {
        // SpringApplication.run(Application.class, args);
        configureApplication(new SpringApplicationBuilder()).run(args);
        log.info("myself Application running...");
//        SpringApplicationBuilder builder = new SpringApplicationBuilder(DjnativeswingApplication.class);
//        ApplicationContext applicationContext = builder.headless(false).run(args);
//        applicationContext.getBean(TestController.class);
////        SpringApplication.run(DjnativeswingApplication.class, args);

    }

}
