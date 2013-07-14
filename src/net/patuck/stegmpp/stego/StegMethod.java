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
public interface StegMethod
{
	public void send(Document tag);
	public void recieve(Document tag);
}
