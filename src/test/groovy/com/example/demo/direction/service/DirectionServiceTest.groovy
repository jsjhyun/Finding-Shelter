package com.example.demo.direction.service

import com.example.demo.api.dto.DocumentDto
import com.example.demo.api.service.KakaoCategorySearchService
import com.example.demo.direction.repository.DirectionRepository
import com.example.demo.shelter.dto.ShelterDto
import com.example.demo.shelter.service.ShelterSearchService
import spock.lang.Specification
import spock.lang.Subject

class DirectionServiceTest extends Specification {

    private ShelterSearchService shelterSearchService = Mock()
    private DirectionRepository directionRepository = Mock()
    private Base62Service base62Service = Mock()

    private KakaoCategorySearchService kakaoCategorySearchService = Mock()

    @Subject
    private DirectionService directionService = new DirectionService(
            shelterSearchService, directionRepository,
            base62Service, kakaoCategorySearchService)

    private List<ShelterDto> shelterList

    def setup() {

        shelterList = new ArrayList<>()
        shelterList.addAll(
                ShelterDto.builder()
                        .id(1L)
                        .shelterName("돌곶이온누리약국")
                        .shelterAddress("주소1")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                ShelterDto.builder()
                        .id(2L)
                        .shelterName("호수온누리약국")
                        .shelterAddress("주소2")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
        )
    }

    def "calculateDistance"() {
        given:
        def latitude1 = 37.5505
        def longitude1 = 127.0817

        def latitude2 = 37.541
        def longitude2 = 127.0766
        def result = "1.1"

        when:
        def distance = directionService.calculateDistance(latitude1, longitude1, latitude2, longitude2)

        then:
        String.format("%.1f", distance) == result
    }

    def "buildDirectionList - 결과 값이 거리순 정렬이 되는지 확인"() {
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        shelterSearchService.searchShelterDtoList() >> shelterList

        def results = directionService.buildDirectionList(documentDto)
        then:

        results.size() == 2
        results.get(0).targetShelterName == "호수온누리약국"
        results.get(1).targetShelterName == "돌곶이온누리약국"
        String.format("%.1f", results.get(0).distance) == "1.6"
        String.format("%.1f", results.get(1).distance) == "2.4"
    }

    def "buildDirectionList -  정해진 반경 10km 내에 검색이 되는지 확인"() {
        given:
        shelterList.add(
                ShelterDto.builder()
                        .id(3L)
                        .shelterName("경기약국")
                        .shelterAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        shelterSearchService.searchShelterDtoList() >> shelterList

        def results = directionService.buildDirectionList(documentDto)
        then:

        results.size() == 2
        results.get(0).targetShelterName == "호수온누리약국"
        results.get(1).targetShelterName == "돌곶이온누리약국"
    }
}