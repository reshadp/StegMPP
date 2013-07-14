/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * This class handles the steganography using a leading space in the body of the message.
 * @author reshad
 */
public class LeadingSpace implements StegMethod
{
	
	/**
	 * Set encode bits onto the tag to be sent.
	 * @param tag 
	 */
	@Override
	public void send(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null)
		{
			if(Stego.hasNextBit())
			{
				if(Stego.getNextBit())
				{
					// Encode 1 as a space.
					body.setText(" " + body.getText());
				}
				else
				{
					// Encode 0 as no space.(do nothing)
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
