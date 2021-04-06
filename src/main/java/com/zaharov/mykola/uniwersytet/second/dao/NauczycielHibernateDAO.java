package com.zaharov.mykola.uniwersytet.second.dao;

import com.zaharov.mykola.uniwersytet.second.entity.Nauczyciel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NauczycielHibernateDAO extends JpaRepository<Nauczyciel, Long>,
        PagingAndSortingRepository<Nauczyciel, Long> {

    Nauczyciel findNauczycielByImie(String imie);
    Nauczyciel findNauczycielByNazwisko(String nazwisko);
}
