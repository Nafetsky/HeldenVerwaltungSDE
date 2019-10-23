package controle;

import XsdWrapper.CharacterXml;
import XsdWrapper.MarshallingHelper;
import api.base.Character;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;

import javax.xml.bind.JAXBException;
import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Log4j2
public class CharacterManagement {

	private final Path characterSource;
	private final MarshallingHelper marshaller;

	public CharacterManagement(Path characterSource){
		this.characterSource = characterSource;
		try {
			marshaller  = new MarshallingHelper();
		} catch (JAXBException e) {
			throw new IllegalStateException("Can not initialize reader", e);
		}
	}


	public List<Character> getCharacters() {
		return Arrays.stream(characterSource.toFile()
											.listFiles())
					 .map(this::parseToCharacter)
					 .filter(Optional::isPresent)
					 .map(Optional::get)
					 .collect(Collectors.toList());
	}

	private Optional<Character> parseToCharacter(File file) {
		String ending = StringUtils.right(file.getName(), 3);
		switch (ending) {
			case "xml":
				return openCharacterInXml(file);
			case "jsn":
				return Optional.empty();
			default:
				return Optional.empty();
		}
	}

	private Optional<Character> openCharacterInXml(File file) {
		try {
			return Optional.of(new CharacterXml(marshaller.unmarshallCharakter(file)));
		} catch (JAXBException e) {
			LOGGER.error("File " + file.getName() + " could not be opened", e);
		}
		return Optional.empty();
	}

}
