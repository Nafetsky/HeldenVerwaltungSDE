package data;

import api.IMetaData;
import api.Race;
import api.Sex;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class Metadata implements IMetaData {

	private final String name;
	private final Sex sex;
	private final Race race;
	private final String culture;
	private final String profession;
	private final LocalDate birthday;
	private final int age;
}
