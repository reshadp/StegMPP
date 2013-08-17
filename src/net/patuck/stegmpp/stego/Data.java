/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.stego;

import java.util.BitSet;
import net.patuck.stegmpp.ui.Style;
import net.patuck.stegmpp.ui.UI;

/**
 * This class handles encrypted data as a set of bytes.
 * @author reshad
 */
public class Data 
{
	private BitSet data;
	private int index;
	private int length;
	private boolean eot;
	
	/**
	 * Constructor creates a new Data object with specified data.
	 * @param data the byte array of data.
	 */
	public Data(byte [] data)
	{
		this.data = BitSet.valueOf(data);
		index = 0;
		for(int i = 0; i< data.length;i++)
		{
			System.out.println(data[i]);
		}
		length = data.length * 8;
		System.out.println(this.data.length());
		System.out.println(length);
	}
	
	/**
	 * Constructor creates a new empty Data object.
	 */
	public Data()
	{
		data = new BitSet();
		index = 0;
	}

	/**
	 * Get the value of the next bit.
	 * @return the boolean value of the next bit. true = 1, false = 0. 
	 */
	public boolean getNextBit()
	{
		if(index % 8 == 0 && index != 0)
		{
			checkEOT();//print bit.
		}
		return data.get(index++);
	}
	
	/**
	 * Check if there are more bits in the data.
	 * @return true if there are more bits, false if not.
	 */
	public boolean hasNextBit()
	{
		if(index <= length)
		{
			return true;
		}
		return false;
	}
	
	public void setNextBit(boolean b)
	{
		data.set(index++, b);
		if(index % 8 == 0)
		{
			checkEOT();
		}
	}
	
	private void checkEOT()
	{
		BitSet currentbyte = new BitSet(8);
		for (int i=0,j=8; j>0 ; i++, j--)
		{
			currentbyte.set(i,data.get(index-j));
		}
		byte[] b = currentbyte.toByteArray();
		if(b.length == 0)
		{
			b = new byte[]{0};
		}
		if(Stego.endOfTransmissionFound(b[0]))
		{
			eot = true;
			System.out.println("EOT");
			UI.getUI().print("[System] ", Style.SYSTEM);
			UI.getUI().println("Hidden message received");
		}
	}
	
	/**
	 * Get the list of bytes.
	 * @return  the list of bytes.
	 */
	public byte[] getBytes()
	{
		return data.toByteArray();
	}
	
	/**
	 * Get the End Of Transmission status
	 * @return true if EOT has occurred.
	 */
	public boolean getEOT()
	{
		return eot;
	}
}


