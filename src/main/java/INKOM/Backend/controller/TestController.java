package INKOM.Backend.controller;

import INKOM.Backend.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @GetMapping("/all")
    public String allAccess() {
        return testService.generatePublicContent();
    }

    @GetMapping("/user")
    public String userAccess() {
        return testService.generateUserContent();
    }

    @GetMapping("/mod")
    public String moderatorAccess() {
        return testService.generateModContent();
    }

    @GetMapping("/admin")
    public String adminAccess() {
        return testService.generateAdminContent();
    }

    @PostMapping("/data")
    public Map<String, Object> data(@RequestBody Map<String, Object> data){
        System.out.println(data);
        return data;
    }
}
