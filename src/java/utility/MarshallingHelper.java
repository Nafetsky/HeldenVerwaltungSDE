package utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import generated.Charakter;
import generated.MetaData;
import generated.ObjectFactory;
import generated.Schablone;

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
	public Charakter unmarshallCharakter(File file) throws JAXBException {
		return ((JAXBElement<Charakter>) jaxbUnmarshaller.unmarshal(file)).getValue();
	}
	
	@SuppressWarnings("unchecked")
	public Charakter unmarshallCharakter(String input) throws JAXBException {
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		return ((JAXBElement<Charakter>) jaxbUnmarshaller.unmarshal(stream)).getValue();
	}
	

	@SuppressWarnings("unchecked")
	public Schablone unmarshallSchablone(String input) throws JAXBException {
		InputStream stream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
		return ((JAXBElement<Schablone>) jaxbUnmarshaller.unmarshal(stream)).getValue();
	}
	
	@SuppressWarnings("unchecked")
	public Schablone unmarshallSchablone(File input) throws JAXBException {
		return ((JAXBElement<Schablone>) jaxbUnmarshaller.unmarshal(input)).getValue();
	}
	
	@SuppressWarnings("unchecked")
	public MetaData unmarshallMetaData(File file){
		try{
			return ((JAXBElement<MetaData>) jaxbUnmarshaller.unmarshal(file)).getValue();
		}
		catch (JAXBException e){
			return factory.createMetaData();
		}
	}

	public String marshall(Charakter charakter) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			marshaller.marshal(factory.createCharakter(charakter), outStream);
			return outStream.toString("UTF-8");
		} catch (UnsupportedEncodingException | JAXBException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Something while marshalling went totally wrong", e);
		}

	}
	
	public String marshall(Schablone charakter) {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			marshaller.marshal(factory.createKultur(charakter), outStream);
			return outStream.toString("UTF-8");
		} catch (UnsupportedEncodingException | JAXBException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Something while marshalling went totally wrong", e);
		}

	}
	
	public String marshall(MetaData metaData){
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		try {
			marshaller.marshal(factory.createMetaDate(metaData), outStream);
			return outStream.toString("UTF-8");
		} catch (UnsupportedEncodingException | JAXBException e) {
			e.printStackTrace();
			throw new UnsupportedOperationException("Something while marshalling went totally wrong", e);
		}
	}

	public MetaData makeNewEmptyMetaData() {
		MetaData metaData = factory.createMetaData();
		return metaData;
	}

//	public String marshall(WrappedCharakter activeCharakter) {
//		if(activeCharakter.isCharakter()){
//			return marshall(activeCharakter.charakter);
//		}
//		if(activeCharakter.isCulture() || activeCharakter.isProfession()){
//			return marshall(activeCharakter.template);
//		}
//		return null;
//	}


}
