/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

/**
 * This class is a stub to sync on.
 * @author reshad
 */
public class Sync
{
	private static Sync sync = new Sync();
	
	public static Sync getSyncObject()
	{
		return sync;
	}
	
	private Sync()
	{
		// declare a private constructo to override the default constructor
	}
}
