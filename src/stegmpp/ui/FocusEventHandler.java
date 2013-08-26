package stegmpp.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;

/**
 * The Focus Event Handler is used to handle focus events for components.
 * @author reshad
 */
public class FocusEventHandler implements FocusListener
{

	/**
	 * Override the focusGained event to select all the text when a Component is focused on.
	 * @param fe
	 */
	@Override
	public void focusGained(FocusEvent fe)
	{
		((JTextComponent)fe.getSource()).selectAll();
	}

	
	/**
	 * Override the focusLost event to deselect all the text when a Component is focused on.
	 * @param fe
	 */
	@Override
	public void focusLost(FocusEvent fe)
	{
		((JTextComponent)fe.getSource()).select(0,0);
	}
	
}
