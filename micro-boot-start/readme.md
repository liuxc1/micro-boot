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

## 整合lombok

> 1.在pom.xml文件中引入对应的依赖包

```
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <version>1.18.22</version>
    <scope>compile</scope>
</dependency>
```

> 2.安装idea插件 lombok插件，默认新表idea是安装好了的。

### Accessor

> 访问器模式提供了三种不同的方案：fluent，chain，prefix

* fluent 模式

> 特点可以直接将属性名称作为属性设置和返回的方法名称，进行属性配置的时候可以直接使用代码链的方式进行配置。
> 访问器模式不能进行注解混用，按照一种的固定方式形成才可以正常开发。

  ```
  @Data
  @Accessors(fluent=true)
  public class User {
      private Integer id;
      private String userName;
      private String passWord;
  }
  
  //测试代码
   @Test
    public void test(){
        User user = new User();
        user.userName("xx").passWord("xx");
        System.out.println(user);
    }
  ```

* chain 模式

> 访问链的模式:在每个setter方法里面都返回有当前对象实例，这样就可以直接基于代码链的方式配置。
> 在大部分情况下基于反射的模式来获取setter方法对象都是采用void返回值类型来进行获取的，所以即便此时有了返回值，那么程序也无法正常的采用代码链的进行处理。

```
    @Data
    @Accessors(chain = true)
    public class User {
        private Integer id;
        private String userName;
        private String passWord;
    }
    //测试代码
    @Test
    public void test(){
        User user = new User();
        user.setUserName("xxx").setPassWord("xxx").setId(1);
        System.out.println(user);
    }
```

* prefix 模式

> 配置前缀操作,在生成get,set方法是会去掉根据配置的前缀生成方法

```
@Data
@Accessors(prefix = "liux")
public class User {
    private Integer liuxId;
    private String userName;
    private String passWord;
}
//测试
@Test
public void test(){
  User user = new User();
  user.setId(1);
  System.out.println(user);
}
```

### Builder模式

> 使用builder注解，会生成一个内部builder类，内部使用的是访问者模式的 chain

```
@Data
@Builder
public class User {
    private Integer id;
    private String userName;
    private String passWord;
}
//测试代码
 @Test
    public void test(){
        User user = User.builder().userName("xxx").userName("xxx").build();
    }
```

### 异常处理操作

> 帮助用户自动进行异常捕获

```
//常规处理异常的方式
public class MessageHandler {
    public void handler(User user){
        if(null == user){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

//使用lombok 注解方式处理异常 @SneakyThrows
public class MessageHandler {
    @SneakyThrows
    public void handler(User user){
        if(null == user){
            throw new Exception();
        }
    }
}
```

### 自动关闭IO流 @Cleanup

``` java
    @SneakyThrows
    public void red(){
       @Cleanup InputStream inputStream = new FileInputStream("D:ddd");
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        String s1 = new String(bytes, 0, read);
        System.out.println(s1);
    }
    
    //自动生成的代码
    public void red() {
    try {
      InputStream inputStream = new FileInputStream("D:ddd");
      try {
        byte[] bytes = new byte[1024];
        int read = inputStream.read(bytes);
        String s1 = new String(bytes, 0, read);
        System.out.println(s1);
      } finally {
        if (Collections.<InputStream>singletonList(inputStream).get(0) != null)
          inputStream.close(); 
      } 
    } catch (Throwable $ex) {
      throw $ex;
    } 
  }
```

## rest对象转换

```java
  //参数转换操作

/**
 * 参数转换操作类
 */
public abstract class AbstractBaseController {
    // 在现在的开发之中如果要将字符串转为日期时间，考虑到多线程环境下的并发问题，所以一定要使用LocalDate
    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("initBinder");
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                LocalDate localDate = LocalDate.parse(text, LOCAL_DATE_FORMAT);
                Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                super.setValue(Date.from(instant));
            }
        });
    }
}

//使用参数转换
@RestController
public class MessageController extends AbstractBaseController {
    @RequestMapping("/echo2")
    public Object echo2(Message message) {

        return message;
    }

}
```

## Jackson组件更换为FastJson

