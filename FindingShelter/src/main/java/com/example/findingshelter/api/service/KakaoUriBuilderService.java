package com.example.findingshelter.api.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Slf4j
@Service
public class KakaoUriBuilderService {
    // 요청 uri
    private static final String KAKAO_LOCAL_SEARCH_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    // private static final String KAKAO_LOCAL_CATEGORY_SEARCH_URL = "https://dapi.kakao.com/v2/local/search/category.json";

    // uri 만드는 메소드
    public URI buildUriByAddressSearch(String address) {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_SEARCH_ADDRESS_URL);
        uriBuilder.queryParam("query", address); // 전달 주소값

        URI uri = uriBuilder.build().encode().toUri(); // uri 생성
        log.info("[KakaoAddressSearchService buildUriByAddressSearch] address: {}, uri: {}", address, uri); // 생성 확인

        return uri;
    }

//    public URI buildUriByCategorySearch(double latitude, double longitude, double radius, String category) {
//
//        double meterRadius = radius * 1000;
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(KAKAO_LOCAL_CATEGORY_SEARCH_URL);
//        uriBuilder.queryParam("category_group_code", category);
//        uriBuilder.queryParam("x", longitude);
//        uriBuilder.queryParam("y", latitude);
//        uriBuilder.queryParam("radius", meterRadius);
//        uriBuilder.queryParam("sort","distance");
//
//        URI uri = uriBuilder.build().encode().toUri();
//
//        log.info("[KakaoAddressSearchService buildUriByCategorySearch] uri: {} ", uri);
//
//        return uri;
//    }
}
