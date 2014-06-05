package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class which provide entropy with basic md5
 * 
 * @author p4553d
 * 
 */
public class MD5EnglishGenerator extends AbstractEnglishGenerator implements
	IPassGenerator {

    private static int rounds = 10;

    private byte[] concatenate(byte[] a, byte[] b) {
	int aLen = a.length;
	int bLen = b.length;

	byte[] res = new byte[aLen + bLen];

	System.arraycopy(a, 0, res, 0, aLen);
	System.arraycopy(a, 0, res, aLen, bLen);

	return res;
    }

    private byte[] extract(byte[] a, int start, int stop) {
	// TODO: some checks was useful

	byte[] res = new byte[stop - start + 2];
	for (int i = start; i < stop; i++) {
	    res[i + 1] = a[start + i];
	}
	res[0] = 0; // suppress negative numbers

	return res;

    }

    @Override
    /**
     * Implementation of mdpass method to generate random
     */
    protected BigInteger generateHash(String master, String site) {
	BigInteger res;
	byte[] masterBytes = master.getBytes();
	byte[] siteBytes = site.getBytes();

	byte[] current = masterBytes.clone();

	MessageDigest md = null;
	try {
	    md = MessageDigest.getInstance("MD5");
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}
	// hash master password and site in multiple rounds
	for (int i = 0; i < rounds; i++) {
	    current = md.digest(current);
	}
	current = concatenate(current, siteBytes);
	for (int i = 0; i < rounds; i++) {
	    current = md.digest(current);
	}

	// produce seed (long) out of hash
	byte[] ext = extract(current, 0, 7);
	res = new BigInteger(ext);

	// mask some of them
	// long mask = (0xffffffffffffffffL) >> 24;
	// res &= mask;

	return res;
    }
}
