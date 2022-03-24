package com.liuxc.www.autostart.selector;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class DefaultImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {

        return new String[]{"com.liuxc.www.autostart.domain.User"}; //配置需要扫描类的全路径
    }
}
