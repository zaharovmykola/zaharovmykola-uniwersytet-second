package com.zaharov.mykola.uniwersytet.second.service;

import com.zaharov.mykola.uniwersytet.second.dao.NauczycielHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.dao.StudentHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.entity.Nauczyciel;
import com.zaharov.mykola.uniwersytet.second.entity.Student;
import com.zaharov.mykola.uniwersytet.second.model.NauczycielModel;
import com.zaharov.mykola.uniwersytet.second.model.ResponseModel;
import com.zaharov.mykola.uniwersytet.second.model.StudentModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class NauczycielService {

    private final NauczycielHibernateDAO nauczycielDao;

    private final StudentHibernateDAO studentDao;

    public NauczycielService(NauczycielHibernateDAO nauczycielDao, StudentHibernateDAO studentDao) {
        this.nauczycielDao = nauczycielDao;
        this.studentDao = studentDao;
    }

    public ResponseModel create(@Valid NauczycielModel nauczycielModel) {
         Nauczyciel nauczyciel =
                Nauczyciel.builder()
                        .imie(nauczycielModel.getImie().trim())
                        .nazwisko(nauczycielModel.getNazwisko().trim())
                        .wiek(nauczycielModel.getWiek())
                        .email(nauczycielModel.getEmail().trim())
                        .przedmiot(nauczycielModel.getPrzedmiot().trim())
                        .build();
        nauczycielDao.save(nauczyciel);
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("Nauczyciel %s Created", nauczyciel.getImie()))
                .build();
    }

    public ResponseModel update(@Valid NauczycielModel nauczycielModel) {
        Nauczyciel nauczyciel =
                Nauczyciel.builder()
                        .id(nauczycielModel.getId())
                        .imie(nauczycielModel.getImie().trim())
                        .nazwisko(nauczycielModel.getNazwisko().trim())
                        .wiek(nauczycielModel.getWiek())
                        .email(nauczycielModel.getEmail().trim())
                        .przedmiot(nauczycielModel.getPrzedmiot().trim())
                        .build();
        nauczycielDao.save(nauczyciel);
        return ResponseModel.builder()
                .status(ResponseModel.SUCCESS_STATUS)
                .message(String.format("Nauczyciel %s Updated", nauczyciel.getImie()))
                .build();
    }

    public ResponseModel delete(Long id) {
        Optional<Nauczyciel> nauczycielOptional = nauczycielDao.findById(id);
        if (nauczycielOptional.isPresent()){
            Nauczyciel nauczyciel = nauczycielOptional.get();
            nauczycielDao.delete(nauczyciel);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("Nauczyciel #%s Deleted", nauczyciel.getImie()))
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Nauczyciel #%d Not Found", id))
                    .build();
        }
    }

    public ResponseModel getAll(Integer pageNo, Integer pageSize, String sortBy, Sort.Direction order) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(order, sortBy));
        Page<Nauczyciel> pagedResult = nauczycielDao.findAll(paging);
        if(pagedResult.hasContent()) {
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(
                            pagedResult.getContent().stream()
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
                    .message("Nauczyciele Not Found")
                    .build();
        }
    }

    public ResponseModel getAllStudents(Long id) {
        Optional<Nauczyciel> nauczycielOptional = nauczycielDao.findById(id);
        if (nauczycielOptional.isPresent()){
            Nauczyciel nauczyciel = nauczycielOptional.get();
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(
                            nauczyciel.getSetOfStudents().stream()
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
                    .message(String.format("Nauczyciel #%d Not Found", id))
                    .build();
        }
    }

    public ResponseModel addStudent(Long nauczycielId, Long studentId) {
        Optional<Nauczyciel> nauczycielOptional = nauczycielDao.findById(nauczycielId);
        Optional<Student> studentOptional = studentDao.findById(studentId);
        if (nauczycielOptional.isPresent() && studentOptional.isPresent()) {
            Nauczyciel nauczyciel = nauczycielOptional.get();
            Student studentEntity = studentOptional.get();
            Student student =
                    Student.builder()
                            .imie(studentEntity.getImie().trim())
                            .nazwisko(studentEntity.getNazwisko().trim())
                            .wiek(studentEntity.getWiek())
                            .email(studentEntity.getEmail().trim())
                            .kierunek(studentEntity.getKierunek().trim())
                            .build();
            Set<Nauczyciel> setOfNauczyciele = student.getSetOfNauczyciele();
            if (setOfNauczyciele != null) {
                setOfNauczyciele.add(nauczyciel);
            } else {
                student.setSetOfNauczyciele(Set.of(nauczyciel));
            }
            studentDao.save(student);
            Set<Student> setOfStudents =
                nauczyciel.getSetOfStudents();
            if (setOfStudents != null) {
                setOfStudents.add(student);
            } else {
                nauczyciel.setSetOfStudents(Set.of(student));
            }
            nauczycielDao.save(nauczyciel);
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .message(String.format("Student %s added to nauczyciel %s", student.getImie(), nauczyciel.getImie()))
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Nauczyciel #%d or Student #%d Not Found", nauczycielId, studentId))
                    .build();
        }
    }

    public ResponseModel getNauczycielByImie(String imie) {
        Nauczyciel nauczyciel = nauczycielDao.findNauczycielByImie(imie);
        if (nauczyciel != null){
            NauczycielModel nauczycielModel =
                    NauczycielModel.builder()
                            .id(nauczyciel.getId())
                            .imie(nauczyciel.getImie())
                            .nazwisko(nauczyciel.getNazwisko())
                            .wiek(nauczyciel.getWiek())
                            .email(nauczyciel.getEmail())
                            .przedmiot(nauczyciel.getPrzedmiot())
                            .build();
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(nauczycielModel)
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Nauczyciel #%s Not Found", imie))
                    .build();
        }
    }

    public ResponseModel getNauczycielByNazwisko(String nazwisko) {
        Nauczyciel nauczyciel = nauczycielDao.findNauczycielByNazwisko(nazwisko);
        if (nauczyciel != null){
            NauczycielModel nauczycielModel =
                    NauczycielModel.builder()
                            .id(nauczyciel.getId())
                            .imie(nauczyciel.getImie())
                            .nazwisko(nauczyciel.getNazwisko())
                            .wiek(nauczyciel.getWiek())
                            .email(nauczyciel.getEmail())
                            .przedmiot(nauczyciel.getPrzedmiot())
                            .build();
            return ResponseModel.builder()
                    .status(ResponseModel.SUCCESS_STATUS)
                    .data(nauczycielModel)
                    .build();
        } else {
            return ResponseModel.builder()
                    .status(ResponseModel.FAIL_STATUS)
                    .message(String.format("Nauczyciel #%s Not Found", nazwisko))
                    .build();
        }
    }

}
