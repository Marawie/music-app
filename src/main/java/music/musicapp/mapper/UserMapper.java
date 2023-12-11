package music.musicapp.mapper;

import music.musicapp.dto.UserDto;
import music.musicapp.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);


    UserDto userToUserDTO(User user);

    User userDTOToUser(UserDto userDTO);
}
