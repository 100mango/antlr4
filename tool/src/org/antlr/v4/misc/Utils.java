package org.antlr.v4.misc;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/** */
public class Utils {
	public static final int INTEGER_POOL_MAX_VALUE = 1000;
	static Integer[] ints = new Integer[INTEGER_POOL_MAX_VALUE+1];

	/** Integer objects are immutable so share all Integers with the
	 *  same value up to some max size.  Use an array as a perfect hash.
	 *  Return shared object for 0..INTEGER_POOL_MAX_VALUE or a new
	 *  Integer object with x in it.  Java's autoboxing only caches up to 127.
	 */
	public static Integer integer(int x) {
		if ( x<0 || x>INTEGER_POOL_MAX_VALUE ) {
			return new Integer(x);
		}
		if ( ints[x]==null ) {
			ints[x] = new Integer(x);
		}
		return ints[x];
	}
	
    public static String stripFileExtension(String name) {
        if ( name==null ) return null;
        int lastDot = name.lastIndexOf('.');
        if ( lastDot<0 ) return name;
        return name.substring(0, lastDot);
    }

    // Seriously: why isn't this built in to java? ugh!
    public static String join(Iterator iter, String separator) {
        StringBuilder buf = new StringBuilder();
        while ( iter.hasNext() ) {
            buf.append(iter.next());
            if ( iter.hasNext() ) {
                buf.append(separator);
            }
        }
        return buf.toString();
    }

	public static String join(Object[] a, String separator) {
		StringBuilder buf = new StringBuilder();
		for (int i=0; i<a.length; i++) {
			Object o = a[i];
			buf.append(o.toString());
			if ( (i+1)<a.length ) {
				buf.append(separator);
			}
		}
		return buf.toString();
	}

	/** Given a source string, src,
		a string to replace, replacee,
		and a string to replace with, replacer,
		return a new string w/ the replacing done.
		You can use replacer==null to remove replacee from the string.

		This should be faster than Java's String.replaceAll as that one
		uses regex (I only want to play with strings anyway).
	*/
	public static String replace(String src, String replacee, String replacer) {
		StringBuffer result = new StringBuffer(src.length() + 50);
		int startIndex = 0;
		int endIndex = src.indexOf(replacee);
		while(endIndex != -1) {
			result.append(src.substring(startIndex,endIndex));
			if ( replacer!=null ) {
				result.append(replacer);
			}
			startIndex = endIndex + replacee.length();
			endIndex = src.indexOf(replacee,startIndex);
		}
		result.append(src.substring(startIndex,src.length()));
		return result.toString();
	}

	public static String sortLinesInString(String s) {
		String lines[] = s.split("\n");
		Arrays.sort(lines);
		List<String> linesL = Arrays.asList(lines);
		StringBuffer buf = new StringBuffer();
		for (String l : linesL) {
			buf.append(l);
			buf.append('\n');
		}
		return buf.toString();
	}

}
