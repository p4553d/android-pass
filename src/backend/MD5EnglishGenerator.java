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

    // numbers of rounds, used for "harden" md5
    private static int rounds = 10;

    // Bytes from which a entropy is extracted
    private static int firstByte = 3;
    private static int lastByte = 7;

    /**
     * Concatenate two byte arrays
     * 
     * @param a
     *            first array
     * @param b
     *            second array
     * @return resulting array containing values of array a followed by values of b
     */
    private static byte[] concatenate(byte[] a, byte[] b) {
	int aLen = a.length;
	int bLen = b.length;

	byte[] res = new byte[aLen + bLen];

	System.arraycopy(a, 0, res, 0, aLen);
	System.arraycopy(b, 0, res, aLen, bLen);

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
    private static byte[] extract(byte[] a, int start, int stop) {
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
    private static byte[] hexify(byte[] bytes) {
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
	// compute hash in hex form
	for (int i = 0; i < rounds; i++) {
	    current = hexify(md.digest(current));
	}

	current = concatenate(current, siteBytes);

	for (int i = 0; i < rounds; i++) {
	    current = hexify(md.digest(current));
	}
	// compute hash raw
	current = md.digest(current);

	// produce seed (long) out of hash
	byte[] ext = extract(current, firstByte, lastByte);
	res = new BigInteger(ext);

	return res;
    }
}
