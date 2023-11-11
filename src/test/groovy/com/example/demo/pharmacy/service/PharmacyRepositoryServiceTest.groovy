package com.example.demo.shelter.service

import com.example.demo.AbstractIntegrationContainerBaseTest
import com.example.demo.shelter.entity.Shelter
import com.example.demo.shelter.repository.ShelterRepository
import org.springframework.beans.factory.annotation.Autowired

class ShelterRepositoryServiceTest extends AbstractIntegrationContainerBaseTest {

    @Autowired
    private ShelterRepositoryService shelterRepositoryService

    @Autowired
    ShelterRepository shelterRepository

    void setup() {
        shelterRepository.deleteAll()
    }

    def "ShelterRepository update - dirty checking success"() {

        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def shelter = Shelter.builder()
                .shelterAddress(inputAddress)
                .shelterName(name)
                .build()
        when:
        def entity = shelterRepository.save(shelter)
        shelterRepositoryService.updateAddress(entity.getId(), modifiedAddress)

        def result = shelterRepository.findAll()

        then:
        result.get(0).getShelterAddress() == modifiedAddress
    }

    def "ShelterRepository update - dirty checking fail"() {

        given:
        String inputAddress = "서울 특별시 성북구 종암동"
        String modifiedAddress = "서울 광진구 구의동"
        String name = "은혜 약국"

        def shelter = Shelter.builder()
                .shelterAddress(inputAddress)
                .shelterName(name)
                .build()
        when:
        def entity = shelterRepository.save(shelter)
        shelterRepositoryService.updateAddressWithoutTransaction(entity.getId(), modifiedAddress)

        def result = shelterRepository.findAll()

        then:
        result.get(0).getShelterAddress() == inputAddress
    }


    def "self invocation"() {

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
        shelterRepositoryService.bar(Arrays.asList(shelter))

        then:
        def e = thrown(RuntimeException.class)
        def result = shelterRepositoryService.findAll()
        result.size() == 1 // 트랜잭션이 적용되지 않는다( 롤백 적용 X )
    }

    def "transactional readOnly test"() {

        given:
        String inputAddress = "서울 특별시 성북구"
        String modifiedAddress = "서울 특별시 광진구"
        String name = "은혜 약국"
        double latitude = 36.11
        double longitude = 128.11

        def input = Shelter.builder()
                .shelterAddress(inputAddress)
                .shelterName(name)
                .latitude(latitude)
                .longitude(longitude)
                .build()

        when:
        def shelter = shelterRepository.save(input)
        shelterRepositoryService.startReadOnlyMethod(shelter.id)

        then:
        def result = shelterRepositoryService.findAll()
        result.get(0).getShelterAddress() == inputAddress
    }
}