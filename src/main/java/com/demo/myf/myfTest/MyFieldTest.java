package com.demo.myf.myfTest;


import com.demo.myf.annotation.MyField;
import com.demo.myf.annotation.MyLog;
import org.junit.Test;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import java.lang.reflect.Field;

public class MyFieldTest {

//    //使用我们的自定义注解
//    @MyField(description = "用户名", length = 12)
//    private String username;

    @Test
    public void testMyField(){

        // 获取类模板
        Class c = MyFieldTest.class;

        // 获取所有字段
        for(Field f : c.getDeclaredFields()){
            // 判断这个字段是否有MyField注解
            if(f.isAnnotationPresent(MyField.class)){
                MyField annotation = f.getAnnotation(MyField.class);
                System.out.println(f.getName()+".."+annotation.description()+"--"+annotation.length());
            }
        }

    }


    @Test
    public void testMyLog(){
        System.out.println("testMyLog");
    }

}