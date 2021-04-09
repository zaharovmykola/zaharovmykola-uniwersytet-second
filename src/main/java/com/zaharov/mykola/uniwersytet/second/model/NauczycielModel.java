package com.zaharov.mykola.uniwersytet.second.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = "students")
@ToString(exclude = "students")
@Builder(toBuilder = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@NoArgsConstructor
@AllArgsConstructor
public class NauczycielModel {
    private Long id;
    private String imie;
    private String nazwisko;
    private Integer wiek;
    private String email;
    private String przedmiot;
    public List<StudentModel> students;
}
