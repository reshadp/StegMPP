package net.patuck.stegmpp.xmpp;

/**
 * The RegularExpressions class contains a list of final regular expressions.
 * The regular expressions defined here are used to catch cases which can't be parsed by the XML parser easily.
 * @author reshad
 */
public class RegularExpressions
{
	
	/**
	 * The opening xml tag.
	 */
	public static final String XML =
			  "(<)"			// Open tag
			+ "(\\?)"		// Questionmark(?) character
			+ "(xml)"		// Word xml
			+ ".*?"			// Non-greedy match on filler
			+ "(>)";		// Close tag
	
	/**
	 * The stream tag which is only closed at the end of the session.
	 */
	public static final String OPEN_STREAM =
			  "(<)"			// Begin tag
			+ "(stream)"	// Word "stream"
			+ "(:)"			// Colon(:) character
			+ "(stream)"	// Word "stream"
			+ "( )"			// White Space
			+ ".*?"			// Non-greedy match on filler
			+ "(>)";		// Close tag
	
	/**
	 * The features tag which is not easy to process with the XML parser.
	 */
	public static final String STREAM_FRETURES =
			  "(<)"			// Begin tag
			+ "(stream)"	// Word "stream"
			+ "(:)"			// Colon(:) character
			+ "(features)"	// Word "features"
			+ "(>)"			// Close tag
			+ ".*?"			// Non-greedy match on filler
			+ "(<)"			// Begin tag
			+ "(\\/)"		// Slash(/) character to indicate closing tag
			+ "(stream)"	// Word "stream"
			+ "(:)"			// Colon(:) character
			+ "(features)"	// Word "features"
			+ "(>)";		// Close tag
	
}
