/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;

/**
 *
 * @author reshad
 */
public class StegSender
{
	private Document tag;
	
	/**
	 * Set encode bits onto the tag to be sent.
	 * @param tag the tag to encode.
	 */
	public StegSender(Document tag)
	{
		List<String> types = Stego.getTypes();
		for(String s : types)
		{
			try
			{
				// get an instance of the class named in type followed by the 
				((StegMethod) Class.forName("net.patuck.stegmpp.stego." + s).newInstance()).send(tag);
			}
			catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex)
			{
				Logger.getLogger(StegSender.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
}
