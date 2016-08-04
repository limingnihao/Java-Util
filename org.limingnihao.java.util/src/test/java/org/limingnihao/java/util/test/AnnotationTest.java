package org.limingnihao.java.util.test;

import sun.management.MethodInfo;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by lishiming on 16/8/1.
 */
public class AnnotationTest {


    public static void main(String[] args) {
        try {
            System.out.println("is = " + ControllerTest.class.isAnnotationPresent(org.limingnihao.annotation.ControllerTest.class));
            System.out.println("is = " + AnnotationTest.class.isAnnotationPresent(org.limingnihao.annotation.ControllerTest.class));

            for (Method method : ControllerTest.class.getMethods()) {
                System.out.println("metControllerTesthod - " + method.getName());

            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
}
