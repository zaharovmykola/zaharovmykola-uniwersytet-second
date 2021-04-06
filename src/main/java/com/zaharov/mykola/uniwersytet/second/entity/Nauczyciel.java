package com.zaharov.mykola.uniwersytet.second.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="nauczyciele")
@Data
@EqualsAndHashCode(exclude = "setOfStudents")
@ToString(exclude = "setOfStudents")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Nauczyciel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min=3, message = "Imie should be of min size 3")
    @NotBlank(message = "Imie is mandatory")
    @Column(name = "imie", nullable = false, length = 100)
    private String imie;

    @NotBlank(message = "Nazwisko is mandatory")
    @Column(name = "nazwisko", nullable = false, length = 100)
    private String nazwisko;

    @NotNull
    @Min(19)
    @Column(name="wiek", nullable = false)
    private Integer wiek;

    @Pattern(regexp="^([a-zA-Z0-9\\-\\.\\_]+)(\\@)([a-zA-Z0-9\\-\\.]+)(\\.)([a-zA-Z]{2,4})$")
    @NotBlank(message = "Email is mandatory")
    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @NotBlank(message = "Przedmiot is mandatory")
    @Column(name = "przedmiot", nullable = false, length = 100)
    private String przedmiot;

    @ManyToMany
    @JoinTable(name="NauczycieleStudents")
    private Set<Student> setOfStudents = new HashSet<>(0);
}
