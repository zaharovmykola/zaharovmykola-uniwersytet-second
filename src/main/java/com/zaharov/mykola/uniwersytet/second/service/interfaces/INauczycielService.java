package com.zaharov.mykola.uniwersytet.second.service.interfaces;

import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;

public interface INauczycielService {
    ResponseModel create(NauczycielModel nauczycielModel);
    ResponseModel getAll();
    ResponseModel delete(Long id);
    ResponseModel findNauczycielByImie(String imie);
    ResponseModel findNauczycielByNazwisko(String nazwisko);
    ResponseModel update(NauczycielModel nauczycielModel);
}
