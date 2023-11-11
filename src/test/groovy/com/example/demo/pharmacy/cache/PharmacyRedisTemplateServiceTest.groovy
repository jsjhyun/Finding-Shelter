package com.example.demo.shelter.cache

import com.example.demo.AbstractIntegrationContainerBaseTest
import com.example.demo.shelter.dto.ShelterDto
import org.springframework.beans.factory.annotation.Autowired

class ShelterRedisTemplateServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private ShelterRedisTemplateService shelterRedisTemplateService

    def setup() {
        shelterRedisTemplateService.findAll()
            .forEach(dto -> {
                shelterRedisTemplateService.delete(dto.getId())
            })
    }

    def "save success"() {
        given:
        String shelterName = "name"
        String shelterAddress = "address"
        ShelterDto dto =
                ShelterDto.builder()
                        .id(1L)
                        .shelterName(shelterName)
                        .shelterAddress(shelterAddress)
                        .build()

        when:
        shelterRedisTemplateService.save(dto)
        List<ShelterDto> result = shelterRedisTemplateService.findAll()

        then:
        result.size() == 1
        result.get(0).id == 1L
        result.get(0).shelterName == shelterName
        result.get(0).shelterAddress == shelterAddress
    }

    def "success fail"() {
        given:
        ShelterDto dto =
                ShelterDto.builder()
                        .build()

        when:
        shelterRedisTemplateService.save(dto)
        List<ShelterDto> result = shelterRedisTemplateService.findAll()

        then:
        result.size() == 0
    }

    def "delete"() {
        given:
        String shelterName = "name"
        String shelterAddress = "address"
        ShelterDto dto =
                ShelterDto.builder()
                        .id(1L)
                        .shelterName(shelterName)
                        .shelterAddress(shelterAddress)
                        .build()

        when:
        shelterRedisTemplateService.save(dto)
        shelterRedisTemplateService.delete(dto.getId())
        def result = shelterRedisTemplateService.findAll()

        then:
        result.size() == 0
    }
}
