package net.patuck.stegmpp.stego;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Encode and decode one bit in as the presence or absence of a space at the beginning of the message.
 * @author reshad
 */
public class LeadingSpace implements StegMethod
{
	
	/**
	 * Encode one bit in the presence or absence of a space at the beginning of the message.
	 * @param tag the message tag.
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
	 * Decode one bit in the presence or absence of a space at the beginning of the message.
	 * @param tag the message tag.
	 */
	@Override
	public void receive(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null)
		{
			if(body.getText().charAt(0) == ' ')
			{
				// Decode space at the begining as 1.
				Stego.setNextBit(true);
				body.setText(body.getText().substring(1, (body.getText().length())));
			}
			else
			{
				// Decode no space at the beginning as 0.
				Stego.setNextBit(false);
			}
		}
	}
}
