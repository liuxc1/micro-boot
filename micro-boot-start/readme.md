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

>访问链的模式:在每个setter方法里面都返回有当前对象实例，这样就可以直接基于代码链的方式配置。
>在大部分情况下基于反射的模式来获取setter方法对象都是采用void返回值类型来进行获取的，所以即便此时有了返回值，那么程序也无法正常的采用代码链的进行处理。


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
>配置前缀操作,在生成get,set方法是会去掉根据配置的前缀生成方法
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
>帮助用户自动进行异常捕获
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
>不推荐将spring Boot中的Jackson更换为FastJson
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
>引用maven相关依赖包
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
>2.controller返回图片流数据
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
>1.创建视频流处理handle
```java
@Component
public class VideoResponseHandle extends ResourceHttpRequestHandler {
    @Override
    protected Resource getResource(HttpServletRequest request) throws IOException {
        return new ClassPathResource("video/1111.mp4");
    }
}
```
>2.创建响应controller
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
##属性定义与注入
>属性注入使用SpringEl表达进行注入
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
>在整个spring boot里面对于属性的注入的操作除了可以采用原始的spring的方式手工完成
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