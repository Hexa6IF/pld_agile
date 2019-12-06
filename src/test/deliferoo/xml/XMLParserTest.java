package xml;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import model.Delivery;
import model.Edge;
import model.FullGraph;
import model.FullMap;
import model.Node;

public class XMLParserTest {

    @Test
    public void testMapParser() {
	XMLParser parser = XMLParser.getInstance();
	try {
	    File fXmlFile = new File("././src/main/resources/petitPlan.xml");
	    
	    FullMap map = parser.parseMap(fXmlFile);
	    assertEquals(308, map.getNodeMap().size());
	    assertEquals(616, map.getEdgeList().size());
	} catch (Exception e) {
	    fail(e);
	}
    }
    
    @Test
    public void testFullGraph() {
	XMLParser parser = XMLParser.getInstance();
	try {
	    File fXmlFileMap = new File("././src/main/resources/petitPlan.xml");
	    File fXmlFile = new File("src/main/resources/demandePetit2.xml");
	    
	    FullMap map = parser.parseMap(fXmlFileMap);
	    List<Delivery> listDeliveries = parser.parseDeliveries(fXmlFile,map);
	    System.out.println(listDeliveries.size());
	    Map <String,Node> nodes = map.getNodeMap();
	    List <Edge> edges = map.getEdgeList();
	    
	    String[] idNodesPickup = {"2835339774", "1679901320","208769120"};
	    Double[] latNodesPickup = {45.757305, 45.762653, 45.759434};
	    Double[] longNodesPickup = {4.8666577, 4.875565, 4.869736};
	    String[] idNodesDelivery = {"2835339774", "208769457","25336179"};
	    Double[] latNodesDelivery = {45.757305, 45.760174, 45.754128};
	    Double[] longNodesDelivery = {4.8666577, 4.877455, 4.863194};
	    Double[] dureePickup = {0.0, 7.0, 7.0};
	    Double[] dureeDelivery = {0.0, 10.0, 8.0};
	    int i = 0;
	    
	    for(Delivery delivery : listDeliveries) {
		System.out.println("blabla");
		
		System.out.println( delivery.getDeliveryNode().getNode().getNodeId());
		assertEquals(idNodesDelivery[i], delivery.getDeliveryNode().getNode().getNodeId());
		assertEquals(latNodesDelivery[i], delivery.getDeliveryNode().getNode().getLatitude());
		assertEquals(longNodesDelivery[i], delivery.getDeliveryNode().getNode().getLongitude());
		assertEquals(idNodesPickup[i], delivery.getPickupNode().getNode().getNodeId());
		assertEquals(latNodesPickup[i], delivery.getPickupNode().getNode().getLatitude());
		assertEquals(longNodesPickup[i], delivery.getPickupNode().getNode().getLongitude());
		assertEquals(dureeDelivery[i],delivery.getDeliveryNode().getDuration());
		assertEquals(dureePickup[i],delivery.getPickupNode().getDuration());
		i = i+1;
	    }
	    
//	    assertEquals("2129259176", nodes[3].getIdNode());
//	    assertEquals((float)45.75171, nodes[3].getLatitude());
//	    assertEquals((float)4.8718166, nodes[3].getLongitude());
//	    assertEquals((long)2129259180, edges[2].getNodeDest().getIdNode());
//	    assertEquals((long)2129259178, edges[2].getNodeOrigin().getIdNode());
//	    assertEquals((float)25.26484, edges[2].getDistance());
//	    assertEquals("Avenue Lacassagne", edges[3].getStreetName());
	    
	} catch (Exception e) {
	    fail(e);
	}
    }
    
    @Test
    public void testSingleton() {
	XMLParser enumParser1 = XMLParser.getInstance();
	XMLParser enumParser2 = XMLParser.getInstance();
	assertEquals(enumParser1, enumParser2);
    }
}
