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
	public static final String GUILD_MAGE_XSD_NAME = "MAGIER";
	public static final String ELF_XSD_NAME = "ELF";
	public static final String WITCH_XSD_NAME = "HEXE";

	public Descriptor[] translateToDescriptors(List<String> merkmals) {
		List<Descriptor> collect = merkmals.stream()
										   .map(this::translateToDescriptor)
										   .collect(Collectors.toList());
		return collect.toArray(new Descriptor[0]);
	}

	public Descriptor translateToDescriptor(String description) {
		Optional<Descriptor> magicDescriptor = translateMagicDescriptor(description);
		if ((magicDescriptor).isPresent()) {
			return magicDescriptor.get();
		}

		Optional<Descriptor> tradition = translateTraditionDescriptor(description);
		if (tradition.isPresent()) {
			return tradition.get();
		}


		throw new IllegalArgumentException("Unknown description: " + description);
	}

	private Optional<Descriptor> translateTraditionDescriptor(String description) {
		if (!StringUtils.startsWith(description, PREFIX)) {
			return Optional.empty();
		}

		String tradition = StringUtils.substring(description, PREFIX.length());
		switch (tradition) {
			case GUILD_MAGE_XSD_NAME:
				return Optional.of(TraditionDescriptors.GUILD_MAGE);
			case ELF_XSD_NAME:
				return Optional.of(TraditionDescriptors.ELF);
			case WITCH_XSD_NAME:
				return Optional.of(TraditionDescriptors.WITCH);
		}

		throw new UnsupportedOperationException("Tradition " + description + " not yet supported");
	}

	private Optional<Descriptor> translateMagicDescriptor(String description) {
		try {
			MerkmalMagie merkmalMagie = MerkmalMagie.fromValue(description);
			switch (merkmalMagie) {
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
		} catch (IllegalArgumentException e) {
			//try next, I actually hate this
		}
		return Optional.empty();
	}

	public String translate(Descriptor descriptor){
		if(descriptor instanceof TraditionDescriptors){
			return translateFromTradition((TraditionDescriptors) descriptor);
		}
		if(descriptor instanceof MagicDescriptors){
			return translateFromDescriptor((MagicDescriptors) descriptor);
		}

		return "";
	}

	private String translateFromTradition(TraditionDescriptors descriptor) {
		switch (descriptor) {
			case GUILD_MAGE:
				return PREFIX + GUILD_MAGE_XSD_NAME;
			case ELF:
				return PREFIX + ELF_XSD_NAME;
			case WITCH:
				return PREFIX + WITCH_XSD_NAME;
		}
		return StringUtils.EMPTY;
	}

	private String translateFromDescriptor(MagicDescriptors descriptor) {
		switch (descriptor){
			case ANTI_MAGIC:
				return MerkmalMagie.ANTIMAGIE.value();
			case CLAIRVOYANCE:
				return MerkmalMagie.HELLSICHT.value();
			case Demonic:
				return MerkmalMagie.DÄMONISCH.value();
			case Elemental:
				return MerkmalMagie.ELEMENTAR.value();
			case Healing:
				return MerkmalMagie.HEILUNG.value();
			case Illusion:
				return MerkmalMagie.ILLUSION.value();
			case Influence:
				return MerkmalMagie.EINFLUSS.value();
			case Object:
				return MerkmalMagie.OBJEKT.value();
			case Spheres:
				return MerkmalMagie.SPHÄREN.value();
			case Telekinesis:
				return MerkmalMagie.TELEKINESE.value();
			case Transformation:
				return MerkmalMagie.VERWANDLUNG.value();
		}
		return StringUtils.EMPTY;
	}
}
