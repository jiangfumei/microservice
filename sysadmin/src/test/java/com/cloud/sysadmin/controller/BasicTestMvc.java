package com.cloud.sysadmin.controller;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.persistence.EntityManager;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public abstract class BasicTestMvc extends AbstractTransactionalJUnit4SpringContextTests {

    @Autowired
    protected EntityManager manager;
    @Autowired
    protected CommonDB db;
    protected MockMvc mockMvc;
    @Autowired
    protected WebApplicationContext wac;
    @Before // 7 测试开始前的初始化工作
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build(); // 2
    }

    public void demo() throws Exception {
        TestingAuthenticationToken an = new TestingAuthenticationToken("", "");
        mockMvc.perform(//
                get("/403.html").contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)//
                        .principal(an)//
        )//
                .andExpect(status().is4xxClientError())//
                .andDo(print());//
        //.andExpect(jsonPath("$.result").value("SUCCESS"));
        // {"mobilephone":17161654483}
    }

}
