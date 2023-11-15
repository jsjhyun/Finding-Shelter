package com.example.demo.shelter.service;

import com.example.demo.api.dto.DocumentDto;
import com.example.demo.api.dto.KakaoApiResponseDto;
import com.example.demo.api.service.KakaoAddressSearchService;
import com.example.demo.direction.dto.OutputDto;
import com.example.demo.direction.entity.Direction;
import com.example.demo.direction.service.Base62Service;
import com.example.demo.direction.service.DirectionService;
import com.github.jknack.handlebars.internal.lang3.ObjectUtils.Null;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShelterRecommendationService {

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    @Value("${shelter.recommendation.base.url}")
    private String baseUrl;

    public Map<Integer, OutputDto> recommendShelterList(String address) {

        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);
        //List로 변환하기 전 여기서 주소를 찾는다.
        if (Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())) {
            log.error("[ShelterRecommendationService.recommendShelterList fail] Input address: {}", address);
            return Map.of();
        }

        DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        //List<Direction> directionList = directionService.buildDirectionListByCategoryApi(documentDto);
        AtomicInteger index = new AtomicInteger(1);
        return directionService.saveAll(directionList)
                .stream()
                .map(this::convertToOutputDto)
                .collect(Collectors.toMap(i -> index.getAndIncrement(), Function.identity()));
    }

    private OutputDto convertToOutputDto(Direction direction) {

        return OutputDto.builder()
                .shelterName(direction.getTargetShelterName())
                .shelterAddress(direction.getTargetAddress())
                .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                .distance(String.format("%.2f km", direction.getDistance()))
                .build();
    }
}