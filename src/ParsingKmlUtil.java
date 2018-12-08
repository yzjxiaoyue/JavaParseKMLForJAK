//import de.micromata.opengis.kml.v_2_2_0.*;
//
//import java.io.File;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @program: ParseKMLForJava
// * @description:
// * @author: Mr.Yue
// * @create: 2018-12-04 21:55
// **/
//class ParsingKmlUtil {
//    //以下三行都是自定义的KML类，用于获取名称name、所有点points、样式颜色color
//    private static List<KmlPoint> kmlPointList = new ArrayList<>();
//    private static List<KmlLine> kmlLineList = new ArrayList<>();
//    private static List<KmlPolygon> kmlPolygonList = new ArrayList<>();
//    private static KmlProperty kmlProperty = new KmlProperty();
//
//    public static KmlProperty parseKmlForJAK(File file) {
//        Kml kml = Kml.unmarshal(file);
//        Feature feature = kml.getFeature();
//        parseFeature(feature);
//        kmlProperty.setKmlPoints(kmlPointList);
//        kmlProperty.setKmlLines(kmlLineList);
//        kmlProperty.setKmlPolygons(kmlPolygonList);
//        System.out.println(kmlProperty);
//        return kmlProperty;
//    }
//
//    private static void parseFeature(Feature feature) {
//        if (feature != null) {
//            if (feature instanceof Document) {
//                List<Feature> featureList = ((Document) feature).getFeature();
//                featureList.forEach(
//                        documentFeature -> {
//                            if (documentFeature instanceof Placemark) {
//                                getPlaceMark((Placemark) documentFeature);
//                            } else {
//                                parseFeature(documentFeature);
//                            }
//                        }
//                );
//            } else if (feature instanceof Folder) {
//                List<Feature> featureList = ((Folder) feature).getFeature();
//                featureList.forEach(
//                        documentFeature -> {
//                            if (documentFeature instanceof Placemark) {
//                                getPlaceMark((Placemark) documentFeature);
//                            }
//                            {
//                                parseFeature(documentFeature);
//                            }
//                        }
//                );
//            }
//        }
//    }
//
//    private static KmlProperty getPlaceMark(Placemark placemark) {
//        KmlProperty kmlProperty = new KmlProperty();
//        Geometry geometry = placemark.getGeometry();
//        String name = placemark.getName();
//        kmlProperty = parseGeometry(name, geometry);
//        return kmlProperty;
//    }
//
//
//    private static KmlProperty parseGeometry(String name, Geometry geometry) {
//        KmlProperty kmlProperty = new KmlProperty();
//        if (geometry != null) {
//            if (geometry instanceof Polygon) {
//                Polygon polygon = (Polygon) geometry;
//                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
//                if (outerBoundaryIs != null) {
//                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
//                    if (linearRing != null) {
//                        List<Coordinate> coordinates = linearRing.getCoordinates();
//                        if (coordinates != null) {
//                            outerBoundaryIs = ((Polygon) geometry).getOuterBoundaryIs();
//                            List<KmlPolygon> kmlPolygons = addPolygonToList(kmlPolygonList, name, outerBoundaryIs);
//                            kmlProperty.setKmlPolygons(kmlPolygons);
//                        }
//                    }
//                }
//            } else if (geometry instanceof LineString) {
//                LineString lineString = (LineString) geometry;
//                List<Coordinate> coordinates = lineString.getCoordinates();
//                if (coordinates != null) {
//                    int width = 0;
//                    coordinates = ((LineString) geometry).getCoordinates();
//                    List<KmlLine> kmlLines = addLineStringToList(width, kmlLineList, coordinates, name);
//                    kmlProperty.setKmlLines(kmlLines);
//                }
//            } else if (geometry instanceof Point) {
//                Point point = (Point) geometry;
//                List<Coordinate> coordinates = point.getCoordinates();
//                if (coordinates != null) {
//                    coordinates = ((Point) geometry).getCoordinates();
//                    List<KmlPoint> kmlPoints = addPointToList(kmlPointList, coordinates, name);
//                    kmlProperty.setKmlPoints(kmlPoints);
//                }
//            } else if (geometry instanceof MultiGeometry) {
//                List<Geometry> geometries = ((MultiGeometry) geometry).getGeometry();
//                for (Geometry geometryToMult : geometries) {
//                    Boundary outerBoundaryIs;
//                    List<Coordinate> coordinates;
//                    if (geometryToMult instanceof Point) {
//                        coordinates = ((Point) geometryToMult).getCoordinates();
//                        List<KmlPoint> kmlPoints = addPointToList(kmlPointList, coordinates, name);
//                        kmlProperty.setKmlPoints(kmlPoints);
//                    } else if (geometryToMult instanceof LineString) {
//                        int width = 0;
//                        coordinates = ((LineString) geometryToMult).getCoordinates();
//                        List<KmlLine> kmlLines = addLineStringToList(width, kmlLineList, coordinates, name);
//                        kmlProperty.setKmlLines(kmlLines);
//                    } else if (geometryToMult instanceof Polygon) {
//                        outerBoundaryIs = ((Polygon) geometryToMult).getOuterBoundaryIs();
//                        List<KmlPolygon> kmlPolygons = addPolygonToList(kmlPolygonList, name, outerBoundaryIs);
//                        kmlProperty.setKmlPolygons(kmlPolygons);
//                    }
//                }
//            }
//        }
//        return kmlProperty;
//    }
//
//    private static List<KmlPolygon> addPolygonToList(List<KmlPolygon> kmlPolygonList, String name, Boundary outerBoundaryIs) {
//        LinearRing linearRing;
//        List<Coordinate> coordinates;
//        linearRing = outerBoundaryIs.getLinearRing();//面
//        coordinates = linearRing.getCoordinates();
//        ShowCoordinates(coordinates, name);
//        KmlPolygon kmlPolygon = new KmlPolygon();
//        kmlPolygon.setPoints(coordinates);
//        kmlPolygon.setName(name);
//        kmlPolygonList.add(kmlPolygon);
//        return kmlPolygonList;
//    }
//
//    private static List<KmlLine> addLineStringToList(int width, List<KmlLine> kmlLineList, List<Coordinate> coordinates, String name) {
//        ShowCoordinates(coordinates, name);
//        KmlLine kmlLine = new KmlLine();
//        kmlLine.setPoints(coordinates);
//        kmlLine.setWidth(width);
//        kmlLine.setName(name);
//        kmlLineList.add(kmlLine);
//        return kmlLineList;
//    }
//
//    private static List<KmlPoint> addPointToList(List<KmlPoint> kmlPointList, List<Coordinate> coordinates, String name) {
//        ShowCoordinates(coordinates, name);
//        KmlPoint kmlPoint = new KmlPoint();
//        kmlPoint.setName(name);
//        kmlPoint.setPoints(coordinates);
//        kmlPointList.add(kmlPoint);
//        return kmlPointList;
//    }
//
//    private static void ShowCoordinates(List<Coordinate> coordinates, String name) {
//        for (Coordinate coordinate : coordinates) {
//            System.out.println(coordinate);
//        }
//        System.out.println(name);
//    }
//}

