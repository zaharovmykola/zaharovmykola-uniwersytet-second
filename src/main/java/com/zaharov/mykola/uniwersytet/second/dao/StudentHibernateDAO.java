package com.zaharov.mykola.uniwersytet.second.dao;

import com.zaharov.mykola.uniwersytet.second.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentHibernateDAO extends JpaRepository<Student, Long>,
        PagingAndSortingRepository<Student, Long> {

    Student findStudentByImie(String imie);
    Student findStudentByNazwisko(String nazwisko);
}
