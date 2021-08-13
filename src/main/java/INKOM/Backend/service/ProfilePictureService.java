package INKOM.Backend.service;

import INKOM.Backend.domain.ProfilePicture;
import INKOM.Backend.payload.ProfilePictureDto;
import INKOM.Backend.repository.ProfilePictureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfilePictureService {

    @Autowired
    private ProfilePictureRepository profilePictureRepository;

    public ProfilePicture addImage(ProfilePictureDto profilePictureDto) {

        ProfilePicture image = new ProfilePicture();
        image.setBase64String(profilePictureDto.getBase64String());

        image.setUserId(profilePictureDto.getUserId());
        if(profilePictureDto.getId() > 0)
            image.setId(profilePictureDto.getId());
        return profilePictureRepository.save(image);
    }

    public ProfilePicture fetchImage(long id){
        return profilePictureRepository.findByUserId(id);
    }
}
