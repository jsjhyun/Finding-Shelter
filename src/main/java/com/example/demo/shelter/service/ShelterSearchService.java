package com.example.demo.shelter.service;

import com.example.demo.shelter.cache.ShelterRedisTemplateService;
import com.example.demo.shelter.dto.ShelterDto;
import com.example.demo.shelter.entity.Shelter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShelterSearchService {

    private final ShelterRepositoryService shelterRepositoryService;
    private final ShelterRedisTemplateService shelterRedisTemplateService;

    public List<ShelterDto> searchShelterDtoList() {

        // redis
        List<ShelterDto> shelterDtoList = shelterRedisTemplateService.findAll();
        if(CollectionUtils.isNotEmpty(shelterDtoList)) return shelterDtoList;

        // db
        return shelterRepositoryService.findAll()
                .stream()
                .map(this::convertToShelterDto)
                .collect(Collectors.toList());
    }

    private ShelterDto convertToShelterDto(Shelter shelter) {

        return ShelterDto.builder()
                .id(shelter.getId())
                .shelterName(shelter.getShelterName())
                .shelterAddress(shelter.getShelterAddress())
                .latitude(shelter.getLatitude())
                .longitude(shelter.getLongitude())
                .build();
    }
}