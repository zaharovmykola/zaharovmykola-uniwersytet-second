package com.zaharov.mykola.uniwersytet.second;

import com.zaharov.mykola.uniwersytet.second.dao.NauczycielHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.dao.StudentHibernateDAO;
import com.zaharov.mykola.uniwersytet.second.entity.Nauczyciel;
import com.zaharov.mykola.uniwersytet.second.entity.Student;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import java.util.Set;

@SpringBootApplication
public class SecondApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecondApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData (
			NauczycielHibernateDAO nauczycielDao,
			StudentHibernateDAO studentDao
	) {
		return args -> {
			Nauczyciel nauczycielBillGates =
					Nauczyciel.builder()
							.imie("Bill")
							.nazwisko("Gates")
							.wiek(65)
							.email("billgates@microsoft.com")
							.przedmiot("MS Azure Cloud")
							.build();
			nauczycielDao.save(nauczycielBillGates);
			Nauczyciel nauczycielJohnDoe =
					Nauczyciel.builder()
							.imie("John")
							.nazwisko("Doe")
							.wiek(50)
							.email("johndoe@demo.org")
							.przedmiot("Algorithms")
							.build();
			nauczycielDao.save(nauczycielJohnDoe);
			Nauczyciel nauczycielVasiliiPupkin =
					Nauczyciel.builder()
							.imie("Vasilii")
							.nazwisko("Pupkin")
							.wiek(40)
							.email("vasiliipupkin@test.net")
							.przedmiot("IT Security")
							.build();
			nauczycielDao.save(nauczycielVasiliiPupkin);
			Student student1 =
					Student.builder()
							.imie("One")
							.nazwisko("First")
							.wiek(19)
							.email("first@demo.org")
							.kierunek("Network Administration")
							// .setOfNauczyciele(Set.of(nauczycielBillGates, nauczycielVasiliiPupkin))
							.build();
			System.out.println("mytest " + student1.getSetOfNauczyciele());
			student1.setSetOfNauczyciele(Set.of(nauczycielBillGates, nauczycielVasiliiPupkin));
			studentDao.save(student1);
			Student student2 =
					Student.builder()
							.imie("Two")
							.nazwisko("Second")
							.wiek(23)
							.email("second@demo.org")
							.kierunek("Backend Developer")
							// .setOfNauczyciele(Set.of(nauczycielJohnDoe, nauczycielVasiliiPupkin))
							.build();
			student2.setSetOfNauczyciele(Set.of(nauczycielJohnDoe, nauczycielVasiliiPupkin));
			studentDao.save(student2);
			Student student3 =
					Student.builder()
							.imie("Three")
							.nazwisko("Third")
							.wiek(21)
							.email("third@demo.org")
							.kierunek("Frontend Developer")
							// .setOfNauczyciele(Set.of(nauczycielJohnDoe))
							.build();
			student3.setSetOfNauczyciele(Set.of(nauczycielJohnDoe));
			studentDao.save(student3);
			nauczycielBillGates.setSetOfStudents(Set.of(student1));
			nauczycielJohnDoe.setSetOfStudents(Set.of(student2, student3));
			nauczycielVasiliiPupkin.setSetOfStudents(Set.of(student1, student2));
			nauczycielDao.save(nauczycielBillGates);
			nauczycielDao.save(nauczycielJohnDoe);
			nauczycielDao.save(nauczycielVasiliiPupkin);
		};
	}

}
