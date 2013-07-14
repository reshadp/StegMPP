package net.patuck.stegmpp;

import net.patuck.stegmpp.ui.UI;
import net.patuck.stegmpp.xmpp.Session;

/**
 *
 * @author Reshad Patuck
 * @version 0.1
 */
public class StegMPP
{
	
	private static UI ui;
	private static Session session;
	

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args)
	{
		session = new Session();
		ui = new UI();
		ui.build();
	}
	
	/**
	 * Getter method for UI object.
	 */
	public static UI getUI()
	{
		return ui;
	}

	/**
	 * Getter method for Session object.
	 * @return
	 */
	public static Session getSession()
	{
		return session;
	}
	
	
}