package com.zaharov.mykola.uniwersytet.second.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = "nauczyciele")
@ToString(exclude = "nauczyciele")
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StudentModel {
    private Long id;
    private String imie;
    private String nazwisko;
    private Integer wiek;
    private String email;
    private String kierunek;
    public List<NauczycielModel> nauczyciele;
}