/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;
import org.jdom2.Namespace;

/**
 * This class handles XEP-0085 chat state notifications.
 * It does this by creating a new thread and then tracking changes in chat state.
 * @author reshad
 */
public class XEP0085 implements Runnable
{
	private static final Element ACTIVE = new  Element("active").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element INACTIVE = new  Element("inactive").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element GONE = new  Element("gone").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element COMPOSING = new  Element("composing").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element PAUSED = new  Element("paused").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	
	private static Element currentStatus = COMPOSING;
	private static String lastText;
	private String text;
	
	public XEP0085(String text)
	{
		this.text = text;
	}
	
	@Override
	public void run()
	{
		if(text.equals("") && (currentStatus == COMPOSING || currentStatus == PAUSED))
		{
			currentStatus = ACTIVE;
			send(ACTIVE);
			lastText =  text;
			//wait 2:00 min
			//if text same
			//send inactive
		}
		
		else if(!text.equals(""))
		{
			if (currentStatus == ACTIVE || currentStatus == PAUSED)
			{
				send(COMPOSING);
				currentStatus = COMPOSING;
			}
			lastText =  text;
			try
			{
				synchronized(this)
				{
					wait(30000);
				}
			}
			catch (InterruptedException ex)
			{
				Logger.getLogger(XEP0085.class.getName()).log(Level.SEVERE, null, ex);
			}
			if(lastText.equals(text) && currentStatus == COMPOSING)
			{
				send(PAUSED);
				currentStatus = PAUSED;
				//wait 1:30 min 
				//if text same
				//send inactive
			}
		}
			
	}
	
	/**
	 * Send the chat state.
	 * Create a Message object and send the chat state.
	 * @param e The state to send.
	 */
	private void send(Element e)
	{
		Message m = new Message();
		m.addElement(e.clone());
		m.sendMessage();
	}
}
