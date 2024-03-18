package org.example.igotthese.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.entity.DemandSeal;

public class DemandSealDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create {
        private Long postId;
        private Long pokemonId;

        public Create(DemandSeal demandSeal) {
            this.postId = demandSeal.getPost().getId();
            this.pokemonId = demandSeal.getPokemon().getId();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private Long postId;
        private String pokemonName;

        public Response(DemandSeal demandSeal) {
            this.id = demandSeal.getId();
            this.postId = demandSeal.getPost().getId();
            this.pokemonName = demandSeal.getPokemon().getPokemonName();
        }
    }
}
