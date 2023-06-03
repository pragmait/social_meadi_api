package api.mapping;


import api.db.User;
import api.db.UserChat;
import api.db.UserPost;
import api.model.CorrespondenceDto;
import api.model.PostDto;
import api.model.requestBody.UserDtoReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ApiMapper {
    UserDtoReq userToDto(User user);

    @Mapping(source = "user.name", target = "nameSender")
    CorrespondenceDto userChatToDto(UserChat userChat);

    List<CorrespondenceDto> userChatsToDto(List<UserChat> userChats);

    @Mapping(source = "user.name", target = "userName")
    @Mapping(source = "text", target = "textPost")
    PostDto postToDto(UserPost post);

    List<PostDto> postsToDto(List<UserPost> posts);

}
