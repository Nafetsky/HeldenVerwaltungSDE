package utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import generated.Charakter;

public class TestPreparer {
	
	static final String TEST_RESOURCES_BARUNDAR_XML = "test/resources/Barundar.xml";

	public MarshallingHelper getMarshallingHelper(){
		return MarshallingHelper.getInstance();
	}

	public Charakter getBarundar() throws Exception {
		File file = new File(TestPreparer.TEST_RESOURCES_BARUNDAR_XML);
		return MarshallingHelper.getInstance().unmarshall(file);
	}
	
	public String getBarundarOrig() throws Exception{
		byte[] encoded = Files.readAllBytes(Paths.get(TestPreparer.TEST_RESOURCES_BARUNDAR_XML));
		return new String(encoded, "UTF-8").replaceAll(">\\p{Space}*<", "><").replaceAll("><", ">\r\n<");
	}


}
