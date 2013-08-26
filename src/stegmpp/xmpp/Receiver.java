package stegmpp.xmpp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * The Receiver class implements the Receiver thread which handles all incoming messages.
 * @author reshad
 */
public class Receiver implements Runnable
{

	private BufferedReader br;
	
	
	/**
	 * The Receiver constructor creates a new Receiver object and sets the BuffeterReader object to read from the specified InputStream.
	 * @param in The InputStream to read from.
	 */
	public Receiver(InputStream in)
	{
		br = new BufferedReader(new InputStreamReader(in));
	}
	

	/**
	 * The run method runs once the connection to the server is established and is used to get and identify the type of incoming message.
	 */
	@Override
	@SuppressWarnings("empty-statement")
	public void run()
	{

		int in = 0;
		List input = new ArrayList();
		while (true)
		{

			try
			{
				in = br.read();
				if (in == 65535 | in < 0)
				{
					continue;
				}
				input.add((char) in);

				
				String currentInput = buildString(input);
				
				if (in == '>')
				{

					// Print debug information.
					//System.out.println(currentInput);
					//System.out.println(in);

					// Regex Matching for xml input with namespaces and cases where the XML is not complete(eg: the open stream tag which is only closed when the cilent disconnects).
					if (currentInput.matches(RegularExpressions.OPEN_STREAM))
					{
						input.clear();
					}
					else if (currentInput.matches(RegularExpressions.XML))
					{
						input.clear();
					}
					else if (currentInput.matches(RegularExpressions.STREAM_FRETURES))
					{
						input.clear();
					}


					org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();


					org.jdom2.Document tag = saxBuilder.build(new StringReader(currentInput));
					switch(tag.getRootElement().getName())		
					{
						case "success":
							Authentication.receiveAuth(tag);
							break;
						case "iq":
							IQ.receiveIQ(tag);
							break;
						case "presence":
							Presence.receivePresence(tag);
							break;
						case "failure":
							Authentication.receiveAuth(tag);
							break;
						case "message":
							Message.receiveMessage(tag);
							break;
						default:
							// Print the name of the unimplemented tag.
							System.err.println(tag.getRootElement().getName());
					}
					input.clear();
				}
				
			}
			catch (IOException ex)
			{
				Logger.getLogger(Receiver.class.getName()).log(Level.SEVERE, null, ex);
			}
			catch (JDOMException ex)
			{
				;// Do not do anything as this exception occours regularly(every time until the full message is recieved.
			}

		}
	}

	/**
	 * Convert a list of characters to a string.
	 * @param in the list of characters.
	 * @return the string representation of the character list.
	 */
	private String buildString(List<Character> in)
	{
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < in.size(); i++)
		{
			builder.append(in.get(i));
		}
		return builder.toString();
	}
}
