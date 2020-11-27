package com.example.backend;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;

public class Xml {
    // ## THIS IS A TEMPLATE FOR XML NOT THE REAL CODE!!!
    public void xmlToJava() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("trial.xml");
            NodeList personList = doc.getElementsByTagName("shape");
            for(int i=0; i<personList.getLength(); i++){
               Node node = personList.item(i);
               if(node.getNodeType() == Node.ELEMENT_NODE){
                    Element person = (Element)node;
                    NodeList nameList = person.getChildNodes();
                    for(int j=0; j<nameList.getLength(); j++){
                        Node n = nameList.item(j);
                        if(n.getNodeType() == Node.ELEMENT_NODE){
                            Element name = (Element)n;
                            // System.out.println(); person + id + name.getTagName() + name.getTextContent()
                        }
                    }
               }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
