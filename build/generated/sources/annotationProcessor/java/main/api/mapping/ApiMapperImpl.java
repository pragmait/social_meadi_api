package api.mapping;

import api.db.User;
import api.db.UserChat;
import api.db.UserPost;
import api.model.CorrespondenceDto;
import api.model.PostDto;
import api.model.requestBody.UserDtoReq;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-06-02T18:27:18+0600",
    comments = "version: 1.5.5.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.6.1.jar, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ApiMapperImpl implements ApiMapper {

    @Override
    public UserDtoReq userToDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoReq userDtoReq = new UserDtoReq();

        userDtoReq.setName( user.getName() );
        userDtoReq.setPassword( user.getPassword() );
        userDtoReq.setEmail( user.getEmail() );

        return userDtoReq;
    }

    @Override
    public CorrespondenceDto userChatToDto(UserChat userChat) {
        if ( userChat == null ) {
            return null;
        }

        CorrespondenceDto correspondenceDto = new CorrespondenceDto();

        correspondenceDto.setNameSender( userChatUserName( userChat ) );
        correspondenceDto.setId( userChat.getId() );
        correspondenceDto.setTextMessage( userChat.getTextMessage() );
        if ( userChat.getTimeDispatch() != null ) {
            correspondenceDto.setTimeDispatch( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( userChat.getTimeDispatch() ) );
        }

        return correspondenceDto;
    }

    @Override
    public List<CorrespondenceDto> userChatsToDto(List<UserChat> userChats) {
        if ( userChats == null ) {
            return null;
        }

        List<CorrespondenceDto> list = new ArrayList<CorrespondenceDto>( userChats.size() );
        for ( UserChat userChat : userChats ) {
            list.add( userChatToDto( userChat ) );
        }

        return list;
    }

    @Override
    public PostDto postToDto(UserPost post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = new PostDto();

        postDto.setUserName( postUserName( post ) );
        postDto.setTextPost( post.getText() );
        postDto.setTitle( post.getTitle() );
        byte[] image = post.getImage();
        if ( image != null ) {
            postDto.setImage( Arrays.copyOf( image, image.length ) );
        }
        postDto.setDateCreation( post.getDateCreation() );

        return postDto;
    }

    @Override
    public List<PostDto> postsToDto(List<UserPost> posts) {
        if ( posts == null ) {
            return null;
        }

        List<PostDto> list = new ArrayList<PostDto>( posts.size() );
        for ( UserPost userPost : posts ) {
            list.add( postToDto( userPost ) );
        }

        return list;
    }

    private String userChatUserName(UserChat userChat) {
        if ( userChat == null ) {
            return null;
        }
        User user = userChat.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private String postUserName(UserPost userPost) {
        if ( userPost == null ) {
            return null;
        }
        User user = userPost.getUser();
        if ( user == null ) {
            return null;
        }
        String name = user.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }
}
