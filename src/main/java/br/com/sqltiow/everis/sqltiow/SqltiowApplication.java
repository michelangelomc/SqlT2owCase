package br.com.sqltiow.everis.sqltiow;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.sql2o.Sql2o;

import br.com.sqltiow.everis.sqltiow.repositorie.impl.PeopleRepositorieImpl;

@SpringBootApplication
public class SqltiowApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SqltiowApplication.class, args);
	}

	@Bean
	public Sql2o sql2o(DataSource datasouce) {
		return new Sql2o(datasouce);
	}

	@Autowired
	private PeopleRepositorieImpl peopleRepositorieImpl;

	Boolean hasDeleted = true;

	@Override
	public void run(String... args) throws Exception {
		peopleRepositorieImpl.insertLot();
		
		if (hasDeleted)
			peopleRepositorieImpl.deleteLot();
	}
}