> 不推荐将spring Boot中的Jackson更换为FastJson

* 1.引入FastJson依赖

```xml

<dependency>
    <groupId>com.alibaba</groupId>
    <artifactId>fastjson</artifactId>
    <version>1.2.79</version>
</dependency>
```

* 2.替换Jackson

```java

@Configuration
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
```

* 3.测试修改vo类，替换是否成功

```java

@Data
public class Message {
    private String title;
    private String content;
    @JSONField(format = "yyyy年MM月dd日") // FastJSON组件所提供的转换格式
    private Date publishDate;
}
```

## 整合响应Xml数据

* 1.引用maven相关依赖包

```xml

<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

* 2.修改vo配置类，增加xml相关注解

```java

@Data
@XmlRootElement
public class User {
    @XmlElement
    private Integer id;
    @XmlElement
    private String userName;
    @XmlElement
    private String passWord;
}
```

* 3.响应结果

```xml

<User>
    <id>1</id>
    <userName>xxx</userName>
    <passWord>xxx</passWord>
</User>
```

## 整合响应PDF数据

> 引用maven相关依赖包

```xml

<dependency>
    <groupId>com.itextpdf</groupId>
    <artifactId>itextpdf</artifactId>
    <version>5.5.13.3</version>
</dependency>
```

## 整合响应返回图像流数据

> 1.增加图片消息转换器配置

```java
  /**
 * 处理图像转换器
 */
@Configuration
public class ImageConvertConfig implements WebMvcConfigurer {

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(new BufferedImageHttpMessageConverter());
    }
}
```

> 2.controller返回图片流数据

```java

@RestController
@RequestMapping("/image")
public class ImageController {

    @RequestMapping(value = "/getImage", produces = {MediaType.IMAGE_GIF_VALUE, MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    public Object getImage() throws IOException {
        //读取资源文件
        ClassPathResource resource = new ClassPathResource("images/111.jpeg");
        return ImageIO.read(resource.getInputStream());
    }
}
```

## 整合响应返回视频流数据

> 1.创建视频流处理handle

```java

@Component
public class VideoResponseHandle extends ResourceHttpRequestHandler {
    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        return new ClassPathResource("video/1111.mp4");
    }
}
```

> 2.创建响应controller

```java

@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoResponseHandle videoResponseHandle;

    public VideoController(VideoResponseHandle videoResponseHandle) {
        this.videoResponseHandle = videoResponseHandle;
    }

    @RequestMapping("/getVideo")
    public void getVideo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.videoResponseHandle.handleRequest(request, response);
    }
}
```

## 属性定义与注入

> 属性注入使用SpringEl表达进行注入

```yaml
source:
  mysql: locahost:3306/mysql
  redis: localhost:6379
  info: "{name:'xxx',age:'xxx'}" #注入map集合
  messages: liuxc,liuxc1  #注入list集合
```

```java

@RestController
@RequestMapping("/fieldInject")
public class FieldInjectController {
    //使用springEl进行属性的注入
    @Value("${source.mysql}")
    private String mysql;
    @Value("${source.redis}")
    private String redis;
    @Value("#{${source.info}}")
    private Map<String, Object> info;
    @Value("${source.messages}")
    private List<String> messages;

    @RequestMapping("/show")
    public Object show() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("mysql", mysql);
        resultMap.put("redis", redis);
        resultMap.put("info", info);
        resultMap.put("messages", messages);
        return resultMap;
    }
}
```

## @ConfigurationProperties属性的注入方式

> 在整个spring boot里面对于属性的注入的操作除了可以采用原始的spring的方式手工完成
> 也可以基于Bean的方式自动配置完成

```java

@Component
@ConfigurationProperties(prefix = "source")
@Data
public class Source {
    //使用自动bean注入
    private String mysql;
    private String redis;
    private Map<String, Object> info;
    private List<String> messages;
}
```

## 注入对象数据

> 对象实体

```java

