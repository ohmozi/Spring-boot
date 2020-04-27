package com.study.ohmozi.javaone.infolist.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {

    private String name;

    private String hobby;

    private String address;

    private LocalDate birthday;

    private String job;

    private String phoneNumber;
}
