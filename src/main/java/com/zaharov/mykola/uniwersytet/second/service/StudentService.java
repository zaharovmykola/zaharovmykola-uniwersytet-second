package com.zaharov.mykola.uniwersytet.second.service;

import com.zaharov.mykola.uniwersytet.second.dao.StudentHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.entity.Student;
import com.zaharov.mykola.uniwersytet.second.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    @Autowired
    private StudentHibernateDAO studentDao;

    public ResponseModel create(@Valid StudentModel studentModel) {
        Student student =
                Student.builder()
                        .imie(studentModel.getImie().trim())
                        .nazwisko(studentModel.getNazwisko().trim())
                        .wiek(studentModel.getWiek())
                        .email(studentModel.getEmail().trim())
                        .kierunek(studentModel.getKierunek().trim())
                        .build();
        studentDao.save(student);
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("Student %s Created", student.getImie()))
                .build();
    }

    public ResponseModel update(@Valid StudentModel studentModel) {
        Student student =
                Student.builder()
                        .id(studentModel.getId())
                        .imie(studentModel.getImie().trim())
                        .nazwisko(studentModel.getNazwisko().trim())
                        .wiek(studentModel.getWiek())
                        .email(studentModel.getEmail().trim())
                        .kierunek(studentModel.getKierunek().trim())
                        .build();
        studentDao.save(student);
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("Student %s Updated", student.getImie()))
                .build();
    }

    public ResponseModel delete(Long id) {
        Optional<Student> studentOptional = studentDao.findById(id);
        if (studentOptional.isPresent()){
            Student student = studentOptional.get();
            studentDao.delete(student);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("Student #%s Deleted", student.getImie()))
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Student #%d Not Found", id))
                    .build();
        }
    }

    public ResponseModel getAll(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(order, sortBy));
        Page<Student> pagedResult = studentDao.findAll(paging);
        if(pagedResult.hasContent()) {
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(
                        pagedResult.getContent().stream()
                            .map(student -> StudentModel.builder()
                                .id(student.getId())
                                .imie(student.getImie())
                                .nazwisko(student.getNazwisko())
                                .wiek(student.getWiek())
                                .email(student.getEmail())
                                .kierunek(student.getKierunek())
                                .build()
                            )
                            .collect(Collectors.toList())
                    )
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message("Students Not Found")
                    .build();
        }
    }

    public ResponseModel getAllNauczyciele(Long id) {
        Optional<Student> studentOptional = studentDao.findById(id);
        if (studentOptional.isPresent()){
            Student student = studentOptional.get();
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(
                        student.getSetOfNauczyciele().stream()
                            .map((nauczyciel ->
                                NauczycielModel.builder()
                                    .id(nauczyciel.getId())
                                    .imie(nauczyciel.getImie())
                                    .nazwisko(nauczyciel.getNazwisko())
                                    .wiek(nauczyciel.getWiek())
                                    .email(nauczyciel.getEmail())
                                    .przedmiot(nauczyciel.getPrzedmiot())
                                    .build()
                            ))
                            .collect(Collectors.toList())
                    )
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Student #%d Not Found", id))
                    .build();
        }
    }

    public ResponseModel getStudentByImie(String imie) {
        Student student = studentDao.findStudentByImie(imie);
        if (student != null){
            StudentModel studentModel =
                    StudentModel.builder()
                            .id(student.getId())
                            .imie(student.getImie())
                            .nazwisko(student.getNazwisko())
                            .wiek(student.getWiek())
                            .email(student.getEmail())
                            .kierunek(student.getKierunek())
                            .build();
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(studentModel)
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Student #%s Not Found", imie))
                    .build();
        }
    }

    public ResponseModel getStudentByNazwisko(String nazwisko) {
        Student student = studentDao.findStudentByNazwisko(nazwisko);
        if (student != null){
            StudentModel studentModel =
                    StudentModel.builder()
                            .id(student.getId())
                            .imie(student.getImie())
                            .nazwisko(student.getNazwisko())
                            .wiek(student.getWiek())
                            .email(student.getEmail())
                            .kierunek(student.getKierunek())
                            .build();
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(studentModel)
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Student #%s Not Found", nazwisko))
                    .build();
        }
    }

}
