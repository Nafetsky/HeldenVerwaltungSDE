package XsdWrapper;

import api.Event;
import generated.Ereignis;

import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDateTime;

public class EventParser {


	public Event parse(Ereignis ereignis) {
		Event event = Event.builder()
						   .date(parseDate(ereignis))
						   .build();

		return event;
	}

	private LocalDateTime parseDate(Ereignis ereignis) {
		XMLGregorianCalendar datum = ereignis.getDatum();
		return LocalDateTime.of(datum.getYear(), datum.getMonth(), datum.getDay(), datum.getHour(), datum.getMinute());
	}
}
