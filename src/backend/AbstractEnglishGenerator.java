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

public abstract class AbstractEnglishGenerator implements IPassGenerator {

    private HashMap<String, Vector<String>> m_chain;

    public AbstractEnglishGenerator() {
	m_chain = new HashMap<String, Vector<String>>();

	// read config and build mapping
	File inFile = new java.io.File("./ressource/english"); // TODO
							       // configurable!
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
	    // TODO Auto-generated catch block
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
     * Abstract method to produce entropy as seed for human-readable password.
     * 
     * @param master
     *            Master key
     * @param site
     *            name for web site
     * @return byte array with raw output
     */
    public abstract byte[] generateHash(String master, String site);

    @Override
    public String generatePassword(String master, String site) {
	// TODO Auto-generated method stub
	return null;
    }

}
