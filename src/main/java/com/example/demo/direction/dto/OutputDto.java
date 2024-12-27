package com.example.demo.direction.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OutputDto {

    private String shelterName;    // 대피소 명
    private String shelterAddress; // 대피소 주소
    private String directionUrl;    // 길안내 url
    private String roadViewUrl;     // 로드뷰 url
    private String distance;        // 고객 주소와 대피소 주소의 거리
}
