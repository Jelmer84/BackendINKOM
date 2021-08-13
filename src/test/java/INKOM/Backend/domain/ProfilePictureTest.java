package INKOM.Backend.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class ProfilePictureTest {
    @Test
    public void testMe() {
        ProfilePicture profilePicture = new ProfilePicture();
        profilePicture.setBase64String("1234");
        profilePicture.setUserId(1l);
        assertThat(profilePicture.getBase64String()).isNotBlank();
        assertEquals(profilePicture.getUserId(), 1l);
    }
}