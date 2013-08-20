package net.patuck.stegmpp.xmpp;

import java.io.PrintWriter;
import net.patuck.stegmpp.stego.StegReciever;
import net.patuck.stegmpp.stego.StegSender;
import net.patuck.stegmpp.stego.Stego;
import net.patuck.stegmpp.ui.Style;
import net.patuck.stegmpp.ui.UI;
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
		String from = Session.getUsername() + '@' + Session.getServer();
		String to = UI.getUI().getConnect().getTo();
		message.setAttribute("from", from);
		message.setAttribute("to", to);
		message.setAttribute("type", "chat");
		message.setAttribute("id", Session.getNextId());
		messageTag =  new Document(message);
	}
	
	
	/**
	 * Set the text in the body of the message.
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
	 * @param element The element to add to the message.
	 */
	public void addElement(Element element)
	{
		messageTag.getRootElement().addContent(element);
	}
	
	
	/**
	 * Send the message.
	 */
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
	
	
	/**
	 * Handle received messages.
	 * @param tag The received message tag.
	 */
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
					UI.getUI().print("[" + from.substring(0,from.indexOf('@')) + "] ",Style.OUTGOING);
					UI.getUI().println(tag.getRootElement().getChild("body").getText());
				}
			}
		}
	}
}
