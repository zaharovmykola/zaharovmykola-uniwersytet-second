package com.zaharov.mykola.uniwersytet.second.junit.service;

import com.zaharov.mykola.uniwersytet.second.dao.StudentHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.entity.Nauczyciel;
import com.zaharov.mykola.uniwersytet.second.entity.Student;
import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;
import com.zaharov.mykola.uniwersytet.second.service.StudentService;
import com.zaharov.mykola.uniwersytet.second.service.interfaces.IStudentService;
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
public class StudentServiceTest {

    @Mock
    private StudentHibernateDAO studentDAO;

    @Mock
    private IStudentService studentServiceMock;

    @InjectMocks
    private StudentService studentService;

    ArgumentCaptor<Student> studentArgument =
            ArgumentCaptor.forClass(Student.class);

    @Test
    void shouldCreatedStudentSuccessfully() {
        ResponseModel responseModel =
                studentService.create(returnStudentModelWithoutId());
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        verify(studentDAO, atLeast(1))
                .save(studentArgument.capture());
        verify(studentDAO, atMost(1))
                .save(studentArgument.capture());
    }

    @Test
    void shouldUpdatedStudentSuccessfully() {
        final StudentModel productModel =
                StudentModel.builder()
                        .id(1L)
                        .imie("Natali")
                        .nazwisko("Nedzielska")
                        .wiek(21)
                        .email("swerty853@gmail.com")
                        .kierunek("Biologia")
                        .build();
        ResponseModel responseModel =
                studentService.update(productModel);
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        verify(studentDAO, atLeast(1))
                .save(studentArgument.capture());
        verify(studentDAO, atMost(1))
                .save(studentArgument.capture());
    }

    @Test
    void shouldReturnGetAll() {
        doReturn(returnListOfStudentModels()
        ).when(studentServiceMock)
                .getAll();
        ResponseModel responseModel =
                studentServiceMock.getAll();
        assertNotNull(responseModel);
        assertNotNull(responseModel.getData());
        assertEquals(((List)responseModel.getData()).size(), 3);
    }

    @Test
    void shouldReturnGetAllNauczyciele() {
        doReturn(returnListOfStudentModels()
        ).when(studentServiceMock)
                .getAll();
        ResponseModel responseModel =
                studentServiceMock.getAll();
        assertNotNull(responseModel);
        assertNotNull(responseModel.getData());
        assertEquals(((List)responseModel.getData()).size(), 3);
        Optional<Student> optionalStudent =
                Optional.of(
                        Student.builder()
                                .id(1L)
                                .imie("Przemek")
                                .nazwisko("Rogala")
                                .wiek(31)
                                .email("przemek@gmail.com")
                                .kierunek("Testowanie")
                                .setOfNauczyciele(returnSetOfNauczyciele())
                                .build()
                );
        doReturn(optionalStudent
        ).when(studentDAO)
                .findById(1L);

        Optional<Student> optionalOneStudent =
                studentDAO.findById(1L);
        assertNotNull(optionalOneStudent);
        assertNotNull(optionalOneStudent.get().getSetOfNauczyciele());
        assertEquals(((Set)optionalOneStudent.get().getSetOfNauczyciele()).size(), 3);
    }

    @Test
    void shouldReturGetStudentByImie() {
        doReturn(
                ResponseModel.builder()
                        .status("success")
                        .data(
                                Student.builder()
                                        .id(1L)
                                        .imie("Przemek")
                                        .nazwisko("Rogala")
                                        .wiek(31)
                                        .email("przemek@gmail.com")
                                        .kierunek("Testowanie")
                                        .build()
                        )
                        .build()
        ).when(studentServiceMock)
                .findStudentByImie("Przemek");
        ResponseModel responseModel =
                studentServiceMock.findStudentByImie("Przemek");
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
    }

    @Test
    void shouldReturGetStudentByNazwisko() {
        doReturn(
                ResponseModel.builder()
                        .status("success")
                        .data(
                                Student.builder()
                                        .id(1L)
                                        .imie("Przemek")
                                        .nazwisko("Rogala")
                                        .wiek(31)
                                        .email("przemek@gmail.com")
                                        .kierunek("Testowanie")
                                        .build()
                        )
                        .build()
        ).when(studentServiceMock)
                .findStudentByNazwisko("Rogala");
        ResponseModel responseModel =
                studentServiceMock.findStudentByNazwisko("Rogala");
        assertNotNull(responseModel);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
    }

    @Test
    void shouldDeletedStudentSuccessfully() {
        Optional<Student> optionalStudent =
                Optional.of(
                        Student.builder()
                                .id(1L)
                                .imie("Natali")
                                .nazwisko("Nedzielska")
                                .wiek(21)
                                .email("swerty853@gmail.com")
                                .kierunek("Biologia")
                                .build()
                );
        doReturn(optionalStudent
        ).when(studentDAO)
                .findById(1L);
        final Student student = optionalStudent.get();
        ResponseModel responseModel =
                studentService.delete(student.getId());
        assertNotNull(student);
        assertEquals(ResponseModel.SUCCESS_STATUS, responseModel.getStatus());
        verify(studentDAO, atLeast(1))
                .delete(studentArgument.capture());
        verify(studentDAO, atMost(1))
                .delete(studentArgument.capture());
    }

