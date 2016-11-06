package org.limingnihao.java.util.test;

import org.limingnihao.util.CalculatorUtil;
import org.limingnihao.util.StringUtil;

/**
 * Created by lishiming on 16/8/1.
 */
public class AnnotationTest {


    public static void main(String[] args) {
        try {
//            System.out.println("is = " + ControllerTest.class.isAnnotationPresent(org.limingnihao.annotation.ControllerTest.class));
//            System.out.println("is = " + AnnotationTest.class.isAnnotationPresent(org.limingnihao.annotation.ControllerTest.class));
//
//            for (Method method : ControllerTest.class.getMethods()) {
//                System.out.println("metControllerTesthod - " + method.getName());
//
//            }
            String expression = "${a0} * 100 + ${a1} * 10 + ${a2}";
            double b = CalculatorUtil.calculator(expression, "4,5,6".split(","));
            System.out.println(b);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
