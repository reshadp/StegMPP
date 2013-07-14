
package net.patuck.stegmpp.crypto;



/**
 * The RC4 class is used to encrypt and decrypt data using the RC4 stream cypher.
 * This code is taken from {@link http://stackoverflow.com/a/12290876/1115437} by owlstead.
 * 
 * aes as a stream cypher
 * 3 4 years ago eStrean cyphers 
 * 
 * @author reshad
 */
public class RC4
{

	/**
	 * The main method is used to test the algorithm.
	 * @param args 
	 */
	public static void main(String [] args)
	{
		byte[] key={1,2,3,4};
		String text="Reshad";
		byte[] pt=text.getBytes();
		System.out.println("Plaintext " + new String(pt));
		for(int i=0; i<pt.length;i++)
		{
			System.out.print(pt[i]+" | ");
		}
		System.out.println();
		RC4 rc4=new RC4(key);
		byte[] ct=rc4.encrypt(pt);
		System.out.print("Cyphertext ");
		for(int i=0; i<ct.length;i++)
		{
			System.out.print(ct[i]+" | ");
		}
		System.out.println();
		RC4 rc4a=new RC4(key);
		byte[] decrypted=rc4a.decrypt(ct);
		System.out.println("Decrypted " + new String(decrypted));
		for(int i=0; i<decrypted.length;i++)
		{
			System.out.print(decrypted[i]+" | ");
		}
		System.out.println();
	}
	
	
	private final byte[] S = new byte[256];
	private final byte[] T = new byte[256];
	private final int keylen;
	
	/**
	 * The constructor creates a new RC4 object and initialises the key.
	 * This code was taken from 
	 * @param key The encryption/decryption key.
	 */
	public RC4(final byte[] key)
	{
		if (key.length < 1 || key.length > 256)
		{
			throw new IllegalArgumentException("key must be between 1 and 256 bytes");
		}
		else
		{
			keylen = key.length;
			for (int i = 0; i < 256; i++)
			{
				S[i] = (byte) i;
				T[i] = key[i % keylen];
			}
			int j = 0;
			for (int i = 0; i < 256; i++)
			{
				j = (j + S[i] + T[i]) & 0xFF;
				S[i] ^= S[j];
				S[j] ^= S[i];
				S[i] ^= S[j];
			}
		}
	}

	
	
	/**
	 * The encrypt method encrypts an array of bytes using the key set when the RC4 class was created.
	 * @param plaintext the plain text as a byte array.
	 * @return the cyphertext as a byte array.
	 */
	public byte[] encrypt(final byte[] plaintext)
	{
		final byte[] ciphertext = new byte[plaintext.length];
		int i = 0, j = 0, k, t;
		for (int counter = 0; counter < plaintext.length; counter++)
		{
			i = (i + 1) & 0xFF;
			j = (j + S[i]) & 0xFF;
			S[i] ^= S[j];
			S[j] ^= S[i];
			S[i] ^= S[j];
			t = (S[i] + S[j]) & 0xFF;
			k = S[t];
			ciphertext[counter] = (byte) (plaintext[counter] ^ k);
		}
		return ciphertext;
	}

	/**
	 * The decrypt method decrypts an array of bytes using the key set when the RC4 class was created.
	 * @param ciphertext the cyphertext as a byte array.
	 * @return the plaintext as a byte array.
	 */
	public byte[] decrypt(final byte[] ciphertext)
	{
		return encrypt(ciphertext);
	}
}