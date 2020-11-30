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

import java.io.File;
// import java.io.IOException;
import java.util.HashMap;
// import java.util.Iterator;


@RestController
@CrossOrigin
public class Xml {

    // ## THIS IS A TEMPLATE FOR USING XML NOT THE REAL CODE!!! THE FOLLOWING FUNCTION MIGHT NOT BE NEEDED!
    // @GetMapping("/xmltojava")
    // public String xmlToJava() {

    //     DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    //     String answer = "";
    //     try {
    //         DocumentBuilder builder = factory.newDocumentBuilder();
    //         Document doc = builder.parse("E:\\XML_TEST\\data.xml");
    //         NodeList personList = doc.getElementsByTagName("Shapes");
    //         for (int i = 0; i < personList.getLength(); i++) {
    //             Node node = personList.item(i);
    //             if (node.getNodeType() == Node.ELEMENT_NODE) {
    //                 Element person = (Element) node;
    //                 NodeList nameList = person.getChildNodes();
    //                 for (int j = 0; j < nameList.getLength(); j++) {
    //                     Node n = nameList.item(j);
    //                     if (n.getNodeType() == Node.ELEMENT_NODE) {
    //                         Element name = (Element) n;
    //                         answer += name.getTextContent() + "  ";
    //                     }
    //                 }
    //             }
    //         }
    //     } catch (ParserConfigurationException e) {
    //         e.printStackTrace();
    //         return "fail";
    //     } catch (SAXException e) {
    //         e.printStackTrace();
    //         return "fail";
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return "fail";
    //     }
    //     return answer;
    // }
    public String javaToXml(HashMap<Integer, Shape> shapes, String location) throws TransformerException {
        
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
                isMulti = (shapes.get(key)).getType().equalsIgnoreCase("multi");
                
                if(isLine){
                    Line hold = (Line)shapes.get(key);
                    Point stpt = hold.getStartingPoint();
                    Point endpt = hold.getEndingPoint();

                    createShapeTags(document, shape, "Type", String.valueOf(hold.getType()));
                    createShapeTags(document, shape, "StartingPoint", String.valueOf(stpt.getX()) + "," + String.valueOf(stpt.getY()));
                    createShapeTags(document, shape, "EndingPoint", String.valueOf(endpt.getX()) + "," + String.valueOf(endpt.getY()));
                }
                if(isMulti){
                    MultiPointShape hold = (MultiPointShape)shapes.get(key);
                    Point upperLeft = hold.getUpperLeftCorner();

                    createShapeTags(document, shape, "Type", String.valueOf(hold.getType()));
                    createShapeTags(document, shape, "UpperLeft", String.valueOf(upperLeft.getX()) + "," + String.valueOf(upperLeft.getY()));
                    createShapeTags(document, shape, "Width", String.valueOf(hold.getWidth()));
                    createShapeTags(document, shape, "Height", String.valueOf(hold.getHeight()));
                    createShapeTags(document, shape, "OutlineColor", String.valueOf(hold.getOutlineColor()));
                    createShapeTags(document, shape, "FillOpacity", String.valueOf(hold.getFillOpacity()));
                }
            
            Shape object = (Shape)shapes.get(key);
            
            createShapeTags(document, shape, "FillColor", String.valueOf(object.getFillColor()));
            createShapeTags(document, shape, "Thickness", String.valueOf(object.getThickness()));

           }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");


            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File(location)); // "E:\\XML_TEST\\data.xml"

            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException | TransformerConfigurationException e) {
            e.printStackTrace();
            return "failed";
        }
        return "Success! Saved to XML successfully!";
    }
    private void createShapeTags(Document document, Element shape, String tagName, String value){
        Element newChild = document.createElement(tagName);
        newChild.appendChild(document.createTextNode(value));
        shape.appendChild(newChild);
    }
}
