package com.github.cnkeep.dubbo.common;


import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ExtensionLoader<T> {
    private final static String INTERNAL_PATH = "META-INF/rpc/internal/";
    private Map<String, Class> EXTENSION_CLASS_CACHE = new HashMap<>();
    private static final ConcurrentMap<Class<?>, ExtensionLoader<?>> EXTENSION_LOADER_CACHE = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Class<?>, Object> EXTENSION_INSTANCES = new ConcurrentHashMap<>(64);
    private Class<T> type;

    private ExtensionLoader(Class<T> type) {
        this.type = type;
    }

    public static <T> ExtensionLoader<T> getExtensionLoader(Class<T> type) {
        ExtensionLoader<T> extensionLoader = (ExtensionLoader<T>) EXTENSION_LOADER_CACHE.get(type);
        if (null != extensionLoader) {
            return extensionLoader;
        }

        if (null == extensionLoader) {
            EXTENSION_LOADER_CACHE.putIfAbsent(type, new ExtensionLoader<>(type));
            extensionLoader = (ExtensionLoader<T>) EXTENSION_LOADER_CACHE.get(type);
        }

        return extensionLoader;
    }


    public T getExtension(String name) {
        Class aClass = getExtensionClass(name);
        return getOrCreateInstance(aClass);
    }


    private T getOrCreateInstance(Class aClass) {
        T instance = (T) EXTENSION_INSTANCES.get(aClass);
        if (null != instance) {
            return (T) instance;
        }

        try {
            if (instance == null) {
                EXTENSION_INSTANCES.putIfAbsent(aClass, aClass.newInstance());
                instance = (T) EXTENSION_INSTANCES.get(aClass);
            }

            injectExtension(instance);

            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void injectExtension(T instance) {
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods) {
            if (!isSetter(method)) {
                continue;
            }
            Class<?> pt = method.getParameterTypes()[0];
            try {
                String property = getSetterProperty(method);
                Object extensionProperty = ExtensionLoader.getExtensionLoader(pt).getExtension(property);
                if (null != extensionProperty) {
                    method.invoke(instance, extensionProperty);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getParameterTypes().length == 1
                && Modifier.isPublic(method.getModifiers());
    }

    private String getSetterProperty(Method method) {
        return method.getName().length() > 3 ? method.getName().substring(3, 4).toLowerCase() + method.getName().substring(4) : "";
    }


    private Class getExtensionClass(String name) {
        Class aClass = EXTENSION_CLASS_CACHE.get(name);
        if (null == aClass) {
            EXTENSION_CLASS_CACHE = loadClass();
            aClass = EXTENSION_CLASS_CACHE.get(name);
        }

        if (null == aClass) {
            throw new RuntimeException("not found " + name + " for " + type);
        }
        return aClass;
    }


    private Map<String, Class> loadClass() {
        try {
            Map<String, String> loadClassMap = new HashMap<>();
            String name = INTERNAL_PATH + type.getName();
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(name);
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();
                try {
                    Map<String, String> properties = doResolveFile(Paths.get(url.toURI()));
                    loadClassMap.putAll(properties);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            Map<String, Class> extensionClassCache = new HashMap<>();
            for (Map.Entry<String, String> entry : loadClassMap.entrySet()) {
                String className = entry.getValue();
                Class<?> aClass = Class.forName(className);
                extensionClassCache.put(entry.getKey(), aClass);
            }

            return extensionClassCache;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private static Map<String, String> doResolveFile(Path path) throws IOException {
        Map<String, String> properties = new HashMap<>();
        Files.lines(path)
                .map(String::trim)
                .filter(line -> !line.startsWith("#") || line.length() < 1 || line.indexOf("=") < 0)
                .forEach(line -> {
                    int indexOf = line.indexOf("#");
                    if (indexOf > 0) {
                        line = line.substring(0, indexOf);
                    }
                    String[] split = line.split("=");
                    if (split.length < 2) {
                        return;
                    }
                    properties.put(split[0], split[1]);
                });

        return properties;
    }
}
