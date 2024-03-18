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
@Table(name = "supply_seal")
@ToString(of = {"id", "pokemon"})
public class SupplySeal extends BaseTimeEntity {
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
        pokemon.addSupplySeal(this);
    }

    // 생성 메서드
    public static SupplySeal create(Pokemon pokemon) {
        SupplySeal supplySeal = new SupplySeal();
        supplySeal.confirmPokemon(pokemon);
        return supplySeal;
    }
}
