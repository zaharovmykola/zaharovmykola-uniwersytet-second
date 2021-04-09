package com.zaharov.mykola.uniwersytet.second.application.controller.methods;

import com.zaharov.mykola.uniwersytet.second.controller.StudentController;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class StudentControllerMethodsTest {

    @Autowired
    private StudentController studentController;

    @Test
    public void shouldReturnAllStudents() {
        studentController.getAll(0, 3, "kierunek", Sort.Direction.ASC);
        ResponseEntity responseEntity = studentController.getAll(0, 3, "kierunek", Sort.Direction.ASC);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCreateStudent() {
        StudentModel studentModel
                = StudentModel.builder()
                        .imie("Natali")
                        .nazwisko("Nedzielska")
                        .wiek(21)
                        .email("swerty853@gmail.com")
                        .kierunek("Biologia")
                        .build();
        ResponseEntity responseEntity = studentController.create(studentModel);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void shouldUpdateStudent() {
        StudentModel studentModel
                = StudentModel.builder()
                        .id(1L)
                        .imie("Natali")
                        .nazwisko("Nedzielska")
                        .wiek(19)
                        .email("swerty853@gmail.com")
                        .kierunek("Biologia")
                        .build();
        studentController.create(studentModel);
        ResponseEntity responseEntityUpdate
                = studentController.update(studentModel.getId(), studentModel);
        assertNotNull(responseEntityUpdate);
        assertEquals(responseEntityUpdate.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldDeleteStudent() {
        StudentModel studentModel
                = StudentModel.builder()
                        .id(2L)
                        .imie("Two")
                        .nazwisko("Second")
                        .wiek(23)
                        .email("second@demo.org")
                        .kierunek("Backend Developer")
                        .build();
        studentController.create(studentModel);
        ResponseEntity responseEntityDelete
                = studentController.deleteStudent(studentModel.getId());
        assertNotNull(responseEntityDelete);
        assertEquals(responseEntityDelete.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnAllNauczyciele() {
        studentController.getAllNauczyciele(1L);
        ResponseEntity responseEntity = studentController.getAllNauczyciele(1L);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnStudentByImie() {
        studentController.getStudentByImie("One");
        ResponseEntity responseEntity = studentController.getStudentByImie("One");
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnStudentByNazwisko() {
        studentController.getStudentByNazwisko("Third");
        ResponseEntity responseEntity = studentController.getStudentByNazwisko("Third");
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
