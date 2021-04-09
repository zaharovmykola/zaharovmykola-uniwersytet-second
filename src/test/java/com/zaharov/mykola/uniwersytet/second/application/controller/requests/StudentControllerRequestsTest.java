package com.zaharov.mykola.uniwersytet.second.application.controller.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaharov.mykola.uniwersytet.second.SecondApplication;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SecondApplication.class
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StudentControllerRequestsTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    public void givenImie_whenRequestsStudentByImie_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                (
                        "/student/byImie/Two",
                        ResponseModel.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LinkedHashMap studentLinkedHashMap =
                (LinkedHashMap) response.getBody().getData();
        assertNotNull(studentLinkedHashMap);
        StudentModel studentModel =
                (new ObjectMapper())
                        .convertValue(studentLinkedHashMap, new TypeReference<StudentModel>() {}
                );
        assertEquals("Two", studentModel.getImie());
    }

    @Test
    @Order(2)
    public void givenNazwisko_whenRequestsStudentByNazwisko_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                (
                        "/student/byNazwisko/Third",
                        ResponseModel.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LinkedHashMap studentLinkedHashMap =
                (LinkedHashMap) response.getBody().getData();
        assertNotNull(studentLinkedHashMap);
        StudentModel studentModel =
                (new ObjectMapper())
                        .convertValue(studentLinkedHashMap, new TypeReference<StudentModel>() {}
                );
        assertEquals("Third", studentModel.getNazwisko());
    }

    @Test
    @Order(3)
    public void givenSortByAndOrder_whenRequestsAllStudents_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                (
                        "/student/sortBy:kierunek/order:ASC",
                        ResponseModel.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayList students =
                (ArrayList) response.getBody().getData();
        assertNotNull(students);
        assertEquals(3, students.size());
    }

    @Test
    @Order(4)
    public void givenStudentId_whenRequestsGetAllNauczyciele_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                (
                        "/student/2/nauczyciele",
                        ResponseModel.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayList studentNauczyciele =
                (ArrayList) response.getBody().getData();
        assertNotNull(studentNauczyciele);
        assertEquals(2, studentNauczyciele.size());
    }

}


