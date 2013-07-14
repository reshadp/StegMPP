/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 *
 * @author reshad
 */
public class ByteListHandler 
{
	
	/**
	 * Main method used to test ByteListHandler
	 * @param args
	 */
	public static void main(String [] args)
	{
		
		System.out.println();
		byte [] b = {1,2,3};
		ByteListHandler blh = new ByteListHandler(b);
		ByteListHandler bl1 = new ByteListHandler();
		while(blh.hasNextBit())
		{
			boolean bool = blh.getNextBit();
			//System.out.println(bool);
			bl1.setNextBit(bool);
		}
		
		System.out.println(bl1.getBytes());
	}
	
	
	private List<Byte> bytes;
	private BitSet currentbyte;
	private int position;
	private int pointer;
	private boolean eot;
	
	
	
	/**
	 * Constructor creates a new ByteListHandler.
	 * @param bytes the list of Bytes.
	 */
	public ByteListHandler(List <Byte> bytes)
	{
		this.bytes = bytes;
		this.position=0;
		currentbyte = createBitSet(bytes.get(position));
		eot = false;
	}
	
	/**
	 * Constructor creates a new ByteListHandler.
	 */
	public ByteListHandler()
	{
		this.bytes = new ArrayList<>();
		this.position=0;
		currentbyte = new BitSet(8);
		eot = false;
	}
	
	/**
	 * Constructor creates a new ByteListHandler.
	 * @param bytes an array of bytes to make into a list.
	 */
	public ByteListHandler(byte [] bytes)
	{
		this(byteToList(bytes));
	}
	
	
	/**
	 * Get the value of the next Bit.
	 * @return the boolean value of the next bit. true = 1, fales = 0.
	 */
	public boolean getNextBit()
	{
		if (pointer > 7)
		{
			//System.out.println();
			System.out.println(currentbyte.toByteArray()[0]);
			currentbyte = createBitSet(bytes.get(++position));
		}
		return currentbyte.get(pointer++);
	}
	
	
	/**
	 * Has next bit checks if the Byte list has more bits to be read.
	 * @return true if either the pointer to the BitSet is at the end if the byte
	 * or if the pointer to the bytelist is at the end.
	 */
	public boolean hasNextBit()
	{
		if (pointer <= 7 || position < bytes.size()-1 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Set the next bit of the Byte.
	 * Also check for the end of transmission character if the byte is complete.
	 * @param bit the value of the bit to set true for 1, false for 0.
	 */
	public void setNextBit(boolean bit)
	{
		
		if (bit)
		{
			currentbyte.set(pointer++);
		}
		else
		{
			currentbyte.clear(pointer++);
		}
		if (pointer > 7)
		{
			bytes.add(currentbyte.toByteArray()[0]);
			pointer = 0;
			System.out.println(currentbyte.toByteArray()[0]);
			if (Stego.endOfTransmissionFound(currentbyte.toByteArray()[0]))
			{
				eot=true;
				System.out.println("EOT");
			}
			currentbyte = new BitSet(8);
		}
	}
	
	
	/**
	 * Convert an array of bytes to a list of Bytes
	 * @param array the array of bytes.
	 * @return The List of Bytes.
	 */
	public static List<Byte> byteToList(byte [] array)
	{
		List<Byte> list = new ArrayList<>();
		for (byte b: array)
		{
			list.add(b);
		}
		return list;
	}
	
	/**
	 * Convert a List of Bytes to an array of bytes.
	 * @param list the List of Bytes.
	 * @return the array of bytes.
	 */
	public static byte[] listToByte(List<Byte> list)
	{
		byte[] array = new byte[list.size()];
		for(int i=0; i< list.size();i++)
		{
			array[i]=list.get(i);
		}
		return array;
	}
	
	
	/**
	 * Get the End Of Transmission status
	 * @return true if EOT has occurred.
	 */
	public boolean getEOT()
	{
		return eot;
	}
	
	
	/**
	 * Get the list of bytes.
	 * @return  the list of bytes.
	 */
	public List<Byte> getBytes()
	{
		return bytes;
	}
	
	/**
	 * Create a BitSet from a single byte.
	 * @param b the byte to convert.
	 * @return the converted BitSet.
	 */
	private BitSet createBitSet(byte b)
	{
		byte [] by= {b};
		pointer = 0;
		return BitSet.valueOf(by);
	}
}

