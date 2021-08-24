package com.hlkj.impl;

import com.hlkj.mapper.CarouselMapper;
import com.hlkj.pojo.Carousel;
import com.hlkj.service.CarouselService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Resource
    private CarouselMapper carouselMapper;

    @Override
    public List<Carousel> list(Integer isShow) {
        Example example = new Example(Carousel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        example.orderBy("sort").asc();

        return carouselMapper.selectByExample(example);
    }
}
