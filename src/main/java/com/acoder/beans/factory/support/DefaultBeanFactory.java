package com.acoder.beans.factory.support;

import com.acoder.beans.BeanDefinition;
import com.acoder.beans.exception.BeanCreationException;
import com.acoder.beans.exception.BeanDefinitionException;
import com.acoder.beans.factory.BeanFactory;
import com.acoder.utils.ClassUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class DefaultBeanFactory implements BeanFactory {

    private static final String ID_ATTRIBUTE = "id";
    private static final String CLASS_ATTRIBUTE = "class";
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();


    public DefaultBeanFactory(String configFile) {
        loadBeanDefinition(configFile);
    }

    private void loadBeanDefinition(String configFile) {
        InputStream is = null;
        try {
            ClassLoader cl = ClassUtils.getDefaultClassLoader();
            is = cl.getResourceAsStream(configFile);
            SAXReader reader = new SAXReader();
            Document read = reader.read(is);
            Element root = read.getRootElement();
            Iterator iterator = root.elementIterator();
            while (iterator.hasNext()) {
                Element element = (Element) iterator.next();
                String id = element.attributeValue(ID_ATTRIBUTE);
                String className = element.attributeValue(CLASS_ATTRIBUTE);
                BeanDefinition beanDefinition = new GenericBeanDefinition(id, className);
                this.beanDefinitionMap.put(id, beanDefinition);
            }

        } catch (DocumentException e) {
            throw new BeanDefinitionException("load " + configFile + " file failed.", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition definition = this.beanDefinitionMap.get(beanId);
        if (definition == null) {
            throw new BeanCreationException("bean definition does not exist", null);
        }
        String className = definition.getClassName();
        ClassLoader cl = ClassUtils.getDefaultClassLoader();
        try {
            Class<?> aClass = cl.loadClass(className);
            return aClass.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("class :" + className + " create failed.", e);
        }
    }

}
