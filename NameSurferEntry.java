/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import acm.util.*;
import java.util.*;

public class NameSurferEntry implements NameSurferConstants {

/* Constructor: NameSurferEntry(line) */
/**
 * Creates a new NameSurferEntry from a data line as it appears
 * in the data file.  Each line begins with the name, which is
 * followed by integers giving the rank of that name for each
 * decade.
 */
	public NameSurferEntry(String line) {
		this.parseLine(line);
	}

/* Method: getName() */
/**
 * Returns the name associated with this entry.
 */
	public String getName() {
		return this.name;
	}

/* Method: getRank(decade) */
/**
 * Returns the rank associated with an entry for a particular
 * decade.  The decade value is an integer indicating how many
 * decades have passed since the first year in the database,
 * which is given by the constant START_DECADE.  If a name does
 * not appear in a decade, the rank value is 0.
 */
	public int getRank(int decade) {
		if (decade < NDECADES && decade >= 0) {
			return this.ranks[decade];
		}
		return 0;
	}

/* Method: toString() */
/**
 * Returns a string that makes it easy to see the value of a
 * NameSurferEntry.
 */
	public String toString() {
		return this.name + " " + Arrays.toString(this.ranks);
	}
	
	
/**
 * parses the line to set the name and ranks of this NameSurferEntry
 * equal to the name and ranks of the String line
 */
	private void parseLine(String line) {
		int nameStart = 0;
		int nameEnd = line.indexOf(" ");
		this.name = line.substring(nameStart, nameEnd);
	
		int nextIntStart = nameEnd + 1;
		int nextIntEnd = line.indexOf(" ", nameEnd + 1);
		for(int i = 0; i < NDECADES; i++) {
			String nextInt;
			if (i == NDECADES - 1) {
				nextInt = line.substring(nextIntStart); // final substring goes to end of string
			}
			else {
				nextInt = line.substring(nextIntStart, nextIntEnd);
			}
			this.ranks[i] = Integer.parseInt(nextInt);
			nextIntStart = nextIntEnd + 1;
			nextIntEnd = line.indexOf(" ", nextIntEnd + 1);
	}
}
	
/** private instance variables */
	private String name = null;
	private int[] ranks = new int[NDECADES]; 
}

