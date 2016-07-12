package utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.UnsupportedEncodingException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generated.Charakter;
import generated.ObjectFactory;

public class MarshallingHelper {
	private static MarshallingHelper INSTANCE;

	JAXBContext jaxbContext;
	Unmarshaller jaxbUnmarshaller;
	Marshaller marshaller;
	ObjectFactory factory;

	private MarshallingHelper() throws JAXBException {
		jaxbContext = JAXBContext.newInstance(ObjectFactory.class);
		jaxbUnmarshaller = jaxbContext.createUnmarshaller();
		marshaller = jaxbContext.createMarshaller();
		factory = new ObjectFactory();
	}

	public static MarshallingHelper getInstance() {
		if (INSTANCE == null) {
			try {
				INSTANCE = new MarshallingHelper();
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		}
		return INSTANCE;
	}

	@SuppressWarnings("unchecked")
	public Charakter unmarshall(File file) throws JAXBException {
		return ((JAXBElement<Charakter>) jaxbUnmarshaller.unmarshal(file)).getValue();
	}

	public String marshall(Charakter charakter) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			marshaller.marshal(factory.createCharakter(charakter), outStream);
			return outStream.toString("UTF-8");
		} catch (UnsupportedEncodingException | JAXBException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Something while marshalling wnt totally wrong", e);
		}

	}

}
