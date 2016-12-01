package org.treeleaf.web.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.treeleaf.web.starter.EnableExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by leaf on 2016/11/30 0030.
 */
@Controller
@SpringBootApplication
//@EnableExceptionHandler
public class Application {

    private Logger log = LoggerFactory.getLogger(Application.class);

    @ResponseBody
    @RequestMapping("test")
    public Map test(String s) {
        if ("a".equals(s)) {
            throw new RuntimeException("测试");
        }
        Map map = new HashMap();
        return map;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
