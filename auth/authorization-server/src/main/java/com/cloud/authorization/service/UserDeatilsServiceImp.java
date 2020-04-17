package com.cloud.authorization.service;

import com.cloud.authorization.entity.Role;
import com.cloud.authorization.entity.User;
import com.cloud.authorization.entity.UserEntity;
import com.cloud.authorization.feign.RoleFeign;
import com.cloud.authorization.feign.UserFeign;
import com.cloud.common.vo.SearchVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserDeatilsServiceImp implements UserDetailsService {
    @Resource
    UserFeign userFeign;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        if (StringUtils.isBlank(userName)){
            //todo: 自定义异常类
            log.error("用户名不能为空");
            //throw new Exception("用户名为空");
        }
        //获取本地用户
        User user = userFeign.getByUsername(userName);
        SearchVo searchVo = new SearchVo();
        searchVo.setId(user.getId());
        List<Role> roles = userFeign.getByUserId(searchVo);
        log.debug("role of the user" + roles);

        Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
        for(Role role: roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            log.debug("role" + role + " role.getRole()" + (role.getName()));
        }
        UserEntity u = new UserEntity(user);
        u.setRoles(roles);

        /*if(user != null){
            //返回oauth2的用户
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    user.getAuthorities()
                    //AuthorityUtils.createAuthorityList(user.getRoles())
            ) ;
        }else{
            throw  new UsernameNotFoundException("用户["+userName+"]不存在");
        }*/
        return new UserEntity(user);
    }
}
