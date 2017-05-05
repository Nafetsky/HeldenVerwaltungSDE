package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import generated.Charakter;

public class TestPreparer {
	
	static final String TEST_RESOURCES_BARUNDAR_XML = "Barundar.xml";
	static final String TEST_RESOURCES_BARUNDAR_ORIG_XML = "Barundar_orig.xml";
	static final String TEST_RESOURCES_VITUS_XML = "VitusSanin.xml";
	static final String PATH_PREFIX = "test/resources/";

	public MarshallingHelper getMarshallingHelper(){
		return MarshallingHelper.getInstance();
	}

	public Charakter getBarundar() throws Exception {
		File file = openFile(TestPreparer.TEST_RESOURCES_BARUNDAR_XML);
		return MarshallingHelper.getInstance().unmarshallCharakter(file);
	}
	
	public Charakter getVitus() throws Exception {
		File file = openFile(TestPreparer.TEST_RESOURCES_VITUS_XML);
		return MarshallingHelper.getInstance().unmarshallCharakter(file);
	}
	
	public String getBarundarOrig() throws Exception{
		byte[] encoded = Files.readAllBytes(Paths.get(PATH_PREFIX + TestPreparer.TEST_RESOURCES_BARUNDAR_XML));
		return new String(encoded, "UTF-8").replaceAll(">\\p{Space}*<", "><").replaceAll("><", ">\r\n<");
	}
	
	private File openFile(String name){
		File file =null;
		file = new File(name);
		if(file!=null && file.exists()){
			return file;
		}
		
		return new File(PATH_PREFIX+name);
	}


}
