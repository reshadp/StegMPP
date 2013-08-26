package stegmpp.xmpp;

import stegmpp.ui.Style;
import stegmpp.ui.UI;

/**
 * The presence class handles presence objects.
 * @author reshad
 */
public class Presence
{
	
	/**
	 * Process received presence information from the server.
	 * @param tag The presence tag to process.
	 */
	public static void receivePresence(org.jdom2.Document tag)
	{
		
		if(tag.getRootElement().getAttribute("from") != null)
		{
			String from = tag.getRootElement().getAttribute("from").getValue();
			if(from.substring(0, from.indexOf('/')).trim().equals(Session.to.trim())) // Check if the from field in the pressence tag is the same as the id we are messaging.
			{
				UI.getUI().print("[System] ",Style.SYSTEM);
				if(tag.getRootElement().getChildText("show") != null)
				{
					UI.getUI().println(Session.to + " Online");
				}
				else if(tag.getRootElement().getAttributeValue("type") != null)
				{
					if(tag.getRootElement().getAttributeValue("type").equals("unavailable"))
					{
						UI.getUI().println(Session.to + " Offline");
					}
				}
			}
		}
	}
}
