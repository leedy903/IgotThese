package org.example.igotthese.data.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.example.igotthese.data.repository.LocationRepository;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LocationTest {

    @Autowired LocationRepository locationRepository;

    @Test
    public void createPointWithFactory() throws Exception {
    	//given
        GeometryFactory geometryFactory = new GeometryFactory();

        Double latitude = 36.9483;
        Double longitude = 127.0092;

        final Point point = geometryFactory.createPoint(new Coordinate(latitude, longitude));
        point.setSRID(4326);

        System.out.println("point = " + point);
        final Location location = new Location(point);
        Location save = locationRepository.save(location);

        //when
        String stringPoint = makePoint(latitude, longitude);
        locationRepository.savedWithPoint(stringPoint);

    	//then
    }

    public String makePoint(Double latitude, Double longitude) {
        return String.format("POINT(%s %s)", latitude, longitude);
    }

}