package com.liuxc.www.autostart.config;

import com.liuxc.www.autostart.registrar.DefaultImportBeanDefinitionRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(DefaultImportBeanDefinitionRegistrar.class)
public class UserAutoConfig {

}
