/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

import java.io.PrintWriter;

/**
 * This class represents an XMPP Session.
 * @author reshad
 */
public class Session
{
	
	private static long id = Math.abs(new java.util.Random().nextInt());
	private static Connection connection = new Connection();
	static boolean authenticated;
	static String to = "user@domain";
	static PrintWriter pw = null;
	
	public static String getTo()
	{
		return to;
	}
	
	public static String getNextId()
	{
		id++;
		return Long.toHexString(id);
	}

	
	public static Connection getConnection()
	{
		return connection;
	}
	
	/**
	 * Start the connection.
	 */
	public static void connect()
	{
		connection.connect();
	}
	
	public static void disconnect()
	{
		connection.disconnect();
	}
	
	public static void setupConnection(String usrename, String password, String server, int port)
	{
		connection.setUsername(usrename);
		connection.setPassword(password);
		connection.setServer(server);
		connection.setPort(port);
	}

	public static void setTo(String to)
	{
		Session.to = to;
	}
	
	
}
