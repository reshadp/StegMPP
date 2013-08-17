/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

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
 *
 * @author reshad
 */
public class Receiver implements Runnable
{

	private BufferedReader br;
	private final boolean ISRECIEVER;

	
	
	
	public Receiver(InputStream in, boolean isReciever)
	{
		br = new BufferedReader(new InputStreamReader(in));
		ISRECIEVER = isReciever;
	}
	

	@Override
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

					//System.out.println(currentInput);
					//System.out.println(in);

					if (currentInput.matches(RegularExpressions.OPEN_STREAM))
					{
						System.out.println("OPEN_STREAM test passed");
						input.clear();
					}
					else if (currentInput.matches(RegularExpressions.XML))
					{
						System.out.println("XML test passed");
						input.clear();
					}
					else if (currentInput.matches(RegularExpressions.STREAM_FRETURES))
					{
						System.out.println("STREAM_FRETURES test passed");
						input.clear();
					}


					org.jdom2.input.SAXBuilder saxBuilder = new SAXBuilder();


					org.jdom2.Document tag = saxBuilder.build(new StringReader(currentInput));
					System.out.println(tag.getRootElement().getName());
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
							//System.out.println(currentInput);
							break;
						default:
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
