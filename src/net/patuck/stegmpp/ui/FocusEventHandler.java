/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.ui;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author reshad
 */
public class FocusEventHandler implements FocusListener
{

	@Override
	public void focusGained(FocusEvent fe)
	{
		((JTextComponent)fe.getSource()).selectAll();
	}

	@Override
	public void focusLost(FocusEvent fe)
	{
		((JTextComponent)fe.getSource()).select(0,0);
	}
	
}