@Data
@ConfigurationProperties(prefix = "dept")
@Component
public class Dept {
    private Integer did;
    private String dname;
    private Company company;
    private List<Employee> employees;
}
```

> yml第一种方式配置

```yaml
dept:
  did: 001
  dname: 研发部
  company:
    cid: 002
    cname: 科技无限
  employees:
    - employee:
      eid: 003
      ename: 张三
      job: 开发
    - employee:
      eid: 004
      ename: 李四
      job: 测试
```

> yml第二种方式配置

```yaml
dept:
  did: 001
  dname: 研发部
  company:
    cid: 002
    cname: 科技无限
  employees[0]:
    eid: 003
    ename: 张三
    job: 开发
  employees[1]:
    eid: 004
    ename: 李四
    job: 测试
```

## 自定义配置属性 @PropertySource

```java

@Data
//指定配置文件的路径
@PropertySource(value = "classpath:dept.properties", encoding = "UTF-8")
@ConfigurationProperties(prefix = "dept")
@Component
public class Dept {
    private Integer did;
    private String dname;
    private Company company;
    private List<Employee> employees;
}
```

> 编写dept配置文件property

```properties
dept.did=001
dept.dname=研发部
dept.company.cid=002
dept.company.cname=科技无限
dept.employees[0].eid=003
dept.employees[0].ename=张三
dept.employees[0].job=开发
dept.employees[1].eid=004
dept.employees[1].ename=李四
dept.employees[1].job=测试
```

## 打成war包启动

> 1.在启动类中继承 SpringBootServletInitializer
> 2.修改打包方式 <packaging>war</packaging>

## 整合jetty Web容器

> 1.排除掉默认tomcat容器依赖包

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
    <exclusions>
        <exclusion>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-tomcat</artifactId>
        </exclusion>
    </exclusions>
</dependency>
```

> 2.引用jetty依赖，即整合完毕。整合Undertow容器也是如此

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-jetty</artifactId>
</dependency>
```

## 获取web 内置对象

> 可以通过spring mvc 的形参注入，还可以通过RequestContextHolder这个类获取内置对象。

```java
  //ServletWebRequest requestAttributes = (ServletWebRequest)RequestContextHolder.getRequestAttributes();
//HttpServletRequest request1 = requestAttributes.getRequest();
// HttpServletResponse response1 = requestAttributes.getResponse();
```

## 整合web过滤器

> 方式1使用原生的方式，在启动类中增加@ServletComponentScan注解
> 该方式但是不能设置过滤器的执行顺序

```java
/**
 * 自定义过滤器
 */
@WebFilter(urlPatterns = "/*")//定义要匹配的路径
public class MyFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        System.out.println("****执行MyFilter*****");
        super.doFilter(req, res, chain);
    }
}
```

> 方式2 使用注册Filter的方式FilterRegistrationBean，该方式推荐使用，可以设置执行顺序

```java

/**
 * 配置过滤器
 */
@Configuration
public class WebFilterConfig {
    @Bean
    public FilterRegistrationBean<MyFilter2> registrationMyFilter2() {
        FilterRegistrationBean<MyFilter2> registrationBean = new FilterRegistrationBean<MyFilter2>();
        registrationBean.setFilter(this.getMyFilter2());//设置自定义的filter
        registrationBean.addUrlPatterns("/*");//添加需要拦截的路径
        registrationBean.setOrder(100);//设置顺序
        return registrationBean;
    }

    @Bean
    public MyFilter2 getMyFilter2() {
        return new MyFilter2();
    }
}
```

## 整合web监听器

> 方式一和filter一样使用注解扫描包的方式 ServletComponentScan

```java
/**
 * 自定义监听器
 */
@WebListener
public class MyListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("监听器触发了");
        ServletContextListener.super.contextInitialized(sce);
    }
}
```

> 方式2 使用注册监听器方式 ServletListenerRegistrationBean

## 整合拦截器

* 1.实现HandlerInterceptor接口，自定义拦截器

```java
public class MyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("加载自定义拦截器MyInterceptor");
        return true;
    }
}
```

*2.增加拦截器配置类，配置执行顺序和拦截路径等

```java

@Configuration
public class WebInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**").order(1);
    }

    @Bean
    public MyInterceptor getMyInterceptor() {
        return new MyInterceptor();
    }
}
```

## 整合AOP拦截

*1.引入AOP相关依赖

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

*2.编写AOP拦截类

```java

