package com.example.demo.direction.controller;

import com.example.demo.direction.dto.InputDto;
import com.example.demo.shelter.service.ShelterRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class FormController {
    private final ShelterRecommendationService shelterRecommendationService;

    @GetMapping("/")
    public String main() {
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(@ModelAttribute InputDto inputDto)  {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
        modelAndView.addObject("outputFormList",
                shelterRecommendationService.recommendShelterList(inputDto.getAddress()));
                // 이 메소드가 받은 주소를 통해 추천할 병원을 찾는다
        return modelAndView;
    }
}
