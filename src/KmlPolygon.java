import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.util.List;

/**
 * @program: ParseKMLForJava
 * @description:
 * @author: Mr.Yue
 * @create: 2018-12-04 21:12
 **/
public class KmlPolygon {
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
