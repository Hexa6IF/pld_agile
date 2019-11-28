package view;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import controller.Controller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Delivery;
import model.FullMap;
import xml.XMLParser;

public class Window {

    private Controller controller;
    private Rectangle2D bounds;
    private MapView mapView;
    private ObservableList<SpecialNodeView> specialNodeViews;
    private TableBox tableBox;

    private FullMap map;
    private List<Delivery> deliveries;

    public Window(Controller controller) {
	this.controller = controller;
	this.bounds = Screen.getPrimary().getVisualBounds();

	XMLParser parser = XMLParser.getInstance();
	try {
	    File fXmlFile = new File("src/main/resources/grandPlan.xml");
	    this.map = parser.parseMap(fXmlFile);
	} catch(Exception e) {
	    System.err.println(e);
	}

	this.mapView = new MapView(this.map, this.bounds.getHeight(), this.bounds.getWidth());

    }

    public void launchWindow() {
	Stage stage = new Stage();
	buildAndShowStage(stage);
    }

    private void buildAndShowStage(Stage stage) {
	stage.setTitle("Del'IFeroo");

	stage.setX(this.bounds.getMinX());
	stage.setY(this.bounds.getMinY());
	stage.setWidth(this.bounds.getWidth());
	stage.setHeight(this.bounds.getHeight());

	stage.setResizable(false);

	stage.setScene(getScene(stage));

	stage.show();
    }

    private Scene getScene(Stage stage) {
	BorderPane border = new BorderPane();

	/* Initialise Node text view */
	try {
	    this.initialiseTable();
	} catch (Exception e) {
	    // TODO Auto-generated catch block
	    System.err.println("init failed");
	    e.printStackTrace();
	}

	border.setTop(createMenu(stage));
	border.setRight(createSideBar());
	border.setCenter(this.mapView.getMapView());

	return new Scene(border);
    }

    private VBox createMenu(Stage stage) {

	FileChooser fileChooser = new FileChooser();
	fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));

	Menu menuFile = new Menu("File");
	Menu menuHelp = new Menu("Help");

	MenuItem loadMap = new MenuItem("Load Map...");
	MenuItem loadDeliveries = new MenuItem("Load Deliveries...");

	MenuItem about = new MenuItem("About");

	MenuBar menuBar = new MenuBar();

	menuFile.getItems().add(loadMap);
	menuFile.getItems().add(loadDeliveries);

	menuHelp.getItems().add(about);

	loadMap.setOnAction(e -> {
	    File mapFile = fileChooser.showOpenDialog(stage);
	});
	loadDeliveries.setOnAction(e -> {
	    File deliveriesFile = fileChooser.showOpenDialog(stage);
	});

	menuBar.getMenus().addAll(menuFile, menuHelp);

	return new VBox(menuBar);
    }

    private VBox createSideBar() {
	VBox sideBar = new VBox();

	Rectangle rect = new Rectangle();
	rect.setHeight(bounds.getHeight() / 2);
	rect.setWidth(bounds.getWidth() / 3);
	TableView<String> table = new TableView<String>();
	table.setPrefSize(bounds.getWidth() / 3, bounds.getHeight() / 2);

	sideBar.setPrefWidth(bounds.getWidth() / 3);
	sideBar.getChildren().addAll(this.tableBox.getTable(), table);

	return sideBar;
    }

    /*
     * Creates the text view of special nodes
     * 
     */
    private void initialiseTable() throws Exception {
	XMLParser parser = XMLParser.getInstance();
	try {
	    File deliveryFile = new File("src/main/resources/demandeMoyen5.xml");
	    this.deliveries = parser.parseDeliveries(deliveryFile, this.map);
	} catch(Exception e) {
	    System.err.println(e);
	}
	ArrayList<SpecialNodeView> specialNodeViewTmpList = new ArrayList<SpecialNodeView>();
	  for (int i=0; i < this.deliveries.size(); i++) {
	      try {
		  specialNodeViewTmpList.add(new SpecialNodeView(i, Color.BLACK,
		      deliveries.get(i).getDeliveryNode()));
	    } catch (Exception e) {
		throw new Exception(e);
	    }
	      try {
		  specialNodeViewTmpList.add(new SpecialNodeView(i, Color.BLACK,
		      deliveries.get(i).getPickupNode()));
	    } catch (Exception e) {
		throw new Exception(e);
	    }
	  }
	 this.specialNodeViews = FXCollections.observableArrayList(specialNodeViewTmpList);
	
	//example data to remove
	/*this.specialNodeViews = FXCollections.observableArrayList(
		new SpecialNodeView(1,Color.BLACK,NodeType.PICKUP,7f,"14h34"),
		new SpecialNodeView(2,Color.BLACK,NodeType.PICKUP,10f,"14h34"),
		new SpecialNodeView(1,Color.PURPLE,NodeType.DROPOFF,5f,"14h34"),
		new SpecialNodeView(2,Color.PURPLE,NodeType.PICKUP,4f,"14h34")
		);*/
	this.tableBox = new TableBox(this.specialNodeViews);
    }
}
