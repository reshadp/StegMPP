/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author reshad
 */
public class TrailingSpace implements StegMethod
{

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

	@Override
	public void recieve(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null)
		{
			if(body.getText().charAt(body.getText().length() - 1 ) == ' ')
			{
				Stego.setNextBit(true);
				body.setText(body.getText().substring(0, (body.getText().length() - 1)));
			}
			else
			{
				Stego.setNextBit(false);
			}
		}
	}
	
}
