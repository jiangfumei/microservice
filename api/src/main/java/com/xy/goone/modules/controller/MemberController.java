package com.xy.goone.modules.controller;

import com.cloud.common.base.vo.Result;
import com.cloud.common.exception.HttpRequestException;
import com.cloud.common.util.ResultUtil;
import com.xy.goone.modules.domain.Member;
import com.xy.goone.modules.service.MemberService;
import com.xy.goone.modules.vo.MemberVo;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
@RequestMapping(value = "/member")
@Slf4j
public class MemberController {

    @Resource
    MemberService memberService;

    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ApiOperation(value = "用户登录")
    public MemberVo.Simple login(String phone, String password) {//此处默认前端传来的密码是未加密的
        Member member = memberService.findByPhone(phone).orElseThrow(() -> {
            return HttpRequestException.newI18N("not.find.login.user");
        });
        return new MemberVo.Simple(member);
    }


    @RequestMapping(method = RequestMethod.POST, value = "/registe")
    @ApiOperation(value = "用户注册")
    public MemberVo.Simple registe(String phone, String password) {
        Member member = memberService.findByPhone(phone).orElseThrow(()->{{
            return HttpRequestException.newI18N("not.find.login.user");
        }});

        Member u = new Member();
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setPhone(phone);
        u.setNickName(UUID.randomUUID().toString().replace("-", ""));
        memberService.save(u);
        return new MemberVo.Simple(u);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/modify")
    @ApiOperation(value = "编辑用户信息")
    public MemberVo.Simple modify(Member member) {
        memberService.updateUser(member);
        return new MemberVo.Simple(member);
    }


}
