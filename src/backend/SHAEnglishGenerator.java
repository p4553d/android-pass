/**
 * 
 */
package backend;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author p4553d
 * 
 */
public class SHAEnglishGenerator extends AbstractEnglishGenerator implements
	IPassGenerator {

    // how much entropy should pass have
    private int entropyByteLength = 5;

    // hash description
    private String hashDesc = "SHA-512";
    private int hashLength = 64;

    public SHAEnglishGenerator(int entropy) {
	// TODO: check to stay under max and above meaningful min
	this.entropyByteLength = entropy;
    }

    /*
     * (non-Javadoc)
     * 
     * @see backend.AbstractEnglishGenerator#generateHash(java.lang.String, java.lang.String)
     */
    @Override
    protected BigInteger generateHash(String master, String site) {
	BigInteger res;
	// prepare input for hashing
	byte[] masterBytes = master.getBytes();
	byte[] siteBytes = site.getBytes();
	byte[] cleartext = concatenate(masterBytes, siteBytes);

	// Build up has engine
	MessageDigest md = null;
	try {
	    md = MessageDigest.getInstance(hashDesc);
	} catch (NoSuchAlgorithmException e) {
	    e.printStackTrace();
	}

	// compute hash-value
	byte[] hash = md.digest(cleartext);

	// fold hash-value to target entropy
	int numFoldings = hashLength / entropyByteLength;
	byte[] entropy = new byte[entropyByteLength];

	for (int i = 0; i < numFoldings; i++) {
	    byte[] mask = extract(hash, entropyByteLength * i,
		    entropyByteLength * (i + 1) - 1);
	    XOR(entropy, mask);
	}

	// produce seed
	res = new BigInteger(entropy);

	return res;
    }
}
