package com.example.demo.direction.controller;

import com.example.demo.direction.dto.InputDto;
import com.example.demo.shelter.service.ShelterRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@Slf4j
public class FormController {
    private final ShelterRecommendationService shelterRecommendationService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto)  {
        log.info("입력된 주소: {}", inputDto.getAddress());

        var shelterList = shelterRecommendationService.recommendShelterList(inputDto.getAddress());
        log.info("추천 대피소 목록: {}", shelterList);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
//        modelAndView.addObject("outputFormList",
//                shelterRecommendationService.recommendShelterList(inputDto.getAddress()));
                // 이 메소드가 받은 주소를 통해 추천할 대피소를 찾는다.
        modelAndView.addObject("outputFormList", shelterList);
        return modelAndView;
    }
}
