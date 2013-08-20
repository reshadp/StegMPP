package net.patuck.stegmpp.stego;

import java.util.BitSet;

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
		// Print the data to be sent for debugging.
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
			getByte();
			//checkEOT();//print bit.
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
	
	/**
	 * Set the next bit to the value in the indicated.
	 * @param b the value to set the bit.
	 */
	public void setNextBit(boolean b)
	{
		data.set(index++, b);
		if(index % 8 == 0)
		{
			checkEOT(getByte());
		}
	}
	
	/**
	 * Get the next byte from the last 8 bits read or written from the data BitSet.
	 * @return the last byte read or written.
	 */
	public byte getByte()
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
		System.out.println(b[0]); // Print the byte for debugging.
		return b[0];
	}
	
	/**
	 * Check if the character is an EOT character by decoding 
	 * @param b 
	 */
	private void checkEOT(byte b)
	{
		if(Stego.endOfTransmissionFound(b))
		{
			eot = true;
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



