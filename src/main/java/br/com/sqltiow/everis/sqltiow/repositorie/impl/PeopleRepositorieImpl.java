package br.com.sqltiow.everis.sqltiow.repositorie.impl;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import br.com.sqltiow.everis.sqltiow.Utilities.Utils;
import br.com.sqltiow.everis.sqltiow.repositorie.PeopleRepository;

@Component
public class PeopleRepositorieImpl implements PeopleRepository {
	private DateTimeFormatter dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

	private final String sqlInsert = "insert into people(name, nameC) values(:name_people, :nameCS)";
	private final String sqlDelete = "delete from people";

	@Autowired
	private Sql2o sql2o;

	private LocalDateTime start;
	private LocalDateTime theEnd;

	@Override
	public void insertLot() {
		try (Connection cnt = sql2o.beginTransaction()) {
			setStart(LocalDateTime.now());

			System.out.println("************************************************");

			Query query = cnt.createQuery(sqlInsert);

			for (int i = 0; i < 7000000; i++)
				query.addParameter("name_people", "Dev_Java").
				      addParameter("nameCS", "Dev_C#").addToBatch();

			query.executeBatch();
			cnt.commit();

			setTheEnd(LocalDateTime.now());

			getResultOperation("INSERT");
		}
	}

	@Override
	public void deleteLot() {
		try (Connection cnt = sql2o.beginTransaction()) {
			setStart(LocalDateTime.now());
			System.out.println("*************************************************");
			
			Query query = cnt.createQuery(sqlDelete);
			int total = query.executeUpdate().getResult();
			cnt.commit();

			setTheEnd(LocalDateTime.now());

			getResultOperation("DELETE");

		}
	}

	private void getResultOperation(String string) {
		Utils utils = new Utils(getStart(), getTheEnd());

		System.out.println(String.format("TRANSAÇÃO DE %s", string));
		System.out.println("****************************************");
		System.out.println(String.format("HORA INICIO DA EXECUÇÃO: %s", getStart().format(dateformatter)));
		System.out.println(String.format("HORA TERMINO DA EXECUÇÃO: %s", getTheEnd().format(dateformatter)));
		System.out.println(String.format("DURAÇÃO: %02d hrs - %02d mins - %02d seg", utils.durationInHour(),
				utils.durationInMinutes(), utils.durationInSegundos()));
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getTheEnd() {
		return theEnd;
	}

	public void setTheEnd(LocalDateTime theEnd) {
		this.theEnd = theEnd;
	}
}