/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

import java.io.PrintWriter;
import net.patuck.stegmpp.StegMPP;
import net.patuck.stegmpp.stego.StegReciever;
import net.patuck.stegmpp.stego.StegSender;
import net.patuck.stegmpp.stego.Stego;
import net.patuck.stegmpp.ui.Style;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

/**
 * This class manages the message tag.
 * @author reshad
 */
public class Message
{
	private Document messageTag;
	
	public Message()
	{
		Element message = new Element("message");
		String from = StegMPP.getUI().getConnect().getUsername() + '@' + StegMPP.getUI().getConnect().getServer();
		String to = StegMPP.getUI().getConnect().getTo();
		message.setAttribute("from", from);
		message.setAttribute("to", to);
		message.setAttribute("type", "chat");
		message.setAttribute("id", Session.getNextId());
		messageTag =  new Document(message);
	}
	
//	/**
//	 * build the message.
//	 * @return The xml document representing the message.
//	 */
//	public static Document buildMessage()
//	{
//		Element message = new Element("message");
//		String from = StegMPP.getUI().getConnect().getUsername() + '@' + StegMPP.getUI().getConnect().getServer();
//		String to = StegMPP.getUI().getConnect().getTo();
//		message.setAttribute("from", from);
//		message.setAttribute("to", to);
//		message.setAttribute("type", "chat");
//		message.setAttribute("id", Session.getNextId());
//		return new Document(message);
//	}
	
	/**
	 * Set the text in the body of the message.
	 * @param tag The document representing the message
	 * @param message The message text.
	 */
	public void addBody(String message)
	{
		Element body=new Element("body");
		body.addContent(message.trim());//remove leading and trailing whitespace  from the string
		
		addElement(body);
	}
	
	/**
	 * Add an element to the message.
	 * @param tag
	 * @param element
	 */
	public void addElement(Element element)
	{
		messageTag.getRootElement().addContent(element);
	}
	
	public synchronized void sendMessage()
	{
		
		if(Stego.isSender())
		{
			StegSender ss = new StegSender(messageTag);
		}
		String s = new XMLOutputter().outputString(messageTag);
		s=s.substring(s.indexOf('>')+1); // get rid of <?xml ?> tag.
		//System.out.println(s);
		PrintWriter pw = Session.pw;
		pw.println(s);
		pw.flush();
	}
	
	public static synchronized void receiveMessage(Document tag)
	{
		//System.err.println(new XMLOutputter().outputString(tag));
		if(tag.getRootElement().getAttribute("from") != null )
		{
			if (!Stego.isSender()) //If reciever
			{
				StegReciever sr = new StegReciever(tag);
			}
			
			String from = tag.getRootElement().getAttribute("from").getValue();
			if(from.substring(0, from.indexOf('/')).trim().equals(Session.to.trim()))
			{
				if(tag.getRootElement().getChild("body") != null)
				{
					StegMPP.getUI().print("[" + from.substring(0,from.indexOf('@')) + "] ",Style.OUTGOING);
					StegMPP.getUI().println(tag.getRootElement().getChild("body").getText());
				}
			}
		}
	}
}
