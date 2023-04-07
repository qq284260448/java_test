package com.demo.myf.controller;

import com.demo.myf.annotation.MyField;
import com.demo.myf.annotation.MyLog;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @GetMapping("/sourceA")
    public String sourceA(){
        return "你正在访问sourceA资源";
    }

    @MyField(description = "1",length = 10)
    @MyLog
    @GetMapping("/sourceB")
    public String sourceB(){
        System.out.println("你正在访问sourceB资源");
        return "你正在访问sourceB资源";
    }

}
