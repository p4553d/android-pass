/**
 * 
 */
package backend.seedgenerator;

/**
 * @author p4553d
 * 
 */
public abstract class AbstractSeedGenerator implements ISeedGenerator {

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

    /**
     * Clip a part of byte array.
     * 
     * @param a
     * @param start
     * @param stop
     * @return
     */
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
