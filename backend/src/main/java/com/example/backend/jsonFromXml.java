package com.example.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import javax.xml.parsers.ParserConfigurationException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.SAXException;

public class jsonFromXml  {

	public String jsonString(String path) throws ParserConfigurationException, SAXException, IOException {

		File xmlFile = new File(path+".xml"); //load xml file from its path (path for example "src\\java\\files\\nameOfTheXmlFile" )

		Reader fileReader = new FileReader(xmlFile);

		BufferedReader bufReader = new BufferedReader(fileReader);
		
		StringBuilder jsonString = new StringBuilder(); //stringBuilder to store all the xml file in a string block
		
		String step = bufReader.readLine(); //store the first line in the xml file
		
		jsonString.append(step).append("\n"); //add the first line to the jsonString

		// fill the jsonString with all the contents of the xml file
	    
	    while(step != null){

			step = bufReader.readLine();
			jsonString.append(step).append("\n");

		}
	    
		bufReader.close();
		
		String xmlString = jsonString.toString(); // casting the stringBuilder to string

		JSONObject jsonFile = XML.toJSONObject(xmlString); // the process of transforming the xml file contents to json object

		String jsonFileString = jsonFile.toString(4); // json string from json object


		return jsonFileString;
	}
	    
	
	

	public void saveJson(String path) throws ParserConfigurationException, SAXException, IOException{

		String jsonFileString = jsonString(path); // calling the jsonString function to read the xml and transform it to json

	    try {
			PrintWriter out = new PrintWriter(path+".json"); //creating the file where we save the json
		 	out.println(jsonFileString); //filling the file with the json String
		 	out.close();
		 } catch (FileNotFoundException e) {
			
		 	System.out.println("Error"+ e.getMessage());
		 }


	}
	
}