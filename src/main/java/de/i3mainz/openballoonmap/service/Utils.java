package de.i3mainz.openballoonmap.service;

/**
 * CLASS Utils
 *
 * @author Martin Unold M.Sc.
 * @author Florian Thiery M.Sc.
 */
public interface Utils {

	static final int RADIX = 36;
	static final int MAX_NR = RADIX * RADIX * RADIX * RADIX * RADIX;

	public static String intToCodeString(int value) {
		return Integer.toString(value, RADIX);
	}

	public static int codeStringToInt(String value) {
		try {
			return Integer.valueOf(value, RADIX);
		} catch (Exception e) {
			return 0;
		}
	}
}
