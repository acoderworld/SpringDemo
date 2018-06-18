package com.acoder.beans.factory.support;

import com.acoder.beans.BeanDefinition;

public class GenericBeanDefinition implements BeanDefinition {

    private String id;
    private String className;

    public GenericBeanDefinition(String id, String className) {
        this.id = id;
        this.className = className;
    }

    @Override
    public String getClassName() {
        return this.className;
    }
}
