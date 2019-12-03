package view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.geometry.Insets;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Pair;
import model.BestPath;
import model.Delivery;
import model.Edge;
import model.FullMap;
import model.Node;
import model.Round;
import model.SpecialNode;
import model.SpecialNodeType;

/**
 * Class for displaying a map
 *
 * @author sadsitha
 */
public class MapView {

    private Pane mapView;
    private FullMap map;
    private Double height;
    private Double width;
    private Double offsetX;
    private Double offsetY;
    private Double dimension;
    private List<Line> roundLine;

    /**
     * Constructor
     *
     * @param screenHeight the height of the screen
     * @param screenWidth  the width of the screen
     */
    public MapView(Double height, Double width) {
	this.mapView = new Pane();
	this.mapView.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5),
		BorderWidths.DEFAULT, new Insets(10))));
	this.height = height;
	this.width = width;
	this.offsetX = 0.05 * this.width;
	this.offsetY = 0.05 * this.height;
	this.dimension = Math.min(this.width - 2 * this.offsetX, this.height - 4 * this.offsetY);
	this.roundLine =new ArrayList<Line>();
    }

    /**
     *
     * @return the MapView node
     */
    public Pane getMapView() {
	return this.mapView;
    }

    /**
     *
     * @param map FullMap associated to this MapView
     */
    public void setMap(FullMap map) {
	this.map = map;

    }

    /**
     *
     * @param map FullMap associated to this MapView
     */
    public void updateMap(FullMap map) {
	this.map = map;
	this.mapView.getChildren().clear();
	this.drawMap(Color.BLACK, 2);
    }

    /**
     *
     * @param color       Color of the paths of this MapView
     * @param strokeWidth Width of the paths
     */
    public void drawMap(Color color, Integer strokeWidth) {
	for (Edge edge : this.map.getEdgeList()) {
	    drawPath(edge, color, strokeWidth);
	}
    }

    /**
     * Draws a path
     *
     * @param edge        the corresponding edge to be drawn on the corresponding
     *                    MapView
     * @param color       the color of the drawn path
     * @param strokeWidth the width of the path drawn
     */
    public Line drawPath(Edge edge, Color color, Integer strokeWidth) {
	Pair<Double, Double> p1 = calculateRelativePosition(edge.getStart());
	Pair<Double, Double> p2 = calculateRelativePosition(edge.getEnd());

	Line path = new Line(p1.getKey(), p1.getValue(), p2.getKey(), p2.getValue());

	path.setStroke(color);
	path.setStrokeWidth(strokeWidth);

	this.mapView.getChildren().add(path);
	return path;
    }

    /**
     * Draws markers corresponding to checkpoints of a delivery
     *
     * @param delivery   the corresponding delivery to be represented by the markers
     * @param color      the color code for the markers
     * @param markerSize the size of the markers
     */
    public void drawMarker(Delivery delivery, Color color, Integer markerSize) {
	SpecialNode start = delivery.getPickupNode();
	SpecialNode end = delivery.getDeliveryNode();

	for (SpecialNode node : Arrays.asList(start, end)) {
	    Shape marker = null;

	    Pair<Double, Double> p = calculateRelativePosition(node.getNode());
	    Double x = p.getKey();
	    Double y = p.getValue();

	    if (node.getSpecialNodeType() == SpecialNodeType.PICKUP) {
		marker = new Circle(x, y, markerSize / 2);
	    } else if (node.getSpecialNodeType() == SpecialNodeType.DROPOFF) {
		marker = new Rectangle(x - markerSize / 2, y - markerSize / 2, markerSize, markerSize);
	    } else {
		Polyline triangle = new Polyline();
		triangle.getPoints().addAll(x, y - 2 * markerSize / 3, x - markerSize / 2, y + markerSize / 3,
			x + markerSize / 2, y + markerSize / 3, x, y - 2 * markerSize / 3);
		marker = triangle;
	    }
	    marker.setFill(color);
	    this.mapView.getChildren().add(marker);
	}
    }

    /**
     * Removes old round and draws new round on map
     *
     * @param round a list of best paths to take to optimally complete the round
     */
    public void updateRound(Round round) {
	for (Line path : this.roundLine) {
	    this.mapView.getChildren().remove(path);
	}
	this.drawRound(round);
    }

    /**
     * Draws the paths followed by a round of deliveries, removes old round
     *
     * @param round a list of best paths to take to optimally complete the round
     */
    public void drawRound(Round round) {
	this.roundLine = new ArrayList<Line>();
	List<BestPath> resultRound = round.getResultPath();
	
	for (BestPath bestPath : resultRound) {
	    List<Edge> path = bestPath.getPath();
	    for (Edge edge : path) {
		this.roundLine.add(drawPath(edge, Color.HOTPINK, 3));
	    }
	}
	
    }

    /**
     * Calculates the relative position of a point from the MapView pane
     *
     * @param point point of interest
     * @return the coordinates as a pair with X as key and Y as value
     */
    private Pair<Double, Double> calculateRelativePosition(Node point) {
	Double x = this.offsetX
		+ dimension * (point.getLongitude() - this.map.getMinLong()) / this.map.getRangeLongitude();
	Double y = this.offsetY
		+ dimension * (this.map.getMaxLat() - point.getLatitude()) / this.map.getRangeLatitude();

	return new Pair<Double, Double>(x, y);
    }
}
