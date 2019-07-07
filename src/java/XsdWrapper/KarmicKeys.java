package XsdWrapper;

import api.skills.Descriptor;
import api.skills.KarmicDescriptor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum KarmicKeys {

	P_ANTIMAGIC(KarmicDescriptor.PRAIOS_ANTI_MAGIC, "Praios_Antimagie"),
	P_ORDER(KarmicDescriptor.PRAIOS_ORDER, "Praios_Ordnung");

	private final KarmicDescriptor descriptor;
	private final String xmlKey;

	public static Optional<Descriptor> parse(String description) {
		Optional<KarmicDescriptor> first = Arrays.stream(values())
												 .filter(value -> StringUtils.equals(value.xmlKey, description))
												 .map(KarmicKeys::getDescriptor)
												 .findFirst();
		if (first.isPresent()) {
			return Optional.of(first.get());
		}
		return Optional.empty();
	}

	public static String parse(KarmicDescriptor descriptor) {
		return Arrays.stream(values())
					 .filter(value -> value.descriptor == descriptor)
					 .map(KarmicKeys::getXmlKey)
					 .findFirst()
					 .orElseThrow(() -> new UnsupportedOperationException(""));
	}
}
