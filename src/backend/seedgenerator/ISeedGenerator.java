/**
 * 
 */
package backend.seedgenerator;

/**
 * @author p4553d
 * 
 *         Interface for all seed generators.
 * 
 */
public interface ISeedGenerator {
    /**
     * Generate website specific seed from master password and website name
     * 
     * @param master
     * @param site
     * @return pseudo random seed in raw form
     */
    public byte[] generateSeed(String master, String site);
}
