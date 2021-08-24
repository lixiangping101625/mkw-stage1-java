package com.hlkj.service;

import com.hlkj.pojo.Carousel;

import java.util.List;

public interface CarouselService {

    /**
     * 查询轮播图列表
     * @param isShow
     * @return
     */
    List<Carousel> list(Integer isShow);

}
