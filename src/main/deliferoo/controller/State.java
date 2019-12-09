package controller;

import java.io.File;
import java.util.List;

import model.Delivery;
import model.FullMap;
import model.SpecialNode;
import view.Window;

/**
 * Interface to be followed by all classes implementing states of the
 * application
 * 
 * @author sadsitha
 *
 */
public interface State {

    /**
     * Method systematically called when current state changes to this state
     * 
     */
    public default void init(Window window, Controller controller) {
	
    }

    /**
     * Method called when right mouse button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void rightClick(Window window, Controller controller) {
	
    }

    /**
     * Method called when left mouse button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void leftClick(Window window, Controller controller) {
    }
    
    public default void moveSpecialNode(Window window, Controller controller, SpecialNode node, String newNodeId) {
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void modifyButtonClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Add Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void addButtonClick(Window window, Controller controller) {
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void removeButtonClick(Window window, Controller controller) {
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void confirmButtonClick(Window window, Controller controller) {
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void calculateButtonClick(Window window, Controller controller) {
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void undoButtonClick(Window window, Controller controller) {
    }
    
    /**
     * Method called when the "Modify Delivery" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void redoButtonClick(Window window, Controller controller) {
    }

    /**
     * Method called when the "Cancel" button is clicked
     * 
     * @param the window
     * @param the controller
     */
    public default void cancelButtonClick(Window window, Controller controller) {
    }

    

    /**
     * Method called when a Delivery is clicked on (either on the map or on the text
     * view)
     * 
     * @param the window
     * @param the controller
     */
    public default void selectDeliveryClick(Window window, Controller controller, Integer deliveryIndex) {
    }

    /**
     * Method called when a new deliveries file is loaded
     * 
     * @param the window
     * @param the controller
     * @param the deliveries file
     * @param the map
     */
    public default void loadDeliveries(Window window, Controller controller, File deliveriesFile, FullMap map) {
    }

    /**
     * Method called when a new map file is loaded
     * 
     * @param the window
     * @param the controller
     * @param the map file
     */
    public default void loadMap(Window window, Controller controller, File mapFile) {
    }

    /**
     * Method called when a round needs to be calculated
     * 
     * @param the window
     * @param the controller
     * @param the deliveries
     * @param the map
     */
    public default void calculateRound(Window window, Controller controller, List<Delivery> deliveries, FullMap map) {
    }
}
