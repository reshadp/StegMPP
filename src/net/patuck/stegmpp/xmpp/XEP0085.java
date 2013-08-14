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
	// Define the available chat state notification elements as per the XEP specification.
	private static final Element ACTIVE = new  Element("active").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element INACTIVE = new  Element("inactive").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element GONE = new  Element("gone").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element COMPOSING = new  Element("composing").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	private static final Element PAUSED = new  Element("paused").setNamespace(Namespace.getNamespace("http://jabber.org/protocol/chatstates"));
	
	// Initialise the currentStatus to COMPOSING to allow the active status to be available on starting the session.
	private static Element currentStatus = ACTIVE;
	private static String lastText;
	private String text;
	
	/**
	 * The XEP0085 class is used to send XEP-0085 chat state notifications.
	 * @param text the text currently in the message box.
	 */
	public XEP0085(String text)
	{
		this.text = text;
	}
	
	/**
	 * Run the thread that checks the status and sends appropriate XEP-0085 notifications.
	 */
	@Override
	public void run()
	{
		if(text.equals("") && (currentStatus == COMPOSING || currentStatus == PAUSED))
		{
			currentStatus = ACTIVE;
			lastText =  text;
			send(ACTIVE);
			sleepSeconds(120);
			if(lastText.equals(text) && currentStatus == PAUSED)
			{	
				currentStatus = INACTIVE;
				send(INACTIVE);
			}
		}
		
		else if(!text.equals(""))
		{
			if (currentStatus == ACTIVE || currentStatus == PAUSED)
			{
				currentStatus = COMPOSING;
				send(COMPOSING);
			}
			lastText =  text;
			sleepSeconds(5); // XEP spec says 30 but looking at existing applications 5 seems more appropriate
			if(lastText.equals(text) && currentStatus == COMPOSING)
			{
				currentStatus = PAUSED;
				send(PAUSED);
				sleepSeconds(90);
				if(lastText.equals(text) && currentStatus == PAUSED)
				{
					currentStatus = INACTIVE;
					send(INACTIVE);
				}
			}
		}
			
	}
	
	/**
	 * Pause the thread and sleep for a certain number of seconds.
	 * @param s The number of seconds to wait
	 */
	private void sleepSeconds(long s)
	{
		try
		{
			Thread.sleep(s * 1000);
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(XEP0085.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	/**
	 * Send the chat state.
	 * Create a Message object and send the chat state.
	 * @param e The state to send.
	 */
	private synchronized void send(Element e)
	{
		Message m = new Message();
		m.addElement(e.clone());
		m.sendMessage();
	}
}
