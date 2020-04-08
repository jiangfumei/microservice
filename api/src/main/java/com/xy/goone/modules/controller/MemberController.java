package com.xy.goone.modules.controller;

import com.cloud.common.base.api.ApiConstant;
import com.cloud.common.base.vo.Result;
import com.cloud.common.util.ResultUtil;
import com.xy.goone.modules.dao.MemberRepository;
import com.xy.goone.modules.domain.Member;
import com.xy.goone.modules.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
/*@RequestMapping(value)*/
@Slf4j
public class MemberController {

    @Resource
    MemberService memberService;

    @Resource
    MemberRepository memberRepository;

    @Resource
    BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(method = RequestMethod.POST, value = "/login")
    @ApiOperation(value = "用户登录")
    public Result<Object> login(String phone,String password){//此处默认前端传来的密码是未加密的
        //todo:: oauh2 鉴权，返回token
        if (StringUtils.isEmpty(phone)) {
            log.error("phone can not be empty");
            return new ResultUtil<>().setErrorMsg("phone.not.be.empty");
        }
        if (StringUtils.isEmpty(password)) {
            log.error("password can not be empty");
            return new ResultUtil<>().setErrorMsg("password.not.be.empty");
        }

        return new ResultUtil<>().setData(null);
    }




    @RequestMapping(method = RequestMethod.POST, value = "/registe")
    @ApiOperation(value = "用户注册")
    public Result<Object> registe(String phone,String password ) {
        Member member = memberRepository.findByPhoneAndStatus(phone, ApiConstant.STATUS_NORMAL);
        if (member != null)
            return new ResultUtil<>().setErrorMsg("the.mailbox.has.been.tried");
        Member u = new Member();
        u.setPassword(bCryptPasswordEncoder.encode(password));
        u.setPhone(phone);
        u.setNickName(UUID.randomUUID().toString().replace("-",""));
        memberRepository.save(u);
        return new ResultUtil<>().setSuccessMsg("registed.successfully");
    }

    @RequestMapping(method = RequestMethod.POST,value = "/modifySetings")
    @ApiOperation(value = "编辑用户信息")
    public Result<Object> modify(Member member){

        return ResultUtil.success("successfully");
    }



}
