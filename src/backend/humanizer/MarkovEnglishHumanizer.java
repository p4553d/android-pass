package backend.humanizer;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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
public class MarkovEnglishHumanizer implements IHumanizer {

    private HashMap<String, Vector<String>> m_chain;
    private static String empty_prefix = "-";
    private static BigInteger number_generator = BigInteger.valueOf(1000);

    protected static String ressource = "./res/raw/english";

    public MarkovEnglishHumanizer() {
	m_chain = new HashMap<String, Vector<String>>();

	// read config and build mapping
	InputStream ressourceStream;
	ressourceStream = getClass().getClassLoader().getResourceAsStream(
		ressource);

	if (ressourceStream == null) { // do we running outside of jar?
	    try {
		ressourceStream = new FileInputStream(new java.io.File(
			ressource));
	    } catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	BufferedReader inStream = null;
	try {
	    inStream = new BufferedReader(
		    new InputStreamReader(ressourceStream));

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

    @Override
    public String humanize(byte[] seed) {
	String password = "";

	// convert byte[] to unsigned BigInteger
	byte[] ubyte = new byte[seed.length + 1];
	ubyte[0] = 0;
	for (int i = 1; i < ubyte.length; i++) {
	    ubyte[i] = seed[i - 1];
	}
	BigInteger biSeed = new BigInteger(ubyte);

	// produce password
	password = humanize(biSeed);
	return password;
    }
}
