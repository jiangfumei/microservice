package com.cloud.sysadmin.controller;

import com.cloud.common.base.entity.SysUser;
import org.apache.commons.lang.reflect.MethodUtils;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.concurrent.Callable;

@Component
@Transactional
public class CommonDB {

    @Resource
    EntityManager manager;

    public <T> List<T> clearAll(Class<T> entity) {
        List<T> list = manager.createQuery("select o from " + entity.getSimpleName() + " o", entity).getResultList();
        list.forEach(m -> {
            manager.remove(m);
        });
        return list;
    }

    public <T> T run(Callable<T> runnable) throws Exception {
        return runnable.call();
    }

    public void refresh(Object entity) {
        manager.refresh(entity);
    }

    public void delete(Object entity) {
        manager.remove(entity);
    }

    public int deleteById(Object entity) {
        try {
            Object id = MethodUtils.invokeMethod(entity, "getId", new Object[]{});
            return manager.createQuery("DELETE from " + entity.getClass().getSimpleName() + " o where o.id = ?1")
                    .setParameter(1, id).executeUpdate();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(List<?> entities) {
        for (Object entity : entities) {
            manager.remove(entity);
        }
    }

    public void save(Object entity) {
        if (manager.contains(entity)) {
            manager.merge(entity);
        } else {
            try {
                Integer id = (Integer) MethodUtils.invokeMethod(entity, "geHtId", null);
                if (id.intValue() > 0) {
                    manager.merge(entity);
                } else {
                    manager.persist(entity);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Get id error !", e);
            }
        }
    }


    public SysUser randomUser(){
        SysUser user = new SysUser();
        user.setStatus(1);
        user.setUsername("jfm");
        user.setPassword("123");
        user.setEmail("123@qq.com");
        user.setAvatar("http://1.jpg");
        manager.persist(user);
        return user;
    }

}
