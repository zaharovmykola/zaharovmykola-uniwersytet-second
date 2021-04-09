package com.zaharov.mykola.uniwersytet.second.controller;

import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.service.NauczycielService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/nauczyciel")
public class NauczycielController {

    @Autowired
    private NauczycielService service;

    @GetMapping("/sortBy:{sortBy}/order:{order}")
    public ResponseEntity<ResponseModel> getAll(
            @RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @PathVariable String sortBy,
            @PathVariable Sort.Direction order
    ) {
        return new ResponseEntity<>(service.getAll(pageNo, pageSize, sortBy, order), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<ResponseModel> create(@RequestBody NauczycielModel nauczyciel) {
        return new ResponseEntity<>(service.create(nauczyciel), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable Long id, @RequestBody NauczycielModel nauczyciel) {
        nauczyciel.setId(id);
        return new ResponseEntity<>(service.update(nauczyciel), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseModel> deleteNauczyciel(@PathVariable Long id) {
        ResponseModel responseModel = service.delete(id);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<ResponseModel> getAllStudents(@PathVariable Long id) {
        return new ResponseEntity<>(service.getAllStudents(id), HttpStatus.OK);
    }

    @GetMapping("/byImie/{imie}")
    public ResponseEntity<ResponseModel> getNauczycielByImie(@PathVariable String imie) {
        return new ResponseEntity<>(service.getNauczycielByImie(imie), HttpStatus.OK);
    }

    @GetMapping("/byNazwisko/{nazwisko}")
    public ResponseEntity<ResponseModel> getNauczycielByNazwisko(@PathVariable String nazwisko) {
        return new ResponseEntity<>(service.getNauczycielByNazwisko(nazwisko), HttpStatus.OK);
    }

}
