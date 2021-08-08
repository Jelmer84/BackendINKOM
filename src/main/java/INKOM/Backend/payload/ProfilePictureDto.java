package INKOM.Backend.payload;


import lombok.Data;

@Data
public class ProfilePictureDto {

    private String base64String;
    private long id;
    private long userId;
}

//LOMBOK of GETTERS & SETTERS??