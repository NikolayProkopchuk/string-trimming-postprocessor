package com.prokopchuk.trimpostproc;

import com.prokopchuk.trimpostproc.annotation.Trimmed;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Parameter;

public class TrimmedAnnotationBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Trimmed.class)) {
            return createProxy(bean);
        }
        return bean;
    }

    private Object createProxy(Object bean) {
        MethodInterceptor methodInterceptor = (object, method, args, methodProxy) -> {
            Parameter[] parameters = method.getParameters();
            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].getType().isAssignableFrom(String.class)) {
                    args[i] = args[i].toString().trim();
                }
            }

            var result = methodProxy.invokeSuper(object, args);

            if (method.getReturnType().isAssignableFrom(String.class)) {
                return result.toString().trim();
            }

            return result;
        };
        return Enhancer.create(bean.getClass(), methodInterceptor);
    }
}
