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
	public static Document buildMessage()
	{
		Element message = new Element("message");
		String from = StegMPP.getUI().getConnect().getUsername() + '@' + StegMPP.getUI().getConnect().getServer();
		String to = StegMPP.getUI().getConnect().getTo();
		message.setAttribute("from", from);
		message.setAttribute("to", to);
		message.setAttribute("type", "chat");
		message.setAttribute("id", Session.getNextId());
		return new Document(message);
	}
	
	public static Document setMessage(Document tag,String message)
	{
		Element body=new Element("body");
		body.addContent(message.trim());//remove leading and trailing whitespace  from the string
		tag.getRootElement().addContent(body);
		
		
		if(Stego.isSender())
		{
			StegSender ss = new StegSender(tag);
		}
		return tag;
	}
	
	public static void sendMessage(Document tag)
	{
		String s = new XMLOutputter().outputString(tag);
		s=s.substring(s.indexOf('>')+1); // get rid of <?xml ?> tag.
		//System.out.println(s);
		PrintWriter pw = Session.getPrintWriter();
		pw.println(s);
		pw.flush();
	}
	
	public static void receiveMessage(org.jdom2.Document tag)
	{
		//System.err.println(new XMLOutputter().outputString(tag));
		if(tag.getRootElement().getAttribute("from") != null &&  tag.getRootElement().getChild("body") != null)
		{
			if (!Stego.isSender())
			{
				StegReciever sr = new StegReciever(tag);
			}
			
			String from = tag.getRootElement().getAttribute("from").getValue();
			if(from.substring(0, from.indexOf('/')).trim().equals(Session.getTo().trim()))
			{
				StegMPP.getUI().print("[" + from.substring(0,from.indexOf('@')) + "] ",Style.OUTGOING);
				StegMPP.getUI().println(tag.getRootElement().getChild("body").getText());
			}
		}
	}
}