    StudentModel returnStudentModelWithoutId () {
        return StudentModel.builder()
                .imie("Przemek")
                .nazwisko("Rogala")
                .wiek(20)
                .email("wedertyh48@gmail.com")
                .kierunek("Testowanie")
                .build();
    }

    ResponseModel returnListOfStudentModels () {
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .data(Arrays.asList(
                        StudentModel.builder()
                                .id(1L)
                                .imie("Przemek")
                                .nazwisko("Rogala")
                                .wiek(31)
                                .email("przemek@gmail.com")
                                .kierunek("Testowanie")
                                .nauczyciele(returnListOfNauczycielModels())
                                .build(),
                        StudentModel.builder().
                                id(2L)
                                .imie("Wiola")
                                .nazwisko("Mazur")
                                .wiek(24)
                                .email("mazur@gmail.com")
                                .kierunek("Front-End")
                                .build(),
                        StudentModel.builder().
                                id(3L)
                                .imie("Piotr")
                                .nazwisko("Krol")
                                .wiek(25)
                                .email("piotr@gmail.com")
                                .kierunek("Back-End")
                                .build()))
                .build();
    }

    Set<Nauczyciel> returnSetOfNauczyciele () {
        return Set.of(new Nauczyciel [] {
                Nauczyciel.builder().
                        id(1L)
                        .imie("Mikolaj")
                        .nazwisko("Rogala")
                        .wiek(41)
                        .email("mikola@gmail.com")
                        .przedmiot("Data Bases")
                        .build(),
                Nauczyciel.builder().
                        id(2L)
                        .imie("Karol")
                        .nazwisko("Mazur")
                        .wiek(33)
                        .email("karol@gmail.com")
                        .przedmiot("Front-End")
                        .build(),
                Nauczyciel.builder().
                        id(3L)
                        .imie("Piotr")
                        .nazwisko("Krol")
                        .wiek(29)
                        .email("piotr@gmail.com")
                        .przedmiot("Back-End")
                        .build()
        });
    }

    List<NauczycielModel> returnListOfNauczycielModels () {
        return List.of(new NauczycielModel [] {
                NauczycielModel.builder()
                        .id(1L)
                        .imie("Mikolaj")
                        .nazwisko("Rogala")
                        .wiek(41)
                        .email("mikola@gmail.com")
                        .przedmiot("Data Bases")
                        .build(),
                NauczycielModel.builder()
                        .id(2L)
                        .imie("Karol")
                        .nazwisko("Mazur")
                        .wiek(33)
                        .email("karol@gmail.com")
                        .przedmiot("Front-End")
                        .build(),
                NauczycielModel.builder()
                        .id(3L)
                        .imie("Piotr")
                        .nazwisko("Krol")
                        .wiek(29)
                        .email("piotr@gmail.com")
                        .przedmiot("Back-End")
                        .build()
        });
    }

    // NEGATIVE

    @Test
    void shouldThrowExceptionOnCreatingStudent() {
        final StudentModel studentModelWrongWiek =
                StudentModel.builder()
                        .imie("Przemek")
                        .nazwisko("Rogala")
                        .wiek(15)
                        .email("wedertyh48@gmail.com")
                        .kierunek("Testowanie")
                        .build();
        given(studentServiceMock.create(studentModelWrongWiek))
                .willThrow(new IllegalArgumentException());
        try {
            final StudentModel studentModel =
                    StudentModel.builder()
                            .imie("Przemek")
                            .nazwisko("Rogala")
                            .wiek(15)
                            .email("wedertyh48@gmail.com")
                            .kierunek("Testowanie")
                            .build();
            studentServiceMock.create(studentModel);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) { }
        then(studentDAO)
                .should(never())
                .save(studentArgument.capture());
    }

    @Test
    void shouldThrowExceptionOnUpdatingStudent() {
        final StudentModel studentModelWrongEmail =
                StudentModel.builder()
                        .imie("Przemek")
                        .nazwisko("Rogala")
                        .wiek(15)
                        .email("wedertyhgmailcom")
                        .kierunek("Testowanie")
                        .build();
        given(studentServiceMock.update(studentModelWrongEmail))
                .willThrow(new IllegalArgumentException());
        try {
            final StudentModel studentModel =
                    StudentModel.builder()
                            .imie("Przemek")
                            .nazwisko("Rogala")
                            .wiek(15)
                            .email("wedertyhgmailcom")
                            .kierunek("Testowanie")
                            .build();
            studentServiceMock.update(studentModel);
            fail("Should throw an IllegalArgumentException");
        } catch (IllegalArgumentException ex) { }
        then(studentDAO)
                .should(never())
                .save(studentArgument.capture());
    }

}
