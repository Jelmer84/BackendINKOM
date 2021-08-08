package INKOM.Backend.repository;

import INKOM.Backend.domain.ProfilePicture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
    ProfilePicture findByUserId(long id);
}
