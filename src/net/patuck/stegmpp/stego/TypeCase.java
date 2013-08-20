package net.patuck.stegmpp.stego;

import org.jdom2.Attribute;
import org.jdom2.Document;

/**
 * Encode and decode one bit of data in the case of the value of the type attribute.
 * @author reshad
 */
@Deprecated
public class TypeCase implements StegMethod
{
	
	/**
	 * Encode one bit of data in the case of the type attribute.
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
	 * Decode one bit of data from the case of the type attribute.
	 * @param tag the message tag.
	 */
	@Override
	public void receive(Document tag)
	{
		Attribute type = tag.getRootElement().getAttribute("type");
		if(type != null)
		{
			if(type.getValue().equals("CHAT"))
			{
				// Decode type = "CHAT" as 1.
				Stego.setNextBit(true);
			}
			else
			{
				// Decode type="chat" as 0.
				Stego.setNextBit(false);
			}
		}
	}
}
