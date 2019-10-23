package utility;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import XsdWrapper.CharacterXml;
import XsdWrapper.MarshallingHelper;
import generated.Charakter;

import api.base.Character;
import org.apache.commons.io.IOUtils;

import javax.xml.bind.JAXBException;

public class TestPreparer {

	static final String TEST_RESOURCES_BARUNDAR_XML = "Barundar.xml";
	static final String TEST_RESOURCES_BARUNDAR_ORIG_XML = "Barundar_orig.xml";
	static final String TEST_RESOURCES_VITUS_XML = "VitusSanin.xml";
	static final String PATH_PREFIX = "test/resources/";

	public MarshallingHelper getMarshallingHelper() throws JAXBException {
		return new MarshallingHelper();
	}

	public Character getBarundar() throws Exception {
		Charakter charakter = getRawBarundar();

		return new CharacterXml(charakter);
	}

	public Charakter getRawBarundar() throws JAXBException {
		File file = openFile(TestPreparer.TEST_RESOURCES_BARUNDAR_XML);
		return getMarshallingHelper()
				.unmarshallCharakter(file);
	}

	public String getBarundarAsText() throws IOException {
		return IOUtils.toString(openFile(TestPreparer.TEST_RESOURCES_BARUNDAR_XML).toURI(), StandardCharsets.UTF_8);
	}

	public Charakter getVitus() throws Exception {
		File file = openFile(TestPreparer.TEST_RESOURCES_VITUS_XML);
		return getMarshallingHelper()
				.unmarshallCharakter(file);
	}

	@Deprecated
	/**
	 * @deprecated I have no idea what, this is supposed to do
	 */
	public String getBarundarOrig() throws Exception {
		byte[] encoded = Files.readAllBytes(Paths.get(PATH_PREFIX + TestPreparer.TEST_RESOURCES_BARUNDAR_XML));
		return new String(encoded, "UTF-8").replaceAll(">\\p{Space}*<", "><")
										   .replaceAll("><", ">\r\n<");
	}

	private File openFile(String name) {
		File file;
		file = new File(name);
		if (file != null && file.exists()) {
			return file;
		}

		return new File(PATH_PREFIX + name);
	}


}
