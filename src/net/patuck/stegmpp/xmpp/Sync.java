package net.patuck.stegmpp.xmpp;

/**
 * This class is a stub to synchronise on.
 * @author reshad
 */
public class Sync
{
	private static Sync sync = new Sync();
	
	/**
	 * Get the Sync object to be synchronised on.
	 * @return the Sync object.
	 */
	public static Sync getSyncObject()
	{
		return sync;
	}
	
	/*
	 * Declare a private constructor to override the default constructor.
	 */
	private Sync()
	{
		;
	}
}
