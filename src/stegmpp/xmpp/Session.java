package stegmpp.xmpp;

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
	static String username = "username";
	static String password = "password";
	static String server = "localhost";
	static int port = 5222;
	
	
	/**
	 * Getter method to get to.
	 * @return the to value.
	 */
	public static String getTo()
	{
		return to;
	}
	
	
	/**
	 * Getter method to get the next ID value.
	 * increment the id value before returning it.
	 * @return the next id value.
	 */
	public static String getNextId()
	{
		id++;
		return Long.toHexString(id);
	}

	
	/**
	 * Getter method to get the username.
	 * @return the username.
	 */
	public static String getUsername()
	{
		return username;
	}
	
	
	/**
	 * Setter method for username.
	 * @param username the username to set.
	 */
	public static void setUsername(String username)
	{
		Session.username = username;
	}
	
	
	/**
	 * Getter method to get the password.
	 * @return the password.
	 */
	public static String getPassword()
	{
		return password;
	}
	
	
	/**
	 * Setter method for the password.
	 * @param password the password to set.
	 */
	public static void setPassword(String password)
	{
		Session.password = password;
	}
	
	
	/**
	 * Setter method to for the server.
	 * @param server the server.
	 */
	public static void setServer(String server)
	{
		Session.server = server;
	}
	
	
	/**
	 * Getter method to get the server name.
	 * @return the server name.
	 */
	public static String getServer()
	{
		return server;
	}
	
	
	/**
	 * Setter method to set the port.
	 * @param port the port number.
	 */
	public static void setPort(int port)
	{
		Session.port = port;
	}
	
	
	/**
	 * Getter method to get the port.
	 * @return the port.
	 */
	public static int getPort()
	{
		return port;
	}
	
	
	/**
	 * Getter method to get the connection object.
	 * @return the connection object.
	 */
	public static Connection getConnection()
	{
		return connection;
	}
	
	/**
	 * Connect to the server.
	 */
	public static void connect()
	{
		connection.connect();
	}
	
	
	/**
	 * Disconnect from the server.
	 */
	public static void disconnect()
	{
		connection.disconnect();
	}
	
	
	/**
	 * Setter method for to
	 * @param to the new value for the to attribute.
	 */
	public static void setTo(String to)
	{
		Session.to = to;
	}
	
	
}
