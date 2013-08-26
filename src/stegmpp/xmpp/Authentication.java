package stegmpp.xmpp;

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
	
	/**
	 * 
	 * @param mechanism The type of authentication mechanism (only "PLAIN" supported in this implementation, but can be extended to support other SASL mechanisms).
	 * @param username The username of the user to validate.
	 * @param password The password of the user to validate.
	 * @return true if the user has been authenticated successfully, false if the user has failed to authenticate successfully.
	 */
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
	
	/**
	 * The authPlain method returns the SASL authentication string for a given username and password combination using the SASL PLAIN authentication mechanism.
	 * @param u The username to authenticate against.
	 * @param p The password to authenticate with. 
	 * @return  The SASL PLAIN authentication string.
	 */
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
	
	/**
	 * Process the authentication response from the server.
	 * @param tag The success or failure tag.
	 */
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
