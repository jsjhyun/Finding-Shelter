package com.example.demo.global.utils;

import com.example.demo.shelter.dto.ShelterDto;
import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class CsvUtils {
    public static List<ShelterDto> convertToShelterDtoList() {

        String file = "./shelter.csv";
        List<List<String>> csvList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            String[] values = null;
            while ((values = csvReader.readNext()) != null) {
                csvList.add(Arrays.asList(values));
            }
        } catch (IOException e) {
            log.error("CsvUtils convertToShelterDtoList Fail: {}", e.getMessage());
        }

        return IntStream.range(1, csvList.size()).mapToObj(index -> {
            List<String> rowList = csvList.get(index);

        return ShelterDto.builder()
                .shelterName(rowList.get(1))
                .shelterAddress(rowList.get(2))
                .latitude(Double.parseDouble(rowList.get(3)))
                .longitude(Double.parseDouble(rowList.get(4)))
                .build();
    }).collect(Collectors.toList());
    }
}