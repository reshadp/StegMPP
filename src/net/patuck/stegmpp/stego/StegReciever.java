/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import net.patuck.stegmpp.StegMPP;
import org.jdom2.Document;

/**
 * 
 * @author reshad
 */
public class StegReciever
{
	Document tag;

	/**
	 * Get the encoded bits from an incoming tag.
	 * @param tag the tag to decode. 
	 */
	public StegReciever(Document tag)
	{
		if (Stego.checkEOT())
		{
			String data = Stego.getPlainText();
			StegMPP.getUI().getSteganography().setData(data);
		}
		else
		{
			this.tag = tag;
			new TrailingSpace().recieve(tag);
			new LeadingSpace().recieve(tag);
			new IDValue().recieve(tag);
		}
	}
}
