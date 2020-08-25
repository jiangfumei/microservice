package com.cloud.sysadmin.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UserControllerTest extends BasicTestMvc{

    @Test
    public void getString() throws Exception {
        mockMvc.perform(get("/user/test"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    /*@Test
    public void saveOrUpdate() throws Exception {
        SysUser user = db.randomUser();
        mockMvc.perform(post("/user/saveOrUpdate")
                .param("user",user.toString())
        )
                .andDo(print())
                .andExpect(status().isOk())
        ;

    }*/
}
