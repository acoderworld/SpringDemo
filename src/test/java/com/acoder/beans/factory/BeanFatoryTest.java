package com.acoder.beans.factory;

import com.acoder.beans.BeanDefinition;
import com.acoder.beans.exception.BeanCreationException;
import com.acoder.beans.exception.BeanDefinitionException;
import com.acoder.beans.factory.support.DefaultBeanFactory;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class BeanFatoryTest {

    private final DefaultBeanFactory factory = new DefaultBeanFactory("petstore-v1.xml");

    @Test
    public void testGetBean() {

        BeanDefinition beanDefinition = factory.getBeanDefinition("perstoreService");

        assertEquals("com.acoder.service.v1.PerstoreService", beanDefinition.getClassName());
        assertNotNull(factory.getBean("perstoreService"));

    }


    @Test
    public void testInvalidBean() {
        try {
            factory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return ;
        }
        Assert.fail("test invalid bean failed.");
    }

    @Test
    public void testInvalidDefinition() {
        try {
            new DefaultBeanFactory("xxx.xml");
        } catch (BeanDefinitionException e) {
            return;
        }
        Assert.fail("test invalid definition failed.");
    }
}
