package com.liuxc.www.autostart.registrar;

import com.liuxc.www.autostart.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class DefaultImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        //创建bean
        RootBeanDefinition beanDefinition = new RootBeanDefinition(User.class);
        //注册bean
        registry.registerBeanDefinition("userBean",beanDefinition);

    }
}
