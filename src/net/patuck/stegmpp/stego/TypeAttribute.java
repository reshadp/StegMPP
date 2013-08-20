package net.patuck.stegmpp.stego;

import org.jdom2.Attribute;
import org.jdom2.Document;

/**
 * Encode and decode one bit in as the presence or absence of the type attribute.
 * @author reshad
 */
public class TypeAttribute implements StegMethod
{

	/**
	 * Encode one bit in the presence or absence of the type attribute in a message tag.
	 * @param tag the message tag.
	 */
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

	/**
	 * Decode one bit in the presence or absence of the type attribute in a message tag.
	 * @param tag the message tag.
	 */
	@Override
	public void receive(Document tag)
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
