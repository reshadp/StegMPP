/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stegmpp.stego;

import org.jdom2.Attribute;
import org.jdom2.Document;

/**
 * Encode and decode one bit in as the value of the type attribute (whether the value is "chat" or "normal").
 * @author reshad
 */
public class TypeValue implements StegMethod
{

	@Override
	public void send(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type != null && Stego.hasNextBit())
		{
			if(Stego.getNextBit())
			{
				// Encode 1 as type = "normal".
				tag.getRootElement().setAttribute("type", "normal");
			}
			else
			{
				// Encode 0 as type = "chat".(do nothing)
			}
		}
	}

	@Override
	public void receive(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type.getValue().equals("normal"))
		{
			// Set next bit to true if the value of the type attribute in the message tag is "normal".
			Stego.setNextBit(true);
		}
		else
		{
			// Set next bit to false if the value of the type attribute in the message tag is "chat".
			Stego.setNextBit(false);
		}
	}
	
}
