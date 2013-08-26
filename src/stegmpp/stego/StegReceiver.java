package stegmpp.stego;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import stegmpp.ui.UI;
import org.jdom2.Document;

/**
 * The StegReceiver class is used to encode bits onto a tag using the selected methods for steganography.
 * @author reshad
 */
public class StegReceiver
{
	Document tag;

	/**
	 * Get the encoded bits from an incoming tag.
	 * @param tag the tag to decode. 
	 */
	public StegReceiver(Document tag)
	{
				
		List<String> types = Stego.getTypes();
		for(String s : types)
		{
			try
			{
				if(Stego.checkEOT())
				{
					String data = Stego.getPlainText();
					UI.getUI().setData(data);
				}
				else
				{
					// Get an instance of the class named in the type list and call its receive method. 
					((StegMethod) Class.forName("stegmpp.stego." + s).newInstance()).receive(tag);
				}
			}
			catch (InstantiationException | IllegalAccessException | ClassNotFoundException ex)
			{
				Logger.getLogger(StegSender.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
	}
}
