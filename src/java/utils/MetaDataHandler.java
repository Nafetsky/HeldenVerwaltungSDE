package utils;

import org.apache.commons.lang3.StringUtils;

import generated.MetaData;
import generated.MetaDataLine;
import generated.ObjectFactory;

public class MetaDataHandler {
	
	private MetaDataHandler(){
		
	}
	
	public static String getFileToName(MetaData metaData, String name){
		for(MetaDataLine line:metaData.getCharakters()){
			if(StringUtils.equals(name, line.getName())){
				return line.getDatei();
			}
		}
		return "";
	}

	public static String addEntryToMetaData(MetaData metaData, String name) {
		ObjectFactory factory = new ObjectFactory();
		MetaDataLine line = factory.createMetaDataLine();
		line.setName(name);
		String fileName = name.replaceAll(" ", "")+".xml"; 
		line.setDatei(fileName);
		metaData.getCharakters().add(line);
		return fileName;
	}

}
