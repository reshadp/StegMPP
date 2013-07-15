/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import org.jdom2.Document;

/**
 *
 * @author reshad
 */
public class StegSender
{
	private Document tag;
	
	/**
	 * Set encode bits onto the tag to be sent.
	 * @param tag the tag to encode.
	 */
	public StegSender(Document tag)
	{
		// Check if message sending over.
		
		this.tag = tag;
		new TrailingSpace().send(tag);
		new LeadingSpace().send(tag);
		new IDValue().send(tag);
		new TypeCase().send(tag);
	}
	
}