import de.micromata.opengis.kml.v_2_2_0.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: txhglgj_project
 * @description: KML文件解析
 * @author: 岳正江
 * @create: 2018-12-01 12:39
 **/
public class ParsingKmlUtil {
    //以下三行都是自定义的KML类，用于获取名称name、所有点points、样式颜色color
    private List<KmlPoint> kmlPointList = new ArrayList<>();
    private List<KmlLine> kmlLineList = new ArrayList<>();
    private List<KmlPolygon> kmlPolygonList = new ArrayList<>();
    private KmlProperty kmlProperty = new KmlProperty();

    /**
     * 保存kml数据到临时表
     *
     * @param file 上传的文件实体
     * @return 自定义的KML文件实体
     */
    public KmlProperty parseKmlForJAK(File file) {

        Kml kml = Kml.unmarshal(file);
        Feature feature = kml.getFeature();
        parseFeature(feature);
        kmlProperty.setKmlPoints(kmlPointList);
        kmlProperty.setKmlLines(kmlLineList);
        kmlProperty.setKmlPolygons(kmlPolygonList);
        System.out.println(kmlProperty);
        return kmlProperty;
    }

    private void parseFeature(Feature feature) {
        if (feature != null) {
            if (feature instanceof Document) {
                List<Feature> featureList = ((Document) feature).getFeature();
                featureList.forEach(documentFeature -> {
                            if (documentFeature instanceof Placemark) {
                                getPlaceMark((Placemark) documentFeature);
                            } else {
                                parseFeature(documentFeature);
                            }
                        }
                );
            } else if (feature instanceof Folder) {
                List<Feature> featureList = ((Folder) feature).getFeature();
                featureList.forEach(documentFeature -> {
                            if (documentFeature instanceof Placemark) {
                                getPlaceMark((Placemark) documentFeature);
                            }
                            {
                                parseFeature(documentFeature);
                            }
                        }
                );
            }
        }
    }

