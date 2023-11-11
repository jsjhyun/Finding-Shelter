package com.example.demo.shelter.repository

import com.example.demo.AbstractIntegrationContainerBaseTest
import com.example.demo.shelter.entity.Shelter
import com.example.demo.shelter.service.ShelterRepositoryService
import org.springframework.beans.factory.annotation.Autowired

import java.time.LocalDateTime

class ShelterRepositoryTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    ShelterRepositoryService shelterRepositoryService

    @Autowired
    ShelterRepository shelterRepository

    void setup() {
        shelterRepository.deleteAll()
    }

    def "ShelterRepository save"() {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def shelter = Shelter.builder()
                .shelterAddress(address)
                .shelterName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        def entity = shelterRepository.save(shelter)

        then:
        entity.getShelterAddress() == address
        entity.getShelterName() == name
        entity.getLatitude() == latitude
        entity.getLongitude() == longitude
    }

    def "ShelterRepository saveAll"() {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def shelter = Shelter.builder()
                .shelterAddress(address)
                .shelterName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()
        when:
        shelterRepositoryService.saveAll(Arrays.asList(shelter))
        def result = shelterRepository.findAll()

        then:
        result.get(0).getShelterAddress() == address
        result.get(0).getShelterName() == name
        result.get(0).getLatitude() == latitude
        result.get(0).getLongitude() == longitude
    }

    def "ShelterRepository delete"() {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"

        def shelter = Shelter.builder()
                .shelterAddress(address)
                .shelterName(name)
                .build()
        when:
        def entity = shelterRepository.save(shelter)
        shelterRepository.deleteById(entity.getId())

        def result = shelterRepository.findAll()
        then:
        result.size() == 0
    }

    def "ShelterRepository findById"() {

        given:
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"

        def shelter = Shelter.builder()
                .shelterAddress(address)
                .shelterName(name)
                .build()
        when:
        def entity = shelterRepository.save(shelter)
        def result = shelterRepository.findById(entity.getId()).orElse(null)


        then:
        entity.getId() == result.getId()
        entity.getShelterName() == result.getShelterName()
        entity.getShelterAddress() == result.getShelterAddress()
    }


    def "BaseTimeEntity_등록"() {

        given:
        def now = LocalDateTime.now()
        String address = "서울 특별시 성북구 종암동"
        String name = "은혜 약국"

        def shelter = Shelter.builder()
                .shelterAddress(address)
                .shelterName(name)
                .build()
        when:
        shelterRepository.save(shelter)
        def result = shelterRepository.findAll()
        then:
        result.get(0).getCreatedDate().isAfter(now)
        result.get(0).getModifiedDate().isAfter(now)
    }
}
