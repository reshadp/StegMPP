/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import org.jdom2.Attribute;
import org.jdom2.Document;

/**
 *
 * @author reshad
 */
public class TypeCase implements StegMethod
{
	
	/**
	 * 
	 * @param tag 
	 */
	@Override
	public void send(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type != null && Stego.hasNextBit())
		{
			if(Stego.getNextBit())
			{
				// Encode 1 as a type = "CHAT".
				type.setValue("CHAT");
			}
			else
			{
				// Encode 0 as type = "chat".(do nothing)
			}
		}
	}

	
	/**
	 * 
	 * @param tag 
	 */
	@Override
	public void recieve(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type != null)
		{
			if(type.getValue().equals("CHAT"))
			{
				Stego.setNextBit(true);
			}
			else
			{
				Stego.setNextBit(false);
			}
		}
	}
}
