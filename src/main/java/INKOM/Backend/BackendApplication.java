package INKOM.Backend;

import INKOM.Backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	AuthorizationService authorizationService;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}
}
