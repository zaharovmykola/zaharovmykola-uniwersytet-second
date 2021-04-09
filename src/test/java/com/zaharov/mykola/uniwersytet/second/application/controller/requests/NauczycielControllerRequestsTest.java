package com.zaharov.mykola.uniwersytet.second.application.controller.requests;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zaharov.mykola.uniwersytet.second.SecondApplication;
import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = SecondApplication.class
)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NauczycielControllerRequestsTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Test
    @Order(1)
    public void givenImie_whenRequestsNauczycielByImie_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                        (
                        "/nauczyciel/byImie/John",
                        ResponseModel.class
                        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LinkedHashMap nauczycielLinkedHashMap =
                (LinkedHashMap) response.getBody().getData();
        assertNotNull(nauczycielLinkedHashMap);
        NauczycielModel nauczycielModel =
                (new ObjectMapper())
                        .convertValue(nauczycielLinkedHashMap, new TypeReference<NauczycielModel>() {}
                );
        assertEquals("John", nauczycielModel.getImie());
    }

    @Test
    @Order(2)
    public void givenNazwisko_whenRequestsNauczycielByNazwisko_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                        (
                        "/nauczyciel/byNazwisko/Pupkin",
                        ResponseModel.class
                        );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        LinkedHashMap nauczycielLinkedHashMap =
                (LinkedHashMap) response.getBody().getData();
        assertNotNull(nauczycielLinkedHashMap);
        NauczycielModel nauczycielModel =
                (new ObjectMapper())
                        .convertValue(nauczycielLinkedHashMap, new TypeReference<NauczycielModel>() {}
                );
        assertEquals("Pupkin", nauczycielModel.getNazwisko());
    }

    @Test
    @Order(3)
    public void givenSortByAndOrder_whenRequestsAllNauczyciele_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                (
                        "/nauczyciel/sortBy:przedmiot/order:ASC",
                        ResponseModel.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayList nauczyciele =
                (ArrayList) response.getBody().getData();
        assertNotNull(nauczyciele);
        assertEquals(3, nauczyciele.size());
    }

    @Test
    @Order(4)
    public void givenNuaczycielId_whenRequestsGetAllStudents_thenCorrect() throws Exception {
        ResponseEntity<ResponseModel> response =
                testRestTemplate.getForEntity
                (
                        "/nauczyciel/3/students",
                        ResponseModel.class
                );
        assertEquals(HttpStatus.OK, response.getStatusCode());
        ArrayList nauczycielStudents =
                (ArrayList) response.getBody().getData();
        assertNotNull(nauczycielStudents);
        assertEquals(2, nauczycielStudents.size());
    }

}
