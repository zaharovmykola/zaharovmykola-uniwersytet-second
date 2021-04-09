package com.zaharov.mykola.uniwersytet.second.application.controller.methods;

import com.zaharov.mykola.uniwersytet.second.controller.NauczycielController;
import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class NauczycielControllerMethodsTest {

    @Autowired
    private NauczycielController nauczycielController;

    @Test
    public void shouldReturnAllNauczyciele() {
        nauczycielController.getAll(0, 2, "przedmiot", Sort.Direction.ASC);
        ResponseEntity responseEntity = nauczycielController.getAll(0, 2, "przedmiot", Sort.Direction.ASC);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldCreateNauczyciel() {
        NauczycielModel nauczycielModel
                = NauczycielModel.builder()
                        .imie("Cofja")
                        .nazwisko("Nedzielska")
                        .wiek(35)
                        .email("cofja856@gmail.com")
                        .przedmiot("Biologia")
                        .build();
        ResponseEntity responseEntity = nauczycielController.create(nauczycielModel);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @Test
    public void shouldUpdateNauczyciel() {
        NauczycielModel nauczycielModel
                = NauczycielModel.builder()
                        .id(1L)
                        .imie("Tomasz")
                        .nazwisko("Nowak")
                        .wiek(33)
                        .email("tomasz743@gmail.com")
                        .przedmiot("Testowanie")
                        .build();
        nauczycielController.create(nauczycielModel);
        ResponseEntity responseEntityUpdate
                = nauczycielController.update(nauczycielModel.getId(), nauczycielModel);
        assertNotNull(responseEntityUpdate);
        assertEquals(responseEntityUpdate.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldDeleteNauczyciel() {
        NauczycielModel nauczycielModel
                = NauczycielModel.builder()
                        .id(2L)
                        .imie("John")
                        .nazwisko("Doe")
                        .wiek(50)
                        .email("johndoe@demo.org")
                        .przedmiot("Algorithms")
                        .build();
        nauczycielController.create(nauczycielModel);
        ResponseEntity responseEntityDelete
                = nauczycielController.deleteNauczyciel(nauczycielModel.getId());
        assertNotNull(responseEntityDelete);
        assertEquals(responseEntityDelete.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnAllStudents() {
        nauczycielController.getAllStudents(1L);
        ResponseEntity responseEntity = nauczycielController.getAllStudents(1L);
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnNauczycielByImie() {
        nauczycielController.getNauczycielByImie("Vasilii");
        ResponseEntity responseEntity = nauczycielController.getNauczycielByImie("Vasilii");
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

    @Test
    public void shouldReturnNauczycielByNazwisko() {
        nauczycielController.getNauczycielByNazwisko("Gates");
        ResponseEntity responseEntity = nauczycielController.getNauczycielByNazwisko("Gates");
        assertNotNull(responseEntity);
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);
    }

}
