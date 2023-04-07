package com.demo.myf.aspect;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect // 1.表明这是一个切面类
@Component
public class MyLogAspect {

    // 2. PointCut表示这是一个切点，@annotation表示这个切点切到一个注解上，后面带该注解的全类名
    // 切面最主要的就是切点，所有的故事都围绕切点发生
    // logPointCut()代表切点名称
    @Pointcut("@annotation(com.demo.myf.annotation.MyLog)")
    public void logPointCut(){};

    @Pointcut("@annotation(com.demo.myf.annotation.MyField)")
    public void logPointCut1(){};

    // 3. 前置通知
    @Before("logPointCut()")
    public void logAround(){

        System.out.println( "前置通知：方法执行切片");

    }

    @After("logPointCut1()")
    public void logAfter(){
        System.out.println( "后置通知：方法执行切片");
    }

}