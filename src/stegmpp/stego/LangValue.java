/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package stegmpp.stego;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * Encode and decode one bit in as the value of the lang attribute in the body of a message.
 * Value "en-GB" as 1 and "en-US" as 0.
 * @author reshad
 */
public class LangValue implements StegMethod
{

	@Override
	public void send(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null && Stego.hasNextBit())
		{
			if(Stego.getNextBit())
			{
				// Encode 1 as xml:lang=en-GB
				body.setAttribute("lang", "en-GB", Namespace.XML_NAMESPACE);
			}
			else
			{
				// Encode 0 as xml:lang=en-US
				body.setAttribute("lang", "en-US", Namespace.XML_NAMESPACE);
			}
		}
	}

	@Override
	public void receive(Document tag)
	{
		Element body = tag.getRootElement().getChild("body");
		if(body != null)
		{
			if(body.getAttributeValue("lang", Namespace.XML_NAMESPACE) != null)
			{
				switch (body.getAttributeValue("lang", Namespace.XML_NAMESPACE))
				{
					case "en-GB":
						// Decode lang attribute "en-GB" as 1.
						Stego.setNextBit(true);
						break;
					case "en-US":
						// Decode lang attribute "en-US" as 0.
						Stego.setNextBit(false);
						break;
				}
			}
		}
	}
	
}
