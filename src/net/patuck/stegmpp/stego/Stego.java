/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.patuck.stegmpp.crypto.Rabbit;

/**
 * 
 * @author reshad
 */
public class Stego
{
	private static ByteListHandler data;
	private static boolean sender = false;
	private static List<String> types;
	private static Rabbit rabbit = new Rabbit();;
	private static byte [] r_key;
	private static byte [] r_iv;
	
	
	/**
	 * This method sets up the steganography both for sender and reciever. 
	 * @param plainText
	 * @param key
	 * @param isSender
	 * @param types 
	 */
	public static void Setup(String plainText, String key, boolean isSender, List<String> types)
	{
		try
		{
			sender = isSender;
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			byte [] b_key = md.digest(key.getBytes("UTF-8")); 
			r_key = Arrays.copyOfRange(b_key, 0, 16);
			r_iv = Arrays.copyOfRange(b_key, 17, 25);
			Stego.types = types;
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
		catch (UnsupportedEncodingException ex)
		{
			Logger.getLogger(Stego.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	
	/**
	 * Setup the rabbit cypher.
	 * this method resets the cypher, then sets the key and iv values.
	 */
	private static void setupCypher()
	{
		rabbit.reset();
		rabbit.setupKey(r_key);
		rabbit.setupIV(r_iv);
	}
	
	
	/**
	 * Check if the last character found is an EndOfTransmission character.
	 * @param b The last byte received.
	 * @return true if the character is an End of Transmission(\u0004) character.
	 */
	public static boolean endOfTransmissionFound(byte b)
	{
		System.out.println(b);
		if (rabbit.decryptChar(b) == '\u0004')
		{
			return true;
		}
		return false;
	}
	
	
	/**
	 * Check if the client is a sender or a receiver.
	 * @return true if the client is a sender, false if the client is a receiver.
	 */
	public static boolean isSender()
	{
		return sender;
	}
	
	
	/**
	 * Get the value of the next bit.
	 * @return true if the next bit is set(1), false if the next bit is clear(0).
	 */
	public static boolean getNextBit()
	{
		boolean b = data.getNextBit();
		System.out.println(b);
		return b;
	}
	
	
	/**
	 * Set the value of the next bit.
	 * @param b the value of the next bit, true for 1, false for 0.
	 */
	public static void setNextBit(boolean b)
	{
		System.out.println(b);
		data.setNextBit(b);
	}
	
	
	/**
	 * Check if an End of Transmission character has been found.
	 * @return true if an End of Transmission character has been hit.
	 */
	public static boolean checkEOT()
	{
		return data.getEOT();
	}
	
	
	/**
	 * Check if there is another bit available in the byte buffer.
	 * @return return true of there is another bit to send.
	 */
	public static boolean hasNextBit()
	{
		return data.hasNextBit();
	}
	
	
	/**
	 * Get the plain text after the receiver has received all the data.
	 * @return The plain text.
	 */
	public static String getPlainText()
	{
		byte [] ct = data.getBytes();
		
		
		setupCypher();
		String plainText="";
		try
		{
			plainText = new String(rabbit.crypt(ct), "UTF-8");
		}
		catch (UnsupportedEncodingException ex)
		{
			Logger.getLogger(Stego.class.getName()).log(Level.SEVERE, null, ex);
		}
		// Trim EOT character.
		plainText = plainText.substring(0,plainText.length()-1);
		//System.out.println(plainText);
		return plainText;
	}

	
	/**
	 * Get the list of types.
	 * @return the list of types.
	 */
	public static List<String> getTypes()
	{
		return types;
	}
}