    private void getPlaceMark(Placemark placemark) {
        Geometry geometry = placemark.getGeometry();
        String name = placemark.getName();
        parseGeometry(name, geometry);
    }

    private void parseGeometry(String name, Geometry geometry) {
        if (geometry != null) {
            if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                Boundary outerBoundaryIs = polygon.getOuterBoundaryIs();
                if (outerBoundaryIs != null) {
                    LinearRing linearRing = outerBoundaryIs.getLinearRing();
                    if (linearRing != null) {
                        List<Coordinate> coordinates = linearRing.getCoordinates();
                        if (coordinates != null) {
                            outerBoundaryIs = ((Polygon) geometry).getOuterBoundaryIs();
                            addPolygonToList(kmlPolygonList, name, outerBoundaryIs);
                        }
                    }
                }
            } else if (geometry instanceof LineString) {
                LineString lineString = (LineString) geometry;
                List<Coordinate> coordinates = lineString.getCoordinates();
                if (coordinates != null) {
                    int width = 0;
                    coordinates = ((LineString) geometry).getCoordinates();
                    addLineStringToList(width, kmlLineList, coordinates, name);
                }
            } else if (geometry instanceof Point) {
                Point point = (Point) geometry;
                List<Coordinate> coordinates = point.getCoordinates();
                if (coordinates != null) {
                    coordinates = ((Point) geometry).getCoordinates();
                    addPointToList(kmlPointList, coordinates, name);
                }
            } else if (geometry instanceof MultiGeometry) {
                List<Geometry> geometries = ((MultiGeometry) geometry).getGeometry();
                for (Geometry geometryToMult : geometries) {
                    Boundary outerBoundaryIs;
                    List<Coordinate> coordinates;
                    if (geometryToMult instanceof Point) {
                        coordinates = ((Point) geometryToMult).getCoordinates();
                        addPointToList(kmlPointList, coordinates, name);
                    } else if (geometryToMult instanceof LineString) {
                        int width = 0;
                        coordinates = ((LineString) geometryToMult).getCoordinates();
                        addLineStringToList(width, kmlLineList, coordinates, name);
                    } else if (geometryToMult instanceof Polygon) {
                        outerBoundaryIs = ((Polygon) geometryToMult).getOuterBoundaryIs();
                        addPolygonToList(kmlPolygonList, name, outerBoundaryIs);
                    }
                }
            }
        }
    }

    private void addPolygonToList(List<KmlPolygon> kmlPolygonList, String name, Boundary outerBoundaryIs) {
        LinearRing linearRing;
        List<Coordinate> coordinates;
        linearRing = outerBoundaryIs.getLinearRing();//面
        coordinates = linearRing.getCoordinates();
        ShowCoordinates(coordinates, name);
        KmlPolygon kmlPolygon = new KmlPolygon();
        kmlPolygon.setPoints(coordinates);
        kmlPolygon.setName(name);
        kmlPolygonList.add(kmlPolygon);
    }

    private void addLineStringToList(int width, List<KmlLine> kmlLineList, List<Coordinate> coordinates, String name) {
        ShowCoordinates(coordinates, name);
        KmlLine kmlLine = new KmlLine();
        kmlLine.setPoints(coordinates);
        kmlLine.setWidth(width);
        kmlLine.setName(name);
        kmlLineList.add(kmlLine);
    }

    private void addPointToList(List<KmlPoint> kmlPointList, List<Coordinate> coordinates, String name) {
        ShowCoordinates(coordinates, name);
        KmlPoint kmlPoint = new KmlPoint();
        kmlPoint.setName(name);
        kmlPoint.setPoints(coordinates);
        kmlPointList.add(kmlPoint);
    }

    private void ShowCoordinates(List<Coordinate> coordinates, String name) {
        for (Coordinate coordinate : coordinates) {
            System.out.println(coordinate);
        }
        System.out.println(name);
    }
}
