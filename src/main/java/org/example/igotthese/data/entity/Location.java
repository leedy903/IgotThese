package org.example.igotthese.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name = "location")
public class Location {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(name = "point", columnDefinition = "point", nullable = false)
//    @Column(columnDefinition = "POINT SRID 4326")
    private Point point;

    public Location(Point point) {
        this.point = point;
    }
}
