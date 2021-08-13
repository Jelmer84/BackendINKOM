package INKOM.Backend.domain;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    public void testMe() {
        User user = new User();
        user.setUsername("test");
        user.setFirstName("firstName");
        user.setEmail("email@email.com");
        user.setPassword("password");
        user.setLastname("lastName");
        user.setId(1l);
        assertNotNull(user.getUsername());
        assertEquals("test", user.getUsername());
        assertEquals("firstName", user.getFirstName());
        assertEquals("email@email.com", user.getEmail());
        assertEquals("lastName", user.getLastname());
        assertEquals("password", user.getPassword());
        assertEquals(1l, user.getId());
    }
}