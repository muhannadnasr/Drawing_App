package com.example.backend;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
//import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
// import org.w3c.dom.Node;
// import org.w3c.dom.NodeList;
// import org.xml.sax.SAXException;

// import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
// import java.util.Iterator;


@RestController
@CrossOrigin
public class Xml {
    private static Xml instance;

    private Xml(){}

    public static Xml getInstance(){
        if(instance == null) instance = new Xml();
        return instance;
    }

    public String javaToXml(HashMap<Integer, Shape> shapes /*,String location*/) throws TransformerException {
        
        // INSERTING OBJECTS IN MAP FOR TESTING
        //HashMap <Integer, Shape> shapes = entry;
        // Line line = new Line(5, "line", new Point(31, 28), new Point(3, 4));
        // MultiPointShape multi = new MultiPointShape(6, "multi", new Point(51, 77), 14, 12);
        // Line line2 = new Line(7, "line", new Point(71, 18), new Point(31, 44));
        // MultiPointShape multi2 = new MultiPointShape(8, "multi", new Point(11, 27), 18, 19);

        // shapes.put(5, line);
        // shapes.put(6, multi);
        // shapes.put(7, line2);
        // shapes.put(8, multi2);
        // END
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();

            // beginning of insertion of shapes into xml under root tag "Shapes"
            Element element= document.createElement("Shapes");
            document.appendChild(element);

            for(HashMap.Entry<Integer, Shape> mapElement : shapes.entrySet()){
                Integer key = (Integer)mapElement.getKey();

                boolean isLine = false;
                boolean isMulti = false;

                Element shape = document.createElement("Shape");
                element.appendChild(shape);

                createShapeTags(document, shape, "ID", String.valueOf(key));

                // determining type of the shape
                isLine = (shapes.get(key)).getType().equalsIgnoreCase("line");
                String shapeType = (shapes.get(key)).getType();
                System.out.println(shapeType);

                isMulti = isMultiPointShape(shapeType);
                System.out.println(isMulti);
                
                if(isLine){
                    Line hold = (Line)shapes.get(key);
                    Point stpt = hold.getStartingPoint();
                    Point endpt = hold.getEndingPoint();

                    createShapeTags(document, shape, "type", String.valueOf(hold.getType()));
                    createShapeTags(document, shape, "startingPoint", String.valueOf(stpt.getX()) + "," + String.valueOf(stpt.getY()));
                    createShapeTags(document, shape, "endingPoint", String.valueOf(endpt.getX()) + "," + String.valueOf(endpt.getY()));
                }
                if(isMulti){
                    MultiPointShape hold = (MultiPointShape)shapes.get(key);
                    Point upperLeft = hold.getUpperLeftCorner();

                    createShapeTags(document, shape, "type", String.valueOf(hold.getType()));
                    createShapeTags(document, shape, "upperLeftCorner", String.valueOf(upperLeft.getX()) + "," + String.valueOf(upperLeft.getY()));
                    createShapeTags(document, shape, "width", String.valueOf(hold.getWidth()));
                    createShapeTags(document, shape, "height", String.valueOf(hold.getHeight()));
                    createShapeTags(document, shape, "outlineColor", String.valueOf(hold.getOutlineColor()));
                    createShapeTags(document, shape, "fillOpacity", String.valueOf(hold.getFillOpacity()));
                }
            
            Shape object = (Shape)shapes.get(key);
            
            createShapeTags(document, shape, "fillColor", String.valueOf(object.getFillColor()));
            createShapeTags(document, shape, "thickness", String.valueOf(object.getThickness()));

           }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            DOMSource source = new DOMSource(document);

            StringWriter writer = new StringWriter();
            StreamResult result = new StreamResult(writer);
            transformer.transform(source, result);
            return writer.toString();

        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
            return "failed";
        }
    }
    private void createShapeTags(Document document, Element shape, String tagName, String value){
        Element newChild = document.createElement(tagName);
        newChild.appendChild(document.createTextNode(value));
        shape.appendChild(newChild);
    }
    private boolean isMultiPointShape(String shapeType){
        if(shapeType.equals("square")) return true;
        if(shapeType.equals("rectangle")) return true;
        if(shapeType.equals("circle")) return true;
        if(shapeType.equals("ellipse")) return true;
        if(shapeType.equals("triangle")) return true;
        return false;
    }
}
