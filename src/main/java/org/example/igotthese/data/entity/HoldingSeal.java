package org.example.igotthese.data.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "holding_seal")
public class HoldingSeal extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    public void confirmUser(User user) {
        this.user = user;
    }

    public void confirmPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        pokemon.addHoldingSeal(this);
    }

    public static HoldingSeal create(Pokemon pokemon) {
        HoldingSeal holdingSeal = new HoldingSeal();
        holdingSeal.confirmPokemon(pokemon);
        return holdingSeal;
    }
}
