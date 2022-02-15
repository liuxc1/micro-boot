# 项目功能介绍

## 自定义启动Banner

* 方式一
  > 自定义建一个*.txt文件，将该文件放在resources下，重新启动项目即可。
* 方式二
  > 通过org.springframework.boot.Banner接口方式处理启动Banner
  ```
    Params:
    environment – the spring environment 项目启动所指派的profile
    sourceClass – the source class for the application 应用程序类
    out – the output print stream 文件输出类
    void printBanner(Environment environment, Class<?> sourceClass, PrintStream out);
  
    public static enum Mode {
    OFF, //关闭启动banner日志
    CONSOLE, //控制台输出banner
    LOG; //日志文件中输出banner
  
    private Mode() {
          }

  //在启动类中设置自定义启动banner
  springApplication.setBanner(new CustomBannerImpl());
  //设置banner输出模式
  springApplication.setBannerMode(Banner.Mode.CONSOLE);
  ```

## 导入Spring配置文件

> 导入spring.xml配置方式，在启动类上加入@ImportResource注解

  ```
  @SpringBootApplication
  @ImportResource({"classpath:conf/spring.xml"})
  public class MicroBootStartApplication {}
```

## 项目热部署

> 在pom.xml导入热部署依赖包，并设置开发工具为自动编译就实现热部署，每次代码修改后自动重启容器。

  ```
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-devtools</artifactId>
      <scope>compile</scope>
  </dependency>
```

## 整合JUnit5用例测试

> 1.增加相关测试依赖

```
   <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-test</artifactId>
            <scope>compile</scope>
        </dependency>
```

> 2.编写测试类，增加相关注解

```
@ExtendWith(SpringExtension.class) //使用junit5测试方式
@WebAppConfiguration//启动web环境
@SpringBootTest(classes = MicroBootStartApplication.class)//程序启动主类
public class MessageServiceTest {
    @Autowired
    private IMessageService messageService;

    @Test
    public void testEcho() {
        String echo = messageService.echo("hello !");
        Assertions.assertNull(echo);
        System.out.println("xxx-->" + echo);
    }
}
```
