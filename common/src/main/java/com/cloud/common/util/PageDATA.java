package com.cloud.common.util;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class PageDATA<T> {

    final PageImpl<T> page;

    public PageDATA(List<T> list, Pageable pageable, long total) {
        page = new PageImpl(list, pageable, total);
    }

    public int getTotalPages() {
        return page.getTotalPages();
    }

    public long getTotalElements() {
        return page.getTotalElements();
    }

    public long getPageNumber() {
        return page.getPageable().getPageNumber();
    }

    public long getPageSize() {
        return page.getPageable().getPageSize();
    }

    public boolean isLast() {
        return page.isLast();
    }

    public boolean isFirst() {
        return page.isFirst();
    }

    public List<T> getContent() {
        return page.getContent();
    }
}
