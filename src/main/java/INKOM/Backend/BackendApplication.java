package INKOM.Backend;

import INKOM.Backend.payload.request.SignupRequest;
import INKOM.Backend.service.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class BackendApplication {

	@Autowired
	AuthorizationService authorizationService;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

// Create super admin, run only the first time.
//	@EventListener(ApplicationReadyEvent.class)
//	public void createTestData(){
//		SignupRequest s = new SignupRequest();
//		s.setEmail("admin@admin.com");
//		s.setFirstName("admin");
//		s.setLastName("admin");
//		s.setPassword("Superadmin123!");
//		//s.setStudentID("");
//		Set<String> roles = new HashSet<>();
//		roles.add("admin");
//		roles.add("mod");
//		roles.add("user");
//		s.setRole(roles);
//		authorizationService.registerUser(s);
//	}


}
