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

    private static byte[] concatenate(byte[] a, byte[] b) {
	int aLen = a.length;
	int bLen = b.length;

	byte[] res = new byte[aLen + bLen];

	System.arraycopy(a, 0, res, 0, aLen);
	System.arraycopy(b, 0, res, aLen, bLen);

	return res;
    }

    private static byte[] extract(byte[] a, int start, int stop) {
	// TODO: some checks was useful

	byte[] res = new byte[stop - start + 2];

	res[0] = 0; // suppress negative numbers

	for (int i = 0; i < stop - start + 1; i++) {
	    res[i + 1] = a[start + i];
	}

	return res;
    }

    private static byte[] hexify(byte[] bytes) {
	char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		'A', 'B', 'C', 'D', 'E', 'F' };
	byte[] hexChars = new byte[bytes.length * 2];
	int v;

	for (int j = 0; j < bytes.length; j++) {
	    v = bytes[j] & 0xFF;
	    hexChars[j * 2] = (byte) hexArray[v >>> 4];
	    hexChars[j * 2 + 1] = (byte) hexArray[v & 0x0F];
	}
	return hexChars;
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
	    current = hexify(md.digest(current));
	}

	current = concatenate(current, siteBytes);

	for (int i = 0; i < rounds; i++) {
	    current = hexify(md.digest(current));
	}
	current = md.digest(current);

	// produce seed (long) out of hash
	byte[] ext = extract(current, 11, 15); // TODO: get rid of magic numbers

	res = new BigInteger(ext);

	return res;
    }
}
