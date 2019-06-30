package XsdWrapper;

import api.skills.Descriptor;
import api.skills.MagicDescriptors;
import api.skills.TraditionDescriptors;
import generated.MerkmalMagie;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class DescriptorTranslator {

	public static final String PREFIX = "REPRÄSENTATION_";

	public Descriptor[] translateToDescriptors(List<String> merkmals) {
		List<Descriptor> collect = merkmals.stream()
										   .map(this::translateToDescriptor)
										   .collect(Collectors.toList());
		return collect.toArray(new Descriptor[0]);
	}

	public Descriptor translateToDescriptor(String description) {
		Optional<Descriptor> magicDescriptor = translateMagicDescriptor(description);
		if((magicDescriptor).isPresent()){
			return magicDescriptor.get();
		}

		Optional<Descriptor> tradition = translateTraditionDescriptor(description);
		if(tradition.isPresent()){
			return tradition.get();
		}


		throw new IllegalArgumentException("Unknown description: " +description);
	}

	private Optional<Descriptor> translateTraditionDescriptor(String description) {
		if(!StringUtils.startsWith(description, PREFIX)){
			return Optional.empty();
		}

		String tradition = StringUtils.substring(description, PREFIX.length());
		switch(tradition){
			case "MAGIER":
				return Optional.of(TraditionDescriptors.GuildMage);
			case "ELF":
					return Optional.of(TraditionDescriptors.Elf);
			case "HEXE":
				return Optional.of(TraditionDescriptors.Wtch);
		}

		throw new UnsupportedOperationException("Tradition " + description + " not yet supported");
	}

	private Optional<Descriptor> translateMagicDescriptor(String description) {
		try{
			MerkmalMagie merkmalMagie = MerkmalMagie.fromValue(description);
			switch (merkmalMagie){
				case ANTIMAGIE:
					return Optional.of(MagicDescriptors.ANTI_MAGIC);
				case HELLSICHT:
					return Optional.of(MagicDescriptors.CLAIRVOYANCE);
				case DÄMONISCH:
					return Optional.of(MagicDescriptors.Demonic);
				case ELEMENTAR:
					return Optional.of(MagicDescriptors.Elemental);
				case HEILUNG:
					return Optional.of(MagicDescriptors.Healing);
				case ILLUSION:
					return Optional.of(MagicDescriptors.Illusion);
				case EINFLUSS:
					return Optional.of(MagicDescriptors.Influence);
				case OBJEKT:
					return Optional.of(MagicDescriptors.Object);
				case SPHÄREN:
					return Optional.of(MagicDescriptors.Spheres);
				case TELEKINESE:
					return Optional.of(MagicDescriptors.Telekinesis);
				case VERWANDLUNG:
					return Optional.of(MagicDescriptors.Transformation);

			}
		} catch(IllegalArgumentException e){
			//try next, I actually hate this
		}
		return Optional.empty();
	}
}
