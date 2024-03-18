package org.example.igotthese.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.entity.HoldingSeal;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.entity.User;

public class UserDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create {
        private String userName;
        private List<PokemonDto.Search> holdingSeals = new ArrayList<>();

        public User toEntity(List<HoldingSeal> holdingSeals) {
            User user = User.create(userName, holdingSeals);
            return user;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update {
        private Long userId;
        private String userName;
        private List<PokemonDto.Search> holdingSeals = new ArrayList<>();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long userId;
        private String userName;
        private Long postId;
        private List<PokemonDto.HoldingSealResponse> holdingSeals = new ArrayList<>();

        public Response(User user) {
            this.userId = user.getId();
            this.userName = user.getUserName();
            this.postId = user.getPost() != null ? user.getPost().getId() : null;
            this.holdingSeals = user.getHoldingSeals().stream()
                    .map(holdingSeal -> new PokemonDto.HoldingSealResponse(holdingSeal))
                    .collect(Collectors.toList());
        }
    }

    public static class DeleteResponse {

    }
}
