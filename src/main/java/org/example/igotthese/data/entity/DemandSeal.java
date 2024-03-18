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
import lombok.ToString;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "demand_seal")
@ToString(of = {"id", "pokemon"})
public class DemandSeal extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pokemon_id")
    private Pokemon pokemon;

    public void confirmPost(Post post) {
        this.post = post;
    }

    public void confirmPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
        pokemon.addDemandSeal(this);
    }

    public static DemandSeal create(Pokemon pokemon) {
        DemandSeal demandSeal = new DemandSeal();
        demandSeal.confirmPokemon(pokemon);
        return demandSeal;
    }
}
