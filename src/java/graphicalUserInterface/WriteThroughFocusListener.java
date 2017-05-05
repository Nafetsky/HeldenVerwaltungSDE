package graphicalUserInterface;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class WriteThroughFocusListener implements FocusListener {

	ValueResolver resolver;
	ReferenceSaver saver;

	public WriteThroughFocusListener(ValueResolver resolver, ReferenceSaver saver) {
		this.resolver = resolver;
		this.saver = saver;
	}

	@Override
	public void focusGained(FocusEvent e) {
		// nothing particular

	}

	@Override
	public void focusLost(FocusEvent e) {
		saver.setValueToRefernce(resolver.getValue());
	}

}
