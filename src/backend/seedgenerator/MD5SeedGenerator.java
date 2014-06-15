package backend.seedgenerator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Class which provide entropy with basic md5
 * 
 * @author p4553d
 * 
 */
public class MD5SeedGenerator extends AbstractSeedGenerator implements
	ISeedGenerator {

    // numbers of rounds, used for "harden" md5
    private static int rounds = 10;

    // Bytes from which a entropy is extracted
    private static int firstByte = 3;
    private static int lastByte = 7;

    @Override
    public byte[] generateSeed(String master, String site) {
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
	return ext;
    }
}
