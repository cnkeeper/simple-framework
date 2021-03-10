package com.github.cnkeep.simple_springmvc.test.rules;

import com.google.common.collect.Lists;
import com.googlecode.aviator.AviatorEvaluator;
import org.junit.Test;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/2/23
 * @version:
 **/
public class SPELTest {
    @Test
    public void test4() {
        ExpressionParser parser = new SpelExpressionParser();

        boolean result1 = parser.parseExpression("2>1 and (!true or !false)").getValue(boolean.class);
        boolean result2 = parser.parseExpression("2>1 && (!true || !false)").getValue(boolean.class);

        boolean result3 = parser.parseExpression("2>1 and (NOT true or NOT false)").getValue(boolean.class);
        boolean result4 = parser.parseExpression("2>1 && (NOT true || NOT false)").getValue(boolean.class);

        System.out.println("result1=" + result1);
        System.out.println("result2=" + result2);
        System.out.println("result3=" + result3);
        System.out.println("result4=" + result4);
    }

    public static boolean contains(List<Object> list, Object o) {
        boolean contains = CollectionUtils.contains(list.iterator(), o);
        return contains;

    }

    @Test
    public void testFunctionExpression() throws SecurityException, NoSuchMethodException {
        //定义2个函数,registerFunction和setVariable都可以，不过从语义上面来看用registerFunction更恰当
        StandardEvaluationContext context = new StandardEvaluationContext();
        Method parseInt = Integer.class.getDeclaredMethod("parseInt", String.class);
        context.registerFunction("parseInt1", parseInt);
        context.setVariable("parseInt2", parseInt);
        context.setVariable("age", 10);

        ExpressionParser parser = new SpelExpressionParser();
        System.out.println(parser.parseExpression("#parseInt1('3')==3").getValue(context, boolean.class));
        System.out.println(parser.parseExpression("#parseInt2('3')").getValue(context, int.class));

        ArrayList<Integer> whiteList = Lists.newArrayList(1);
        context.setVariable("whiteList", whiteList);
        context.setVariable("uid", 10);
        context.setVariable("uid3", 1);

        StopWatch stopWatch = new StopWatch();
        stopWatch.start("parse");
        String expression1 = "T(com.github.cnkeep.simple_springmvc.test.rules.SPELTest).contains(#whiteList,#uid) && T(com.github.cnkeep.simple_springmvc.test.rules.SPELTest).contains(#whiteList,#uid) or #age>10 or #parseInt1('3') != #parseInt2('3')";
        Expression expression = parser.parseExpression(expression1);
        stopWatch.stop();
        stopWatch.start("eval");
        boolean result1 = expression.getValue(context, boolean.class);
        stopWatch.stop();
        System.out.println(result1);
        System.out.println(stopWatch.prettyPrint());
    }


}
