package org.example.igotthese.data.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.igotthese.data.entity.DemandSeal;
import org.example.igotthese.data.entity.HoldingSeal;
import org.example.igotthese.data.entity.Pokemon;
import org.example.igotthese.data.entity.SupplySeal;

public class PokemonDto {
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Search {
        private Long pokemonId;
        private String pokemonName;

        public Search(Pokemon pokemon) {
            pokemonId = pokemon.getId();
            pokemonName = pokemon.getPokemonName();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class HoldingSealResponse {
        private Long pokemonId;
        private String pokemonName;

        public HoldingSealResponse(HoldingSeal holdingSeal) {
            pokemonId = holdingSeal.getPokemon().getId();
            pokemonName = holdingSeal.getPokemon().getPokemonName();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SupplySealResponse {
        private Long pokemonId;
        private String pokemonName;

        public SupplySealResponse(SupplySeal supplySeal) {
            pokemonId = supplySeal.getPokemon().getId();
            pokemonName = supplySeal.getPokemon().getPokemonName();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DemandSealResponse {
        private Long pokemonId;
        private String pokemonName;

        public DemandSealResponse(DemandSeal demandSeal) {
            pokemonId = demandSeal.getPokemon().getId();
            pokemonName = demandSeal.getPokemon().getPokemonName();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long pokemonId;
        private String pokemonName;
    }
}
