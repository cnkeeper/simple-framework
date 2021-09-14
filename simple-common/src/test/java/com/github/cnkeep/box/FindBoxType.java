/*
 * *****************************************************
 * Copyright (C) 2021 bytedance.com. All Rights Reserved
 * This file is part of bytedance EA project.
 * Unauthorized copy of this file, via any medium is strictly prohibited.
 * Proprietary and Confidential.
 * ****************************************************
 */

package com.github.cnkeep.box;

import com.google.common.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;
import org.junit.Test;

/**
 * FindBoxType
 *
 * @author LeiLi.Zhang <mailto:zhangleili@bytedance.com>
 * @date 2021/7/2
 */
public class FindBoxType {
    private static abstract class Bean<T> {
        private final TypeToken<T> typeToken = new TypeToken<T>(getClass()) {
        };
        private final Type type = typeToken.getType();

        private Class<T> clazz;

        {
            if (type instanceof Class) {
                clazz = (Class<T>) type;
            }
        }

        public Class<T> getClazz() {
            return clazz;
        }
    }

    @Test
    public void test() {
        System.out.println(new Bean<String>(){}.getClazz());
        System.out.println(new Bean<Integer>(){}.getClazz());
        System.out.println(new Bean<List<String>>(){}.getClazz());
    }
}


