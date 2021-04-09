package com.zaharov.mykola.uniwersytet.second.junit.service;

import com.zaharov.mykola.uniwersytet.second.dao.NauczycielHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.entity.Nauczyciel;
import com.zaharov.mykola.uniwersytet.second.entity.Student;
import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;
import com.zaharov.mykola.uniwersytet.second.service.NauczycielService;
import com.zaharov.mykola.uniwersytet.second.service.interfaces.INauczycielService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations="classpath:application.properties")
public class NauczycielServiceTest {

    @Mock
    private NauczycielHibernateDAO nauczycielDAO;

    @Mock
    private INauczycielService nauczycielServiceMock;

    @InjectMocks
    private NauczycielService nauczycielService;

    ArgumentCaptor<Nauczyciel> nauczycielArgument =
            ArgumentCaptor.forClass(Nauczyciel.class);

    @Test
    void shouldCreatedNauczycielSuccessfully() {
        ResponseModel responseModel =
                nauczycielService.create(returnNauczycielModelWithoutId());
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        verify(nauczycielDAO, atLeast(1))
                .save(nauczycielArgument.capture());
        verify(nauczycielDAO, atMost(1))
                .save(nauczycielArgument.capture());
    }

    @Test
    void shouldUpdatedNauczycielSuccessfully() {
        final NauczycielModel nauczycielModel =
                NauczycielModel.builder()
                        .id(1L)
                        .imie("Tomasz")
                        .nazwisko("Nowak")
                        .wiek(31)
                        .email("nowak438@gmail.com")
                        .przedmiot("Data Bases")
                        .build();
        ResponseModel responseModel =
                nauczycielService.update(nauczycielModel);
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        verify(nauczycielDAO, atLeast(1))
                .save(nauczycielArgument.capture());
        verify(nauczycielDAO, atMost(1))
                .save(nauczycielArgument.capture());
    }

    @Test
    void shouldReturnGetAll() {
        doReturn(returnListOfNauczycielModels()
        ).when(nauczycielServiceMock)
                .getAll();
        ResponseModel responseModel =
                nauczycielServiceMock.getAll();
        assertNotNull(responseModel);
        assertNotNull(responseModel.getData());
        assertEquals(((List)responseModel.getData()).size(), 3);
    }

    @Test
    void shouldReturnGetAllStudents() {
        doReturn(returnListOfNauczycielModels()
        ).when(nauczycielServiceMock)
                .getAll();
        ResponseModel responseModel =
                nauczycielServiceMock.getAll();
        assertNotNull(responseModel);
        assertNotNull(responseModel.getData());
        assertEquals(((List)responseModel.getData()).size(), 3);
        Optional<Nauczyciel> optionalNauczyciel =
                Optional.of(
                        Nauczyciel.builder()
                                .id(1L)
                                .imie("Przemek")
                                .nazwisko("Rogala")
                                .wiek(31)
                                .email("przemek@gmail.com")
                                .przedmiot("Testowanie")
                                .setOfStudents(returnSetOfStudents())
                                .build()
                );
        doReturn(optionalNauczyciel
        ).when(nauczycielDAO)
                .findById(1L);
        Optional<Nauczyciel> optionalOneNauczyciel =
                nauczycielDAO.findById(1L);
        assertNotNull(optionalOneNauczyciel);
        assertNotNull(optionalOneNauczyciel.get().getSetOfStudents());
        assertEquals(((Set)optionalOneNauczyciel.get().getSetOfStudents()).size(), 3);
    }

    @Test
    void shouldReturGetNauczycielByImie() {
        doReturn(
                ResponseModel.builder()
                        .status("success")
                        .data(
                                Nauczyciel.builder()
                                        .id(1L)
                                        .imie("Przemek")
                                        .nazwisko("Rogala")
                                        .wiek(31)
                                        .email("przemek@gmail.com")
                                        .przedmiot("Testowanie")
                                        .build()
                        )
                        .build()
        ).when(nauczycielServiceMock)
                .findNauczycielByImie("Przemek");
        ResponseModel responseModel =
                nauczycielServiceMock.findNauczycielByImie("Przemek");
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
    }

    @Test
    void shouldReturGetNauczycielByNazwisko() {
        doReturn(
                ResponseModel.builder()
                        .status("success")
                        .data(
                                Nauczyciel.builder()
                                        .id(1L)
                                        .imie("Przemek")
                                        .nazwisko("Rogala")
                                        .wiek(31)
                                        .email("przemek@gmail.com")
                                        .przedmiot("Testowanie")
                                        .build()
                        )
                        .build()
        ).when(nauczycielServiceMock)
                .findNauczycielByNazwisko("Rogala");
        ResponseModel responseModel =
                nauczycielServiceMock.findNauczycielByNazwisko("Rogala");
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
    }

