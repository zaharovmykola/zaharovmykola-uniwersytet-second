package com.zaharov.mykola.uniwersytet.second.controller;

import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;
import com.zaharov.mykola.uniwersytet.second.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService service;

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
    public ResponseEntity<ResponseModel> create(@RequestBody StudentModel student) {
        return new ResponseEntity<>(service.create(student), HttpStatus.CREATED);
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<ResponseModel> update(@PathVariable Long id, @RequestBody StudentModel student) {
        student.setId(id);
        return new ResponseEntity<>(service.update(student), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<ResponseModel> deleteStudent(@PathVariable Long id) {
        ResponseModel responseModel = service.delete(id);
        System.out.println(responseModel);
        return new ResponseEntity<>(responseModel, HttpStatus.OK);
    }

    @GetMapping("/{id}/nauczyciele")
    public ResponseEntity<ResponseModel> getAllNauczyciele(@PathVariable Long id) {
        return new ResponseEntity<>(service.getAllNauczyciele(id), HttpStatus.OK);
    }

    @GetMapping("/byImie/{imie}")
    public ResponseEntity<ResponseModel> getStudentByImie(@PathVariable String imie) {
        return new ResponseEntity<>(service.getStudentByImie(imie), HttpStatus.OK);
    }

    @GetMapping("/byNazwisko/{nazwisko}")
    public ResponseEntity<ResponseModel> getStudentByNazwisko(@PathVariable String nazwisko) {
        return new ResponseEntity<>(service.getStudentByNazwisko(nazwisko), HttpStatus.OK);
    }

}
