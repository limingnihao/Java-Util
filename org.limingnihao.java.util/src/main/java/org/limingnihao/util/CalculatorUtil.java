package org.limingnihao.util;

import net.sourceforge.jeval.Evaluator;
import java.util.*;
/**
 * Created by lishiming on 2016/11/6.
 */
public class CalculatorUtil {

    /**
     * 根据公式计算结果
     * @param expression
     * @param value
     * @return
     */
    public static double calculator(String expression, String[] value){
        try {
            Map<String, String> params = new HashMap<String, String>();
            for(int i=0; i<value.length; i++){
                params.put("a" + i, value[i]);
            }
            for(Map.Entry<String, String> e : params.entrySet()){
                expression = expression.replace("${" + e.getKey() + "}", e.getValue());
            }
            Evaluator eval = new Evaluator();
            String result = eval.evaluate(expression);
            double r= NumberUtil.getDoubleScale(NumberUtil.parseDouble(result), 4);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 根据公式计算结果
     * @param expression
     * @param params
     * @return
     */
    public static double calculator(String expression, Map<String, String> params){
        try {
            for(Map.Entry<String, String> e : params.entrySet()){
                expression = expression.replace("${" + e.getKey() + "}", e.getValue());
            }
            Evaluator eval = new Evaluator();
            String result = eval.evaluate(expression);
            double r = NumberUtil.getDoubleScale(NumberUtil.parseDouble(result), 4);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

}
