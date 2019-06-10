package XsdWrapper;

import api.Event;
import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import generated.Ereignis;
import generated.ObjectFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

class EventParserTest {

	private static final String DATE_AS_TEXT = "201906101409";
	private EventParser parser = new EventParser();
	private ObjectFactory factory = new ObjectFactory();

	@Test
	void testParseDate(){
		Ereignis ereignis = factory.createEreignis();
		XMLGregorianCalendarImpl xmlDate = buildCalendar();
		ereignis.setDatum(xmlDate);

		Event event = parser.parse(ereignis);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
		TemporalAccessor expectation = LocalDateTime.parse(DATE_AS_TEXT, formatter);
		assertThat(event.getDate(), is(expectation));
	}

	private XMLGregorianCalendarImpl buildCalendar() {
		XMLGregorianCalendarImpl xmlDate = new XMLGregorianCalendarImpl();
		xmlDate.setYear(2019);
		xmlDate.setMonth(6);
		xmlDate.setDay(10);
		xmlDate.setHour(14);
		xmlDate.setMinute(9);
		return xmlDate;
	}

}