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
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
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
    public Result<Object> login(@RequestParam String phone,String password) throws Exception {//此处默认前端传来的密码是未加密的

        if (StringUtils.isEmpty(phone)) {
            log.error("phone can not be empty");
            return new ResultUtil<>().setErrorMsg("phone.not.be.empty");
        }
        if (StringUtils.isEmpty(password)) {
            log.error("password can not be empty");
            return new ResultUtil<>().setErrorMsg("password.not.be.empty");
        }
        Member member = new Member();
        member.setPhone(phone);
        member.setPassword(bCryptPasswordEncoder.encode(password));
        //member.setNickName();
        return new ResultUtil<>().setData(null);
    }


    @RequestMapping(value = "/checkUser", method = RequestMethod.POST)
    @ApiOperation(value = "唯一性检查")
    public Result<Object> checkUser(@RequestBody Map<String, String> map) {
        Member u = memberRepository.findByNickNameAndStatus(map.get("nickName"), ApiConstant.STATUS_NORMAL);
        if (u != null) {
            return new ResultUtil<>().setErrorMsg("changed.nickname.has.been.tried");
        }
        Member uu = memberRepository.findByEmailAndStatus(map.get("email"), ApiConstant.STATUS_NORMAL);
        if (uu != null) {
            return new ResultUtil<>().setErrorMsg("the.mailbox.has.been.tried");
        }
        return new ResultUtil<>().setSuccessMsg("success");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/registe")
    @ApiOperation(value = "用户注册")
    public Result<Object> registe(@RequestBody Map<String, String> map) {
        Member uu = memberRepository.findByEmailAndStatus(map.get("email"), ApiConstant.STATUS_NORMAL);
        if (uu != null)
            return new ResultUtil<>().setErrorMsg("the.mailbox.has.been.tried");
        Member uuu = memberRepository.findByNickNameAndStatus(map.get("nickName"), ApiConstant.STATUS_NORMAL);
        if (uuu != null)
            return new ResultUtil<>().setErrorMsg("changed.nickname.has.been.tried");
        Member u = new Member();
        memberRepository.save(u);
        //}
        return new ResultUtil<>().setSuccessMsg("registed.successfully");
    }


}
