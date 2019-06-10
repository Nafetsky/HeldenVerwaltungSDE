package api;

import java.time.LocalDate;

public interface IMetaData {

	String getName();

	Sex getSex();

	Race getRace();

	String getCulture();

	String getProfession();

	LocalDate getBirthday();

	int getAge();

}
