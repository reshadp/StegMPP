/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import net.patuck.stegmpp.xmpp.Session;
import org.jdom2.Attribute;
import org.jdom2.Document;

/**
 * 
 * @author reshad
 */
public class IDValue implements StegMethod
{
	
	/**
	 * Set encode bits onto the tag to be sent.
	 * @param tag the tag to encode.
	 */
	@Override
	public void send(Document tag)
	{
		Attribute id = tag.getRootElement().getAttribute("id");
		if(id != null)
		{
			if(Stego.hasNextBit())
			{
				if(Stego.getNextBit())
				{
					//encode 1 make sure id is odd.
					if (Long.decode("0x"+id.getValue()) % 2 == 0)
					{
						id.setValue(Session.getNextId());
					}
				}
				else
				{
					// Encode 0 make sure id is even.
					if (Long.decode("0x"+id.getValue()) % 2 == 1)
					{
						id.setValue(Session.getNextId());
					}
				}
			}
		}
	}

	
	/**
	 * Get the encoded bits from an incoming tag.
	 * @param tag the tag to decode.
	 */
	@Override
	public void recieve(Document tag)
	{
		Attribute id = tag.getRootElement().getAttribute("id");
		if(id != null)
		{
			if(Long.decode("0x"+id.getValue()) % 2 == 1)
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
