package org.example.igotthese.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.dto.PokemonDto.HoldingSealResponse;
import org.example.igotthese.data.entity.User;

public class BookDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MyBook {
        private Long userId;
        private String userName;
        private List<HoldingSealResponse> holdingSeals = new ArrayList<>();
        public MyBook(User user) {
            this.userId = user.getId();
            this.userName = user.getUserName();
            this.holdingSeals = user.getHoldingSeals().stream()
                    .map(holdingSeal -> new PokemonDto.HoldingSealResponse(holdingSeal))
                    .collect(Collectors.toList());
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Ranker {
        private Long userId;
        private String userName;
        private Integer count;
        public Ranker(User user) {
            this.userId = user.getId();
            this.userName = user.getUserName();
            this.count = user.getHoldingSeals().size();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private MyBook myBook;
        private List<Ranker> rankers;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Ranking {
        List<MyBook> rankersBook = new ArrayList<>();
    }
}