@Component
@Aspect
public class AOPInterceptor {
    @Around("execution(* com.liuxc.www.microboot.start.service..*.*(..))")
    public Object aroundInterceptor(ProceedingJoinPoint point) throws Throwable {
        System.out.println("开始执行AOPInterceptor。。。");
        return point.proceed(point.getArgs());
    }
}
```

## 整合邮件服务

> pom文件中引入相关配置包，并在yml文件中加入文件相关的配置。

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-mail</artifactId>
</dependency>
```

##全局错误页面
>注册全局错误页面跳转页面
```java
@Configuration
public class ErrorConfig implements ErrorPageRegistrar {
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        registry.addErrorPages(
                new ErrorPage(HttpStatus.NOT_FOUND, "/error/error_404"),// 已经添加了新的错误页
                new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/error_500") // // 已经添加了新的错误页
        );
    }
}
```
>响应rest请求
```java
/**
 * 全局异常处理类
 */
@RestController
@RequestMapping("/error")
public class ErrorController {

    @RequestMapping("/error_404")
    public Object error_404() {
        return getObject("无法找到用户访问路径。");
    }

    @RequestMapping("/error_500")
    public Object error_500() {
        return getObject("服务器内部错误。");
    }

    private Object getObject(String content) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        HttpServletResponse response = requestAttributes.getResponse();
        Map<String, Object> errMap = new HashMap<String, Object>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String element = headerNames.nextElement();
            System.out.println(element + "===>" + request.getHeader(element));
        }
        assert response != null;
        errMap.put("status", response.getStatus()); // 响应编码
        errMap.put("content", content); // 适当性的带一点文字描述
        errMap.put("referer", request.getHeader("Referer")); // 获取之前的来源
        errMap.put("path", request.getRequestURI()); // 访问路径
        return errMap;
    }
}
```
##全局异常处理
```java
/**
 * 全局异常处理类
 */
@ControllerAdvice
public class GlobalExceptionAdvice {
    
    @ResponseBody // 本次的处理是基于Rest风格完成的
    @ExceptionHandler(Exception.class)
    public Object exceptionHandler(Exception exception){
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("message", exception.getMessage()); // 直接获取异常信息
        map.put("status", HttpStatus.INTERNAL_SERVER_ERROR_500); // 设置一个HTTP状态码
        map.put("exception", exception.getClass().getName()); // 获取异常类型
        map.put("path", request.getRequestURI()); // 异常发生的路径
        return map; // 直接返回对象
    }
}
```
##整合actuator
>pom引入监控包
```xml
  <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-actuator</artifactId>
  </dependency>
```
>配置yml文件，打开所有应用监控
> 相关节点访问路径：[https://www.cnblogs.com/JpfBlog66/p/14237909.html](https://www.cnblogs.com/JpfBlog66/p/14237909.html)
```yaml
management:
  endpoints:
    web:
      exposure:
        include: '*' #暴露所有服务
      base-path: /actuator #访问的父路径
  endpoint:
    shutdown:
      enabled: true
  server:
    port: 8081
info:
  app:
    name: SpringBoot微服务
```
>自定义endpoint

|注解	|描述	|类比
|------ |----:  |----
|@Endpoint|	该注解的类可以通过http查看也可以通过jmx查看，他是在两个地方注册	|相当于springmvc中的RestController和JMX中MBean的集合
|@JmxEndpoint	|该注解的类开放的是JMX接口	|相当于JMX中的MBean
|@WebEndpoint	|该注解的类开饭的是http接口	|相当于mvc当中的RestController
|WriteOperation	|http-POST请求	|相当于mvc中的@PostMapping
|@ReadOperation	|http- GET请求	|相当于mvc中的@GetMapping
|@DeleteOpretation	|http- DELETE请求	|相当于mvc中的@DeleteMapping

```java

@Configuration
@Endpoint(id = "myEndPoint")
public class MyEndPoint {
    @ReadOperation
    public Map<String, Object> getMyEndPoint() {
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("message", "hello world");
        return resultMap;
    }
}

```