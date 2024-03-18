package org.example.igotthese.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.entity.HoldingSeal;

public class HoldingSealDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Create {
        private String userName;
        private String pokemonName;
        public Create (HoldingSeal holdingSeal) {
            this.userName = holdingSeal.getUser().getUserName();
            this.pokemonName = holdingSeal.getPokemon().getPokemonName();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String userName;
        private String pokemonName;
        public Response (HoldingSeal holdingSeal) {
            this.id = holdingSeal.getId();
            this.userName = holdingSeal.getUser().getUserName();
            this.pokemonName = holdingSeal.getPokemon().getPokemonName();
        }
    }
}
