package music.musicapp.mapper;

import music.musicapp.dto.FriendshipDto;
import music.musicapp.model.user.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendshipMapper {

    FriendshipMapper INSTANCE = Mappers.getMapper(FriendshipMapper.class);

    FriendshipDto friendshipToFriendshipDTO(Friendship friendship);

    Friendship friendshipDTOToFriendship(FriendshipDto friendshipDTO);
}

