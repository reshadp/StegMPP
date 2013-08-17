/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.patuck.stegmpp.ui.UI;
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
				
		List<String> types = Stego.getTypes();
		for(String s : types)
		{
			try
			{
				if(Stego.checkEOT())
				{
					String data = Stego.getPlainText();
					UI.getUI().setData(data);
				}
				else
				{
					// get an instance of the class named in type followed by the 
					((StegMethod) Class.forName("net.patuck.stegmpp.stego." + s).newInstance()).recieve(tag);
				}
			}
			catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex)
			{
				Logger.getLogger(StegSender.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}
}
