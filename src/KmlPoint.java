import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.util.List;

public class KmlPoint {
    private  String color;
    private List<Coordinate> points;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<Coordinate> getPoints() {
        return points;
    }

    public void setPoints(List<Coordinate> points) {
        this.points = points;
    }
}
