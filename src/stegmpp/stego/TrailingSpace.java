package stegmpp.stego;

import org.jdom2.Document;
import org.jdom2.Element;

/**
 * Encode and decode one bit in as the presence or absence of a space at the end of the message.
 * @author reshad
 */
public class TrailingSpace implements StegMethod
{

	/**
	 * Encode one bit in the presence or absence of a space at the end of the message.
	 * @param tag the message tag.
	 */
	@Override
	public void send(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null && Stego.hasNextBit())
		{
			if(Stego.getNextBit())
			{
				// Encode 1 as a space.
				body.setText(body.getText() + " ");
			}
			else
			{
				// Encode 0 as no space.(do nothing)
			}
		}
	}

	/**
	 * Decode one bit in the presence or absence of a space at the end of the message.
	 * @param tag the message tag.
	 */
	@Override
	public void receive(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null)
		{
			if(body.getText().charAt(body.getText().length() - 1 ) == ' ')
			{
				// Decode space at the end as 1.
				Stego.setNextBit(true);
				body.setText(body.getText().substring(0, (body.getText().length() - 1)));
			}
			else
			{
				// Decode no space at the end as 0.
				Stego.setNextBit(false);
			}
		}
	}
	
}
