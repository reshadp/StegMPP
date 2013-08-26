package stegmpp.stego;

import stegmpp.xmpp.Session;
import org.jdom2.Attribute;
import org.jdom2.Document;

/**
 * Encode one bit of data in the value of the id attribute of the message tag.
 * @author reshad
 */
public class IDValue implements StegMethod
{
	
	/**
	 * Encode one bit in the id attribute of the message tag.
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
					// Encode 1 make sure id is odd.
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
	 * Decode one bit in the id attribute of the message tag.
	 * @param tag the tag to decode.
	 */
	@Override
	public void receive(Document tag)
	{
		Attribute id = tag.getRootElement().getAttribute("id");
		if(id != null)
		{
			if(Long.decode("0x"+id.getValue()) % 2 == 1)
			{
				// Decode odd value of id attribute as 1.
				Stego.setNextBit(true);
			}
			else
			{
				// Decode even value of id attribute as 0.
				Stego.setNextBit(false);
			}
		}
	}
}
