/**
 * 
 */
package backend;

import backend.humanizer.IHumanizer;
import backend.seedgenerator.ISeedGenerator;

/**
 * @author p4553d
 * 
 */
public class PassGenerator {

    private ISeedGenerator m_seedGenerator = null;
    private IHumanizer m_humanizer = null;

    public PassGenerator(ISeedGenerator seedGen, IHumanizer hum) {
	m_seedGenerator = seedGen;
	m_humanizer = hum;
    }

    /**
     * Generate website specific password from master password and website name
     * 
     * @param master
     *            Master password
     * @param site
     *            Website name
     * @return String with password
     */
    public String generatePassword(String master, String site) {
	byte[] seed = m_seedGenerator.generateSeed(master, site);
	String password = m_humanizer.humanize(seed);

	return password;
    }

}
