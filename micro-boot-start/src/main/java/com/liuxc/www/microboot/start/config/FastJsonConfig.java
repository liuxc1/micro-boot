package com.liuxc.www.microboot.start.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.ArrayList;
import java.util.List;

//@Configuration
public class FastJsonConfig extends WebMvcConfigurationSupport {

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        //1.清除原用的JackSon处理方式
        for (int i = 0; i < converters.size(); i++) {
            if (converters.get(i) instanceof MappingJackson2HttpMessageConverter) {
               converters.remove(i);// 删除当前的转换器
            }
        }
        //2.创建FastJson转换器
        FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        //3.FastJSON在进行最终消息转换处理的时候实际上是需要进行相关配置定义的
        com.alibaba.fastjson.support.config.FastJsonConfig config = new com.alibaba.fastjson.support.config.FastJsonConfig();
        config.setSerializerFeatures(
                SerializerFeature.WriteMapNullValue, // 允许Map的内容为null
                SerializerFeature.WriteNullListAsEmpty, // List集合为null则使用“[]”代替
                SerializerFeature.WriteNullStringAsEmpty, // String内容为空使用空字符串代替
                SerializerFeature.WriteDateUseDateFormat, // 日期格式化输出
                SerializerFeature.WriteNullNumberAsZero, // 数字为空使用0表示
                SerializerFeature.DisableCircularReferenceDetect // 禁用循环引用
        );
        fastJsonHttpMessageConverter.setFastJsonConfig(config);
        //4.设置转换器处理的媒体类型
        List<MediaType> fastjsonMediaTypes = new ArrayList<>();
        fastjsonMediaTypes.add(MediaType.APPLICATION_JSON);
        fastJsonHttpMessageConverter.setSupportedMediaTypes(fastjsonMediaTypes);
        //5.加入转换器
        converters.add(fastJsonHttpMessageConverter);
    }
}
