/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.patuck.stegmpp.xmpp;

/**
 * The RegularExpressions class contains a list of final regular expressions.
 * 
 * anything with the suffix ST allows 
 * 
 * @author reshad
 */
public class RegularExpressions
{

	public static final String XML =
			  "(<)"			// Open tag
			+ "(\\?)"		// Questionmark(?) character
			+ "(xml)"		// Word xml
			+ ".*?"			// Non-greedy match on filler
			+ "(>)";		// Close tag
	
	public static final String OPEN_STREAM =
			  "(<)"			// Begin tag
			+ "(stream)"	// Word "stream"
			+ "(:)"			// Colon(:) character
			+ "(stream)"	// Word "stream"
			+ "( )"			// White Space
			+ ".*?"			// Non-greedy match on filler
			+ "(>)";		// Close tag
	
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
