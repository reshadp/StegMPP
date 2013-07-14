/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 *
 * @author reshad
 */
public class IDValue implements StegMethod
{
	
	/**
	 * Set encode bits onto the tag to be sent.
	 * @param tag 
	 */
	@Override
	public void send(Document tag)
	{
		String id = tag.getRootElement().getAttributeValue("id");
		if(id != null)
		{
			if(Stego.hasNextBit())
			{
				if(Stego.getNextBit())
				{
					//encode 1 make sure id is odd.
					if (Long.decode("0x"+id) % 2 == 0)
					{
						//reset id
					}
							
				}
				else
				{
					// Encode 0 make sure id is even.
				}
			}
		}
	}

	
	/**
	 * Get the encoded bits from an incoming tag.
	 * @param tag 
	 */
	@Override
	public void recieve(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null)
		{
			if(body.getText().charAt(0) == ' ')
			{
				Stego.setNextBit(true);
				body.setText(body.getText().substring(1, (body.getText().length())));
			}
			else
			{
				Stego.setNextBit(false);
			}
		}
	}
}
