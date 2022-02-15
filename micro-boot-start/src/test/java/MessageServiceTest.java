import com.liuxc.www.microboot.start.MicroBootStartApplication;
import com.liuxc.www.microboot.start.service.IMessageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * spring boot集成junit5
 */
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
