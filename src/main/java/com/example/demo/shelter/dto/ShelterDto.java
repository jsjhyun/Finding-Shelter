package com.example.demo.shelter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShelterDto {

    private Long id;
    private String shelterName;
    private String shelterAddress;
    private double latitude;
    private double longitude;
}
