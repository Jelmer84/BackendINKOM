package INKOM.Backend.controller;

import INKOM.Backend.exceptions.BadRequestException;
import INKOM.Backend.domain.User;
import INKOM.Backend.exceptions.BadRequestException;
import INKOM.Backend.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import java.net.URI;
import java.util.Map;

@RestController
@CrossOrigin (origins = "*")

@RequestMapping(value = "/api/users")
public class UserController {

    @Autowired
    private UserDetailsServiceImpl userService;

//    @GetMapping(value = "")
//    public ResponseEntity<Object> getUsers() {
//        return ResponseEntity.ok().body(userService.getUsers());
//    }


    @GetMapping(value = "/{email}")
    public ResponseEntity<Object> getUser(@PathVariable("email") String email) {
        return ResponseEntity.ok().body(userService.getUser(email));
    }

//    @PostMapping(value = "")
//    public ResponseEntity<Object> createUser(@RequestBody User user) {
//        String newUsername = userService.createUser(user);
//
//        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{username}")
//                .buildAndExpand(newUsername).toUri();
//
//        return ResponseEntity.created(location).build();
//    }
//
//    @PutMapping(value = "/{username}")
//    public ResponseEntity<Object> updateUser(@PathVariable("username") String username, @RequestBody User user) {
//        userService.updateUser(username, user);
//        return ResponseEntity.noContent().build();
//    }
//
//    @DeleteMapping(value = "/{username}")
//    public ResponseEntity<Object> deleteUser(@PathVariable("username") String username) {
//        userService.deleteUser(username);
//        return ResponseEntity.noContent().build();
//    }
//
//    @GetMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> getUserAuthorities(@PathVariable("username") String username) {
//        return ResponseEntity.ok().body(userService.getAuthorities(username));
//    }
//
//    @PostMapping(value = "/{username}/authorities")
//    public ResponseEntity<Object> addUserAuthority(@PathVariable("username") String username, @RequestBody Map<String, Object> fields) {
//        try {
//            String authorityName = (String) fields.get("authority");
//            userService.addAuthority(username, authorityName);
//            return ResponseEntity.noContent().build();
//        }
//        catch (Exception ex) {
//            throw new BadRequestException();
//        }
//    }
//
//    @DeleteMapping(value = "/{username}/authorities/{authority}")
//    public ResponseEntity<Object> deleteUserAuthority(@PathVariable("username") String username, @PathVariable("authority") String authority) {
//        userService.removeAuthority(username, authority);
//        return ResponseEntity.noContent().build();
//    }

}
