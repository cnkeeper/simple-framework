package com.github.cnkeep.simple_springmvc.test.rules;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Options;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;
import org.mvel2.MVEL;
import org.mvel2.ParserContext;
import org.mvel2.integration.VariableResolverFactory;
import org.mvel2.integration.impl.MapVariableResolverFactory;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.StopWatch;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/4/23
 * @version:
 **/
public class PerformanceTest {
    static long count = 1000000L;

    @Test
    public void testWithBool() {
        int counter = 1;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("aviator");
        for (int i = 0; i < counter; i++) {
            aviatorWithBool();
        }
        stopWatch.stop();

        stopWatch.start("mvel");
        for (int i = 0; i < counter; i++) {
            mvelWithBool();
        }

        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void testWithCalculate() {
        int counter = 1;
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("aviator");
        for (int i = 0; i < counter; i++) {
            aviatorWithCalculate();
        }
        stopWatch.stop();

        stopWatch.start("mvel");
        for (int i = 0; i < counter; i++) {
            mvelWithCalculate();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    @Test
    public void testWithFunction() {
        int count = 10;
        StopWatch stopWatch = new StopWatch();


        stopWatch.start("mvel");
        for (int i = 0; i < count; i++) {
            mvelWithFunction();
        }
        stopWatch.stop();


        stopWatch.start("aviator");
        for (int i = 0; i < count; i++) {
            aviatorWithFunction();
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static void mvelWithCalculate() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("parse");
        ParserContext parserContext = new ParserContext();
        String expressionStr = "x + y*2 - z";
        ;
        Serializable expression = MVEL.compileExpression(expressionStr, parserContext);
        stopWatch.stop();
        stopWatch.start("eval");


        for (int i = 0; i < count; i++) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("x", 1);
            context.put("y", 1);
            context.put("z", 1);
            VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
            MVEL.executeExpression(expression, context, functionFactory);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static void mvelWithBool() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("parse");
        ParserContext parserContext = new ParserContext();
        String expressionStr = "(x || y) || z && m";
        ;
        Serializable expression = MVEL.compileExpression(expressionStr, parserContext);
        stopWatch.stop();
        stopWatch.start("eval");


        for (int i = 0; i < count; i++) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("x", true);
            context.put("y", true);
            context.put("z", true);
            context.put("m", true);
            VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
            MVEL.executeExpression(expression, context, functionFactory);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static void mvelWithFunction() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("parse");
        ParserContext parserContext = new ParserContext();
        parserContext.addImport("eval", MVEL.getStaticMethod(PerformanceTest.class, "eval", new Class[]{String.class, Context.class}));
        String expressionStr = "(eval('1',context) || eval('2',context)) || eval('3',context) && eval('4',context) && eval('5',context) && eval('6',context)";
        Serializable expression = MVEL.compileExpression(expressionStr, parserContext);
        stopWatch.stop();
        stopWatch.start("eval");


        for (int i = 0; i < count; i++) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("rule", "me");
            context.put("context", new Context());
            VariableResolverFactory functionFactory = new MapVariableResolverFactory(context);
            MVEL.executeExpression(expression, context, functionFactory);
        }
        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());
    }

    public static void spel() {
        //定义2个函数,registerFunction和setVariable都可以，不过从语义上面来看用registerFunction更恰当
        Method evalMethod = null;
        try {
            evalMethod = PerformanceTest.class.getDeclaredMethod("eval", String.class, Context.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        ExpressionParser parser = new SpelExpressionParser();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("parse");
        String expressionStr = "(#eval(#rule,#context) || #eval(#rule,#context)) || #eval(#rule,#context) && #eval(#rule,#context) && #eval(#rule,#context) && #eval(#rule,#context)";
        Expression expression = parser.parseExpression(expressionStr);
        stopWatch.stop();
        stopWatch.start("eval");

        for (int i = 0; i < count; i++) {
            StandardEvaluationContext context = new StandardEvaluationContext();
            context.setVariable("rule", "me");
            context.setVariable("context", new Context());
            context.registerFunction("eval", evalMethod);
            expression.getValue(context, boolean.class);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }


    public static void aviatorWithCalculate() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("compile");
        // 编译表达式, 设置运行速度优先模式
        AviatorEvaluator.setOptimize(AviatorEvaluator.EVAL);
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
        AviatorEvaluator.addFunction(new EvalFunction());
        String expressionStr = "x + y*2 - z";
        com.googlecode.aviator.Expression compiledExp = AviatorEvaluator.compile(expressionStr, true);
        stopWatch.stop();

        stopWatch.start("execute");

        // 执行表达式
        for (int i = 0; i < count; i++) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("x", 1);
            context.put("y", 1);
            context.put("z", 1);
            compiledExp.execute(context);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static void aviatorWithBool() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("compile");
        // 编译表达式, 设置运行速度优先模式
        AviatorEvaluator.setOptimize(AviatorEvaluator.EVAL);
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
        AviatorEvaluator.addFunction(new EvalFunction());
        String expressionStr = "(x || y) || z && m";
        com.googlecode.aviator.Expression compiledExp = AviatorEvaluator.compile(expressionStr, true);
        stopWatch.stop();

        stopWatch.start("execute");

        // 执行表达式
        for (int i = 0; i < count; i++) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("x", true);
            context.put("y", true);
            context.put("z", true);
            context.put("m", true);
            compiledExp.execute(context);
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    public static void aviatorWithFunction() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("compile");
        // 编译表达式, 设置运行速度优先模式
        AviatorEvaluator.setOption(Options.OPTIMIZE_LEVEL, AviatorEvaluator.EVAL);
        AviatorEvaluator.addFunction(new EvalFunction());
        String expressionStr = "(eval('1',context) || eval('2',context)) || eval('3',context) && eval('4',context) && eval('5',context) && eval('6',context)";
        com.googlecode.aviator.Expression compiledExp = AviatorEvaluator.compile(expressionStr);
        stopWatch.stop();

        stopWatch.start("execute");

        // 执行表达式
        for (int i = 0; i < count; i++) {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("rule", "me");
            context.put("context", new Context());
            compiledExp.execute(context);
        }
        stopWatch.stop();
//        System.out.println(stopWatch.prettyPrint());
    }

    static class EvalFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            String rule = FunctionUtils.getStringValue(arg1, env);
            Context context = (Context) FunctionUtils.getJavaObject(arg2, env);
            eval(rule, context);
            return AviatorBoolean.valueOf(false);
        }

        public String getName() {
            return "eval";
        }
    }

    public static class Context {
        String name = "";
    }

    public static boolean eval(String rule, Context context) {
        try {
//            TimeUnit.NANOSECONDS.sleep(1);
//            byte[] arr = new byte[1024];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
