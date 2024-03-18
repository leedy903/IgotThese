package org.example.igotthese.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.entity.SupplySeal;

public class SupplySealDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create {
        private Long postId;
        private Long pokemonId;

        public Create(SupplySeal supplySeal) {
            this.postId = supplySeal.getPost().getId();
            this.pokemonId = supplySeal.getPokemon().getId();
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

        public Response(SupplySeal supplySeal) {
            this.id = supplySeal.getId();
            this.postId = supplySeal.getPost().getId();
            this.pokemonName = supplySeal.getPokemon().getPokemonName();
        }
    }
}
