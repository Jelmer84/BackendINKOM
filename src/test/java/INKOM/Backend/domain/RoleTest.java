package INKOM.Backend.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


class RoleTest {

    @Test
    void testMe() {
        Role role = new Role();
        role.setName(ERole.ROLE_ADMIN);
        assertThat(role.getName()).isNotNull();
        assertThat(role.getName()).isEqualTo(ERole.ROLE_ADMIN);

        role.setId(1);
        assertEquals(role.getId(), 1);
    }
}