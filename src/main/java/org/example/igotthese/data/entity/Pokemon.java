package org.example.igotthese.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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
@Table(name = "pokemon")
@ToString(of = {"id", "pokemonName"})
public class Pokemon extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pokemon_id")
    private Long id;
    private String pokemonName;

    @OneToMany(mappedBy = "pokemon")
    private List<HoldingSeal> holdingSeals = new ArrayList<>();
    @OneToMany(mappedBy = "pokemon")
    private List<DemandSeal> demandSeals = new ArrayList<>();
    @OneToMany(mappedBy = "pokemon")
    private List<SupplySeal> supplySeals = new ArrayList<>();

    public void addHoldingSeal(HoldingSeal holdingSeal) {
        this.holdingSeals.add(holdingSeal);
    }
    public void removeHoldingSeal(HoldingSeal holdingSeal) {
        this.holdingSeals.remove(holdingSeal);
    }
    public void addDemandSeal(DemandSeal demandSeal) {
        this.demandSeals.add(demandSeal);
    }
    public void removeDemandSeal(DemandSeal demandSeal) {
        this.demandSeals.remove(demandSeal);
    }
    public void addSupplySeal(SupplySeal supplySeal) {
        this.supplySeals.add(supplySeal);
    }
    public void removeSupplySeal(SupplySeal supplySeal) {
        this.supplySeals.remove(supplySeal);
    }

    public static Pokemon create(String pokemonName) {
        Pokemon pokemon = new Pokemon();
        pokemon.pokemonName = pokemonName;
        return pokemon;
    }
}
