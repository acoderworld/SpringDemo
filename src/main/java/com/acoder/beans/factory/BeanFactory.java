package com.acoder.beans.factory;

import com.acoder.beans.BeanDefinition;

public interface BeanFactory {

    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);
}