    @Test
    void shouldDeletedNauczycielSuccessfully() {
        Optional<Nauczyciel> optionalNauczyciel =
                Optional.of(
                        Nauczyciel.builder()
                                .id(1L)
                                .imie("Tomasz")
                                .nazwisko("Nowak")
                                .wiek(31)
                                .email("nowak438@gmail.com")
                                .przedmiot("Data Bases")
                                .build()
                );
        doReturn(optionalNauczyciel
        ).when(nauczycielDAO)
                .findById(1L);
        final Nauczyciel nauczyciel = optionalNauczyciel.get();
        ResponseModel responseModel =
                nauczycielService.delete(nauczyciel.getId());
        assertNotNull(nauczyciel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        verify(nauczycielDAO, atLeast(1))
                .delete(nauczycielArgument.capture());
        verify(nauczycielDAO, atMost(1))
                .delete(nauczycielArgument.capture());
    }

    NauczycielModel returnNauczycielModelWithoutId () {
        return NauczycielModel.builder()
                .imie("Pawel")
                .nazwisko("Redrzynski")
                .wiek(20)
                .email("pawel471@gmail.com")
                .przedmiot("Programowanie")
                .build();
    }

    ResponseModel returnListOfNauczycielModels () {
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .data(Arrays.asList(
                        NauczycielModel.builder()
                                .id(1L)
                                .imie("Mikolaj")
                                .nazwisko("Rogala")
                                .wiek(41)
                                .email("mikola@gmail.com")
                                .przedmiot("Data Bases")
                                .students(returnListOfStudentsModels())
                                .build(),
                        NauczycielModel.builder().
                                id(2L)
                                .imie("Karol")
                                .nazwisko("Mazur")
                                .wiek(33)
                                .email("karol@gmail.com")
                                .przedmiot("Front-End")
                                .build(),
                        NauczycielModel.builder().
                                id(3L)
                                .imie("Piotr")
                                .nazwisko("Krol")
                                .wiek(29)
                                .email("piotr@gmail.com")
                                .przedmiot("Back-End")
                                .build()))
                .build();
    }

    Set<Student> returnSetOfStudents() {
        return Set.of(new Student [] {
                Student.builder().
                        id(1L)
                        .imie("Przemek")
                        .nazwisko("Rogala")
                        .wiek(31)
                        .email("przemek@gmail.com")
                        .kierunek("Testowanie")
                        .build(),
                Student.builder().
                        id(2L)
                        .imie("Wiola")
                        .nazwisko("Mazur")
                        .wiek(24)
                        .email("mazur@gmail.com")
                        .kierunek("Front-End")
                        .build(),
                Student.builder().
                        id(3L)
                        .imie("Piotr")
                        .nazwisko("Krol")
                        .wiek(25)
                        .email("piotr@gmail.com")
                        .kierunek("Back-End")
                        .build()
        });
    }

    List<StudentModel> returnListOfStudentsModels() {
        return List.of(new StudentModel [] {
                StudentModel.builder()
                        .id(1L)
                        .imie("Przemek")
                        .nazwisko("Rogala")
                        .wiek(31)
                        .email("przemek@gmail.com")
                        .kierunek("Testowanie")
                        .build(),
                StudentModel.builder()
                        .id(2L)
                        .imie("Wiola")
                        .nazwisko("Mazur")
                        .wiek(24)
                        .email("mazur@gmail.com")
                        .kierunek("Front-End")
                        .build(),
                StudentModel.builder()
                        .id(3L)
                        .imie("Piotr")
                        .nazwisko("Krol")
                        .wiek(25)
                        .email("piotr@gmail.com")
                        .kierunek("Back-End")
                        .build()
        });
    }

    // NEGATIVE

    @Test
    void shouldThrowExceptionOnCreatingNauczyciel() {
        final NauczycielModel nauczycielModelWrongImie =
                NauczycielModel.builder()
                        .imie("Au")
                        .nazwisko("Rogala")
                        .wiek(25)
                        .email("wedertyh48@gmail.com")
                        .przedmiot("Testowanie")
                        .build();
        given(nauczycielServiceMock.create(nauczycielModelWrongImie))
                .willThrow(new IllegalArgumentException());
        try {
            final NauczycielModel nauczycielModel =
                    NauczycielModel.builder()
                            .imie("Au")
                            .nazwisko("Rogala")
                            .wiek(25)
                            .email("wedertyh48@gmail.com")
                            .przedmiot("Testowanie")
                            .build();
            nauczycielServiceMock.create(nauczycielModel);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) { }
        then(nauczycielDAO)
                .should(never())
                .save(nauczycielArgument.capture());
    }

    @Test
    void shouldThrowExceptionOnUpdatingNauczyciel() {
        final NauczycielModel nauczycielModelWithBlankPrzedmiot =
                NauczycielModel.builder()
                        .imie("Przemek")
                        .nazwisko("Rogala")
                        .wiek(33)
                        .email("wedertyh48@gmail.com")
                        .przedmiot("")
                        .build();
        given(nauczycielServiceMock.update(nauczycielModelWithBlankPrzedmiot))
                .willThrow(new IllegalArgumentException());
        try {
            final NauczycielModel nauczycielModel =
                    NauczycielModel.builder()
                            .imie("Przemek")
                            .nazwisko("Rogala")
                            .wiek(33)
                            .email("wedertyh48@gmail.com")
                            .przedmiot("")
                            .build();
            nauczycielServiceMock.update(nauczycielModel);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) { }
        then(nauczycielDAO)
                .should(never())
                .save(nauczycielArgument.capture());
    }

}
