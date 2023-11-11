package com.example.demo.shelter.service

import com.example.demo.shelter.cache.ShelterRedisTemplateService
import com.example.demo.shelter.dto.ShelterDto
import com.example.demo.shelter.entity.Shelter
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Subject

class ShelterSearchServiceTest extends Specification {

    @Subject
    private ShelterSearchService shelterSearchService

    private ShelterRepositoryService shelterRepositoryService = Mock()
    private ShelterRedisTemplateService shelterRedisTemplateService = Mock()

    private List<Shelter> shelterList

    def setup() {
        shelterSearchService = new ShelterSearchService(shelterRepositoryService, shelterRedisTemplateService)

        shelterList = Lists.newArrayList(
                Shelter.builder()
                        .id(1L)
                        .shelterName("호수온누리약국")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build(),
                Shelter.builder()
                        .id(2L)
                        .shelterName("돌곶이온누리약국")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build()
        )
    }

    def "searchShelterDtoList convert shelterList to shelterDtoList"() {
        when:
        shelterRepositoryService.findAll() >> shelterList
        def result = shelterSearchService.searchShelterDtoList()

        then:
        result.size() == 2
        result.get(0).getId() == 1
        result.get(0).getShelterName() == "호수온누리약국"
        result.get(1).getId() == 2
        result.get(1).getShelterName() == "돌곶이온누리약국"
    }

    def "searchShelterDtoList return empty list if shelterList is empty"() {
        when:
        shelterRepositoryService.findAll() >> []
        def result = shelterSearchService.searchShelterDtoList()

        then:
        result.size() == 0
        result.empty
    }

    def "레디스 장애시 DB를 이용 하여 약국 데이터 조회"() {

        when:
        shelterRedisTemplateService.findAll() >> []
        shelterRepositoryService.findAll() >> shelterList

        def result = shelterSearchService.searchShelterDtoList()

        then:
        result.size() == 2
    }
}
