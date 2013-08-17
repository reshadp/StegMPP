/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;

/**
 * This class handles tags for authentication.
 * It only works for PLAIN authentication but can be extended if need be.
 * @author reshad
 */
public class Authentication
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
	
	
	public static boolean sendAuth(String mechanism, String username, String password)
	{
		pw.println("<auth xmlns='urn:ietf:params:xml:ns:xmpp-sasl' mechanism='" + mechanism + "'>" + authPlain(username,password) + "==</auth>");
		pw.flush();
		synchronized(Sync.getSyncObject())
		{
			try
			{
				Sync.getSyncObject().wait();
			}
			catch (InterruptedException ex)
			{
				Logger.getLogger(Authentication.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		return Session.authenticated;
	}
	
	
	private static String authPlain(String u,String p)
	{
		sun.misc.BASE64Encoder en = new BASE64Encoder();
		byte [] username =u.getBytes();
		byte [] password = p.getBytes();
		byte [] l = new byte[username.length+password.length+2];
		l[0]=0;
		int index=1;
		for (int i=0; i<username.length;i++,index++)
		{
			l[index]=username[i];
		}
		l[index]=0;
		index++;
		for (int i=0; i<password.length;i++,index++)
		{
			l[index]=password[i];
		}
		return en.encode(l);
	}
	
	public static void receiveAuth(org.jdom2.Document tag)
	{
		switch (tag.getRootElement().getName())
		{
			case "success":
				Session.authenticated = true;
				break;
			case "failure":
				Session.authenticated = false;
				break;
		}
		synchronized(Sync.getSyncObject())
		{
			Sync.getSyncObject().notifyAll();
		}
	}
}
