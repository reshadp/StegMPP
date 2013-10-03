package stegmpp.xmpp;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Element;

/**
 * The IQ class represents an in
 * @author reshad
 */
public class IQ
{
	
	private static PrintWriter pw=null;

	/**
	 * Set the PrintWriter to write to the XMPP server.
	 * @param p the PrintWriter object.
	 */
	public static void setPrintWriter(PrintWriter p)
	{
		pw = Session.pw;
	}

	
	/**
	 * Send an IQ message.
	 * @param type the type of message.
	 * @param to the to field.
	 * @param data the data in the tag.
	 */
	public static void sendIQ(String type, String to, String data)
	{
		if (to == null)
		{
			pw.println("<iq type='" + type + "' id='" + Session.getNextId() + "'>" + data + "</iq>");
		}
		else
		{
			pw.println("<iq type='" + type +"' id='" + Session.getNextId() + "' to='" + to + "'>" + data + "</iq>");
		}
		pw.flush();
		try		
		{
			// Wait for response from server. typically notrequired but makes life easier.
			synchronized(Sync.getSyncObject())
			{
				Sync.getSyncObject().wait();
			}
		}
		catch (InterruptedException ex)
		{
			Logger.getLogger(IQ.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	
	/**
	 * Read and process the IQ tag.
	 * @param tag the IQ tag.
	 */
	public static void receiveIQ(org.jdom2.Document tag)
	{
		switch (tag.getRootElement().getAttribute("type").getValue())
		{
			case "result":
				synchronized(Sync.getSyncObject())
				{
					Sync.getSyncObject().notifyAll();
				}
				break;
			case "error":
				System.out.println("error in iq.");
				synchronized(Sync.getSyncObject())
				{
					Sync.getSyncObject().notifyAll();
				}
				break;
			case "get":
				for(Element element:tag.getRootElement().getChildren())
				{
					// Respond to ping.
					if(element.getName().equals("ping"))
					{
						System.out.println("PONG");
						pw.println("<iq from='" + tag.getRootElement().getAttributeValue("to") + "' to='" + tag.getRootElement().getAttributeValue("from") + "' id='" + tag.getRootElement().getAttributeValue("id") + "' type='result'/>");
						pw.flush();
					}
				}
				
				// Ignore other get messages. This is just a proof of concept and not a fully functional XMPP Client.
				//tag.toString();
				break;
		}
		
	}
}
