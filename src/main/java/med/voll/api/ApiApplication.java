package med.voll.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
		/*
		 * TRELLO: https://trello.com/b/O0lGCsKb/api-voll-med
		 * FIGMA: https://www.figma.com/file/N4CgpJqsg7gjbKuDmra3EV/Voll.med?node-id=2-1007
		 *
		 * COMANDOS MYSQL:
		 * mysql -u root -p
		 * create database vollmed_api;
		 * exit
		 * delete from flyway_schema_history where success = 0;
		 * drop database vollmed_api;
		 * create database vollmed_api;
		 */
	}

}
