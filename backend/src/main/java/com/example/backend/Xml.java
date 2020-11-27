package com.example.backend;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;


@RestController
@CrossOrigin
public class Xml {

    // ## THIS IS A TEMPLATE FOR USING XML NOT THE REAL CODE!!!
    @GetMapping("/xmltojava")
    public String xmlToJava() {
        HashMap <Integer, Shape> shapes = new HashMap<Integer, Shape>(); 
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        String answer = "";
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("E:\\XML_TEST\\data.xml");
            NodeList personList = doc.getElementsByTagName("Shapes");
            for (int i = 0; i < personList.getLength(); i++) {
                Node node = personList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element person = (Element) node;
                    NodeList nameList = person.getChildNodes();
                    for (int j = 0; j < nameList.getLength(); j++) {
                        Node n = nameList.item(j);
                        if (n.getNodeType() == Node.ELEMENT_NODE) {
                            Element name = (Element) n;
                            answer += name.getTextContent() + "  ";
                        }
                    }
                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return "fail";
        } catch (SAXException e) {
            e.printStackTrace();
            return "fail";
        } catch (IOException e) {
            e.printStackTrace();
            return "fail";
        }
        return answer;
    }
    @GetMapping("/javatoxml")
    public String javaToXml() throws TransformerException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.newDocument();
            Element element= document.createElement("Shapes");
            document.appendChild(element);

            //for(int i=0; i<5; i++){
                // features of the developers
                Element developer = document.createElement("Shape");
                element.appendChild(developer);

                Attr attr = document.createAttribute("type");
                attr.setValue("ellipse");
                developer.setAttributeNode(attr);

                Element name = document.createElement("ID");
                name.appendChild(document.createTextNode("12"));
                developer.appendChild(name);
    
                Element surname = document.createElement("radiusX");
                surname.appendChild(document.createTextNode("34"));
                developer.appendChild(surname);
    
                Element age = document.createElement("radiusY");
                age.appendChild(document.createTextNode("56"));
                developer.appendChild(age);

           // }
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(document);

            StreamResult streamResult = new StreamResult(new File("E:\\XML_TEST\\data.xml"));

            transformer.transform(source, streamResult);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
            return "failed";
        } catch (TransformerConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "failed";
        }
        return "Success! Saved to XML successfully!";
    }
}
