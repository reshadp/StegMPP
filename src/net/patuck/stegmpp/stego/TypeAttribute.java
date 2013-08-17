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
public class TypeAttribute implements StegMethod
{

	@Override
	public void send(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type != null && Stego.hasNextBit())
		{
			if(Stego.getNextBit())
			{
				// Encode 1 as no type attribute.
				tag.getRootElement().removeAttribute(type);
			}
			else
			{
				// Encode 0 as type = "chat".(do nothing)
			}
		}
	}

	@Override
	public void recieve(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type == null)
		{
			// Set next bit to true if there is no type attribute in the message tag.
			Stego.setNextBit(true);
		}
		else
		{
			// Set next bit to false if there is a type attribute in the message tag.
			Stego.setNextBit(false);
		}
	}
	
}
