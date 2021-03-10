package com.github.cnkeep.simple_springmvc.test.rules;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import com.googlecode.aviator.runtime.function.AbstractFunction;
import com.googlecode.aviator.runtime.function.FunctionUtils;
import com.googlecode.aviator.runtime.type.AviatorBoolean;
import com.googlecode.aviator.runtime.type.AviatorObject;
import org.junit.Test;
import org.springframework.util.StopWatch;
import sun.jvm.hotspot.utilities.RBTree;

import java.util.HashMap;
import java.util.Map;

/**
 * @description:
 * @author: <a href="mailto:zhangleili@lizhi.fm">LeiLi.Zhang</a>
 * @date: 2021/2/25
 * @version:
 **/
public class AvtiorTest {
    public static class Context {
        String name = "ss";
    }

    @Test
    public void avtior() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("compile");
        String expression = "(eval(rule,context) || eval(rule,context)) || eval(rule,context) && eval(rule,context) && eval(rule,context) && eval(rule,context)";
        // 编译表达式, 设置运行速度优先模式
        AviatorEvaluator.setOptimize(AviatorEvaluator.EVAL);
        AviatorEvaluator.addFunction(new EvalFunction());
        Expression compiledExp = AviatorEvaluator.compile(expression);
        stopWatch.stop();


        stopWatch.start("execute");
        Map<String, Object> env = new HashMap<String, Object>();
        env.put("rule", "me");
        env.put("context", new Context());
        env.put("c", -199.100);

        // 执行表达式
        for (int i = 0; i < 100000; i++) {
            Boolean result = (Boolean) compiledExp.execute(env);
//            System.out.println(result);  // false
        }
        stopWatch.stop();
        System.out.println(stopWatch.prettyPrint());
    }

    class EvalFunction extends AbstractFunction {
        @Override
        public AviatorObject call(Map<String, Object> env, AviatorObject arg1, AviatorObject arg2) {
            String rule = FunctionUtils.getStringValue(arg1, env);
            Context context = (Context) FunctionUtils.getJavaObject(arg2, env);
            return AviatorBoolean.valueOf(false);
        }

        public String getName() {
            return "eval";
        }
    }
}
