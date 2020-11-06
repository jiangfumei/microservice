package com.cloud.sysadmin;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SysadminApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void pass() {
        String pwd = "123456";
        String encodePwd = BCrypt.hashpw(pwd, BCrypt.gensalt()); // 加密，核心代码
        System.out.println(encodePwd);
        boolean flag = BCrypt.checkpw(pwd, encodePwd); // 验证加密是否正确
        System.out.println(flag);
    }
}
