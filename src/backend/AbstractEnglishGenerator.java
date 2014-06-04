package backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
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
    private static int number_generator = 1000;

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
    private String humanize(long seed) {
	String result = "";
	String current = empty_prefix;

	while (seed > 0) {
	    Vector<String> next_list = m_chain.get(current);

	    // defensive against dead-ends
	    if (next_list == null) {
		current = empty_prefix;
		continue;
	    }

	    // generate some numbers
	    if (seed < number_generator) {
		int number = (int) (seed % number_generator);
		seed = seed / number_generator; // TODO: to refactor, can be execute only once
		result = result + "-" + number;
		continue;
	    }

	    int next_length = next_list.size();

	    int index = (int) (seed % next_length); // determine index of next syllable
	    seed = seed / next_length; // reduce number

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
    protected abstract long generateHash(String master, String site);

    @Override
    public String generatePassword(String master, String site) {
	String password = null;
	long seed = generateHash(master, site);
	password = humanize(seed);
	return password;
    }
}
