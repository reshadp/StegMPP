package net.patuck.stegmpp.stego;

import org.jdom2.Document;

/**
 * The StegMethod interface defines an interface for each method of steganography to implement.
 * @author reshad
 */
public interface StegMethod
{
	/**
	 * The send method encodes some bits in the message tag.
	 * @param tag the message tag.
	 */
	public void send(Document tag);
	
	/**
	 *The receive method decodes bits from an incoming message tag.
	 * @param tag the message tag.
	 */
	public void receive(Document tag);
}
