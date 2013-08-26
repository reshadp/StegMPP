package stegmpp.stego;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.Document;

/**
 * The StegSender class is used to encode bits onto a tag using the selected methods for steganography.
 * @author reshad
 */
public class StegSender
{
	private Document tag;
	
	/**
	 * Set encode bits onto the tag to be sent.
	 * @param tag the tag to encode.
	 */
	public StegSender(Document tag)
	{
		List<String> types = Stego.getTypes();
		for(String s : types)
		{
			try
			{
				// Get an instance of the class named in the type list and call its send method.
				((StegMethod) Class.forName("stegmpp.stego." + s).newInstance()).send(tag);
			}
			catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex)
			{
				Logger.getLogger(StegSender.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
	}
	
}
