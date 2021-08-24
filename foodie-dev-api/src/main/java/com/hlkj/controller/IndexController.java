package com.hlkj.controller;

import com.hlkj.enums.YesOrNo;
import com.hlkj.pojo.Carousel;
import com.hlkj.service.CarouselService;
import com.hlkj.utils.HLKJJSONResult;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@Api(value = "首页展示相关api", tags = "首页展示相关api")
@RestController
@RequestMapping("/index")
public class IndexController {

    @Resource
    private CarouselService carouselService;

    @GetMapping("/carousel")
    public HLKJJSONResult listCarousel(){
        List<Carousel> list = carouselService.list(YesOrNo.YES.type);
        return HLKJJSONResult.ok(list);
    }

}
