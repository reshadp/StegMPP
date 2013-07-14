/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.patuck.stegmpp.crypto.Rabbit;

/**
 * 
 * @author reshad
 */
public class Stego
{
	//private static ArrayList<Byte> plainText;
	private static ByteListHandler data;
	private static boolean sender;
	private static Rabbit rabbit = new Rabbit();;
	private static byte [] r_key;
	private static byte [] r_iv;
	
	public static void Setup(String plainText, String key, boolean isSender)
	{
		try
		{
			sender = isSender;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte [] b_key = md.digest(key.getBytes()); 
			r_key = Arrays.copyOfRange(b_key, 0, 16);
			r_iv = Arrays.copyOfRange(b_key, 17, 25);
			setupCypher();
			if(isSender)
			{
				plainText = plainText + '\u0004';
				data = new ByteListHandler(rabbit.crypt(plainText.getBytes()));
			}
			else
			{
				data = new ByteListHandler();
			}
			
			
			
		}
		catch (NoSuchAlgorithmException ex)
		{
			Logger.getLogger(Stego.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private static void setupCypher()
	{
		rabbit.reset();
		rabbit.setupKey(r_key);
		rabbit.setupIV(r_iv);
	}
	
	public static boolean endOfTransmissionFound(byte b)
	{
		if (rabbit.decryptChar(b) == '\u0004')
		{
			return true;
		}
		return false;
	}
	
	public static boolean isSender()
	{
		return sender;
	}
	
	public static boolean getNextBit()
	{
		boolean b = data.getNextBit();
		System.out.println(b);
		return b;
	}
	
	public static void setNextBit(boolean b)
	{
		System.out.println(b);
		data.setNextBit(b);
	}
	
	public static boolean checkEOT()
	{
		return data.getEOT();
	}
	
	public static boolean hasNextBit()
	{
		return data.hasNextBit();
	}
	
	public static String getPlainText()
	{
		byte [] pt = ByteListHandler.listToByte(data.getBytes());
		
		
		setupCypher();
		String plainText = new String(rabbit.crypt(pt));
		System.out.println(plainText);
		return plainText;
	}
}
