package org.example.igotthese.data.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Table(name = "post", indexes = @Index(name = "idx_point", columnList = "location"))
@ToString(of = {"id", "demandSeals", "supplySeals", "latitude", "longitude", "location"})
public class Post extends BaseTimeEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;
    @OneToOne(mappedBy = "post", fetch = FetchType.LAZY)
    private User user;
    @OneToMany(mappedBy = "post")
    private List<DemandSeal> demandSeals = new ArrayList<>();
    @OneToMany(mappedBy = "post")
    private List<SupplySeal> supplySeals = new ArrayList<>();
    @NotNull
    private Double latitude;
    @NotNull
    private Double longitude;
    @Column(columnDefinition = "point srid 4326", nullable = false)
    private Point location;

    public static Point createPoint(Double latitude, Double longitude) {
        GeometryFactory geometryFactory = new GeometryFactory(
                new PrecisionModel(), 4326);
        return geometryFactory.createPoint(new Coordinate(longitude, latitude));
    }

    public void addDemandSeal(DemandSeal demandSeal) {
        demandSeals.add(demandSeal);
        demandSeal.confirmPost(this);
    }

    public void removeDemandSeal(DemandSeal demandSeal) {
        demandSeals.remove(demandSeal);
        demandSeal.confirmPost(null);
    }

    public void addSupplySeal(SupplySeal supplySeal) {
        supplySeals.add(supplySeal);
        supplySeal.confirmPost(this);
    }

    public void removeSupplySeal(SupplySeal supplySeal) {
        supplySeals.remove(supplySeal);
        supplySeal.confirmPost(null);
    }

    public static Post create(User user, List<DemandSeal> demandSeals, List<SupplySeal> supplySeals, Double latitude, Double longitude) {
        Post post = new Post();
        user.setPost(post);
        for (DemandSeal demandSeal : demandSeals) {
            post.addDemandSeal(demandSeal);
        }
        for (SupplySeal supplySeal : supplySeals) {
            post.addSupplySeal(supplySeal);
        }

        post.latitude = latitude;
        post.longitude = longitude;
        post.location = createPoint(latitude, longitude);

        return post;
    }

    public void change(List<DemandSeal> demandSeals, List<SupplySeal> supplySeals, Double latitude, Double longitude) {
        changeDemandSeals(demandSeals);
        changeSupplySeals(supplySeals);
        changeLocation(latitude, longitude);
    }

    public void changeDemandSeals(List<DemandSeal> demandSeals) {
        List<DemandSeal> oldDemandSeals = this.demandSeals.stream()
                .filter(demandSeal -> demandSeals.stream()
                        .noneMatch(Predicate.isEqual(demandSeal)))
                .collect(Collectors.toList());

        List<DemandSeal> newDemandSeals = demandSeals.stream()
                .filter(demandSeal -> this.demandSeals.stream()
                        .noneMatch(Predicate.isEqual(demandSeal)))
                .collect(Collectors.toList());

        for (DemandSeal demandSeal : oldDemandSeals) {
            removeDemandSeal(demandSeal);
        }

        for (DemandSeal demandSeal : newDemandSeals) {
            addDemandSeal(demandSeal);
        }
    }

    public void changeSupplySeals(List<SupplySeal> supplySeals) {
        List<SupplySeal> oldSupplySeals = this.supplySeals.stream()
                .filter(supplySeal -> supplySeals.stream()
                        .noneMatch(Predicate.isEqual(supplySeal)))
                .collect(Collectors.toList());

        List<SupplySeal> newSupplySeals = supplySeals.stream()
                .filter(supplySeal -> this.supplySeals.stream()
                        .noneMatch(Predicate.isEqual(supplySeal)))
                .collect(Collectors.toList());

        for (SupplySeal supplySeal : oldSupplySeals) {
            removeSupplySeal(supplySeal);
        }

        for (SupplySeal supplySeal : newSupplySeals) {
            addSupplySeal(supplySeal);
        }
    }

    public void changeLocation(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = createPoint(latitude, longitude);
    }
}
