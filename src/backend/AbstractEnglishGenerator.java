package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Vector;

/**
 * Class with markov chain to generate pronounceable and rememberable passwords. Class do not include entropy generation
 * and need an external producer of 64 bits (long) seed.
 * 
 * @author p4553d
 * 
 */
public abstract class AbstractEnglishGenerator implements IPassGenerator {

    private HashMap<String, Vector<String>> m_chain;
    private static String empty_prefix = "-";
    private static BigInteger number_generator = BigInteger.valueOf(1000);

    protected static String ressource = "./ressource/english";

    public AbstractEnglishGenerator() {
	m_chain = new HashMap<String, Vector<String>>();

	// read config and build mapping
	File inFile = new java.io.File(ressource);
	BufferedReader inStream = null;
	try {
	    inStream = new BufferedReader(new InputStreamReader(
		    new FileInputStream(inFile)));

	    String line = null;

	    while ((line = inStream.readLine()) != null) { // c - yeah!
		String[] result = line.split(":");
		String[] values = result[1].split(" ");

		// just for debug mode
		assert !result[0].equals("");
		assert !result[1].equals("");

		Vector<String> v = new Vector<String>(Arrays.asList(values));
		m_chain.put(result[0], v);
	    }

	    inStream.close();

	} catch (FileNotFoundException e) {
	    e.printStackTrace();
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Prints language building mapping
     * 
     * @return formated mapping
     */
    public String getMapping() {
	String res = "";
	int count = 1;
	for (String key : m_chain.keySet()) {
	    res = res + count + " ";
	    res = res + key + ": ";
	    for (String val : m_chain.get(key)) {
		res = res + val + " ";
	    }
	    res = res + "\n";
	    count = count + 1;

	}
	return res;
    }

    /**
     * Produce rememberable password from entropy seed
     * 
     * @param seed
     * @return
     */
    private String humanize(BigInteger seed) {
	String result = "";
	String current = empty_prefix;

	while (seed.compareTo(BigInteger.ZERO) > 0) {
	    Vector<String> next_list = m_chain.get(current);

	    // defensive against dead-ends
	    if (next_list == null) {
		current = empty_prefix;
		continue;
	    }

	    // generate some numbers
	    if (seed.compareTo(number_generator) <= 0) {

		BigInteger[] tmp = seed.divideAndRemainder(number_generator);

		// TODO: to refactor, can be execute only once
		seed = tmp[0]; // reduce number
		int number = tmp[1].intValue();

		result = result + "-" + number;
		continue;
	    }

	    int next_length = next_list.size();

	    BigInteger[] tmp = seed.divideAndRemainder(BigInteger
		    .valueOf(next_length));

	    seed = tmp[0]; // reduce number
	    int index = tmp[1].intValue(); // determine index of next syllable

	    current = next_list.get(index);
	    result = result + current;
	}

	return result;
    }

    /**
     * Abstract method to produce entropy as seed for human-readable password.
     * 
     * @param master
     *            Master key
     * @param site
     *            name for web site
     * @return byte array with raw output
     */
    protected abstract BigInteger generateHash(String master, String site);

    @Override
    public String generatePassword(String master, String site) {
	String password = null;
	BigInteger seed = generateHash(master, site);
	System.out.println(seed); // TODO: debug
	password = humanize(seed);
	return password;
    }

    // TODO: Refactor
    /**
     * Concatenate two byte arrays
     * 
     * @param a
     *            first array
     * @param b
     *            second array
     * @return resulting array containing values of array a followed by values of b
     */
    protected static byte[] concatenate(byte[] a, byte[] b) {
	int aLen = a.length;
	int bLen = b.length;

	byte[] res = new byte[aLen + bLen];

	System.arraycopy(a, 0, res, 0, aLen);
	System.arraycopy(b, 0, res, aLen, bLen);

	return res;
    }

    protected static byte[] extract(byte[] a, int start, int stop) {
	// TODO: some checks was useful

	byte[] res = new byte[stop - start + 1];

	for (int i = 0; i < stop - start + 1; i++) {
	    res[i] = a[start + i];
	}

	return res;
    }

    /**
     * Clip a part of byte array. First byte ist preset to be 0, due later conversion to unsigned BigInt and avoiding
     * negative numbers.
     * 
     * @param a
     *            source array
     * @param start
     *            first byte to be include
     * @param stop
     *            last byte to be include
     * @return extraction of source array
     */
    protected static byte[] extractUnsign(byte[] a, int start, int stop) {
	// TODO: some checks was useful

	byte[] res = new byte[stop - start + 2];

	res[0] = 0; // suppress negative numbers

	for (int i = 0; i < stop - start + 1; i++) {
	    res[i + 1] = a[start + i];
	}

	return res;
    }

    /**
     * Construct string representation of byte array. Without 0x-prefix and low-case letters.
     * 
     * @param bytes
     * @return byte array with ascii-values of string representation.
     */
    protected static byte[] hexify(byte[] bytes) {
	char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'a', 'b', 'c', 'd', 'e', 'f' };
	byte[] hexChars = new byte[bytes.length * 2];
	int v;

	for (int j = 0; j < bytes.length; j++) {
	    v = bytes[j] & 0xFF;
	    hexChars[j * 2] = (byte) hexArray[v >>> 4];
	    hexChars[j * 2 + 1] = (byte) hexArray[v & 0x0F];
	}
	return hexChars;
    }

    protected static void XOR(byte[] target, byte[] mask) {
	// assure same length
	assert (target.length == mask.length);

	for (int i = 0; i < target.length; i++) {
	    target[i] = (byte) (target[i] ^ mask[i]);
	}
    }
}
