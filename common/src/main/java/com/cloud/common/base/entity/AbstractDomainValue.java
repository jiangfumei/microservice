package com.cloud.common.base.entity;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractDomainValue<T> {

    protected final T domain;

    public AbstractDomainValue(T domain) {
        this.domain = domain;
    }

    public T origin() {
        return this.domain;
    }

    public static <T, V extends AbstractDomainValue<T>> List<V> conversion(Class<V> clazz, Collection<T> list) {
        return list.stream().map((T m) -> {
            try {
                return clazz.getConstructor(new Class[] { m.getClass() }).newInstance(m);
            } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                throw new UnsupportedOperationException("Need one Constructor with one <T> param", e);
            }
        }).collect(Collectors.toList());
    }
}
