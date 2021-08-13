package INKOM.Backend.controller;

import INKOM.Backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin (origins = "*")

@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

    @GetMapping(value = "/{email}")
    public ResponseEntity<Object> getUser(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.getUser(email));
    }
}
