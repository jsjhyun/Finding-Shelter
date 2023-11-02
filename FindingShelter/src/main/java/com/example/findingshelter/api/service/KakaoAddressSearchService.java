package com.example.findingshelter.api.service;


import com.example.findingshelter.api.dto.KakaoApiResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@Slf4j
@Service
@RequiredArgsConstructor
public class KakaoAddressSearchService {

    private final RestTemplate restTemplate;
    private final KakaoUriBuilderService kakaoUriBuilderService;

    @Value("${kakao.rest.api.key}")
    private String kakaoRestApiKey; // Header에 담기 위한 key 값

//    @Retryable(
//            value = {RuntimeException.class},
//            maxAttempts = 2,
//            backoff = @Backoff(delay = 2000)
//    )

    public KakaoApiResponseDto requestAddressSearch(String address) {

        if(ObjectUtils.isEmpty(address)) return null; // null 값 체크

        URI uri = kakaoUriBuilderService.buildUriByAddressSearch(address);  // 주소값 전달

        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.AUTHORIZATION, "KakaoAK " + kakaoRestApiKey);
        HttpEntity httpEntity = new HttpEntity<>(headers);
        // Kakao api 호출
        return restTemplate.exchange(uri, HttpMethod.GET, httpEntity, KakaoApiResponseDto.class).getBody();
    }

//    @Recover
//    public KakaoApiResponseDto recover(RuntimeException e, String address) {
//        log.error("All the retries failed. address: {}, error : {}", address, e.getMessage());
//        return null;
//    }
}