/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import org.jdom2.Document;

/**
 *
 * @author reshad
 */
public class StegReciever
{
	Document tag;

	public StegReciever(Document tag)
	{
		if (Stego.checkEOT())
		{
			Stego.getPlainText();
		}
		else
		{
			this.tag = tag;
			new TrailingSpace().recieve(tag);
			new LeadingSpace().recieve(tag);
		}
	}
}
