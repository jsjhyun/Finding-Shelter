package com.example.demo.shelter.cache;

import com.example.demo.shelter.dto.ShelterDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShelterRedisTemplateService {

    private static final String CACHE_KEY = "PHARMACY";

    private final RedisTemplate<String, Object> redisTemplate;
    private final ObjectMapper objectMapper;

    private HashOperations<String, String, String> hashOperations;

    @PostConstruct
    public void init() {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void save(ShelterDto shelterDto) {
        if(Objects.isNull(shelterDto) || Objects.isNull(shelterDto.getId())) {
            log.error("Required Values must not be null");
            return;
        }

        try {
            hashOperations.put(CACHE_KEY,
                    shelterDto.getId().toString(),
                    serializeShelterDto(shelterDto));
            log.info("[ShelterRedisTemplateService save success] id: {}", shelterDto.getId());
        } catch (Exception e) {
            log.error("[ShelterRedisTemplateService save error] {}", e.getMessage());
        }
    }

    public List<ShelterDto> findAll() {

        try {
            List<ShelterDto> list = new ArrayList<>();
            for (String value : hashOperations.entries(CACHE_KEY).values()) {
                ShelterDto shelterDto = deserializeShelterDto(value);
                list.add(shelterDto);
            }
            return list;

        } catch (Exception e) {
            log.error("[ShelterRedisTemplateService findAll error]: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    public void delete(Long id) {
        hashOperations.delete(CACHE_KEY, String.valueOf(id));
        log.info("[ShelterRedisTemplateService delete]: {} ", id);
    }

    private String serializeShelterDto(ShelterDto shelterDto) throws JsonProcessingException {
        return objectMapper.writeValueAsString(shelterDto);
    }

    private ShelterDto deserializeShelterDto(String value) throws JsonProcessingException {
        return objectMapper.readValue(value, ShelterDto.class);
    }
}