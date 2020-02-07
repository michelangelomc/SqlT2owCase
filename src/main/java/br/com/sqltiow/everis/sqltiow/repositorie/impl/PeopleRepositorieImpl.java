package br.com.sqltiow.everis.sqltiow.repositorie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.sql2o.Connection;
import org.sql2o.Query;
import org.sql2o.Sql2o;

import br.com.sqltiow.everis.sqltiow.repositorie.PeopleRepository;

@Component
public class PeopleRepositorieImpl implements PeopleRepository {

	private final String sqlInsert = "insert into people(name) values(:name_people)";

	@Autowired
	private Sql2o sql2o;

	@Override
	public void insertLot() {
		try (Connection cnt = sql2o.beginTransaction()) {
			Query query = cnt.createQuery(sqlInsert);
			
			for (int i = 0; i < 1000000; i++)
		        query.addParameter("name_people", "Dev_Java" + i).addToBatch();
			
			query.executeBatch();
			cnt.commit();
		}
	}
}
