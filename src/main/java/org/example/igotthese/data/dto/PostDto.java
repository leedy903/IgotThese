package org.example.igotthese.data.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.entity.DemandSeal;
import org.example.igotthese.data.entity.Post;
import org.example.igotthese.data.entity.SupplySeal;
import org.example.igotthese.data.entity.User;

public class PostDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create {
        private String userName;
        private List<PokemonDto.Search> demandSeals = new ArrayList<>();
        private List<PokemonDto.Search> supplySeals = new ArrayList<>();
        private Double latitude;
        private Double longitude;

        public Post toEntity(User user, List<DemandSeal> demandSeals, List<SupplySeal> supplySeals) {
            Post post = Post.create(user, demandSeals, supplySeals, latitude, longitude);
            return post;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Update {
        private Long postId;
        private String userName;
        private List<PokemonDto.Search> demandSeals = new ArrayList<>();
        private List<PokemonDto.Search> supplySeals = new ArrayList<>();
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Search {
        private PokemonDto.Search pokemonSearch;
        private Double latitude;
        private Double longitude;
    }


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long postId;
        private String userName;
        private List<PokemonDto.SupplySealResponse> supplySeals = new ArrayList<>();
        private List<PokemonDto.DemandSealResponse> demandSeals = new ArrayList<>();
        private Double latitude;
        private Double longitude;

        public Response(Post post) {
            this.postId = post.getId();
            this.userName = post.getUser().getUserName();
            this.supplySeals = post.getSupplySeals().stream()
                    .map(supplySeal -> new PokemonDto.SupplySealResponse(supplySeal))
                    .collect(Collectors.toList());
            this.demandSeals = post.getDemandSeals().stream()
                    .map(demandSeal -> new PokemonDto.DemandSealResponse(demandSeal))
                    .collect(Collectors.toList());
            this.latitude = post.getLatitude();
            this.longitude = post.getLongitude();
        }
    }
}
