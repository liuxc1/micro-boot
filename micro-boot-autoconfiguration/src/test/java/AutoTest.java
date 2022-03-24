import com.liuxc.www.autostart.AutoConfigurationApplicationStart;
import com.liuxc.www.autostart.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class) //使用junit5测试方式
@WebAppConfiguration//启动web环境
@SpringBootTest(classes = AutoConfigurationApplicationStart.class)//程序启动主类
public class AutoTest {
    @Autowired
    private User user;

    @Test
    public void testEcho() {

        System.out.println("xxx-->" + user);
    }

}

