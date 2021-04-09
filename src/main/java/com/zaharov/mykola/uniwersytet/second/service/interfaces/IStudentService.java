package com.zaharov.mykola.uniwersytet.second.service.interfaces;

import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;

public interface IStudentService {
    ResponseModel create(StudentModel studentModel);
    ResponseModel getAll();
    ResponseModel delete(Long id);
    ResponseModel findStudentByImie(String imie);
    ResponseModel findStudentByNazwisko(String nazwisko);
    ResponseModel update(StudentModel studentModel);
}
