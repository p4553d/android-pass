/**
 * 
 */
package backend;

/**
 * @author p4553d
 * 
 *         Common interface for all password generators.
 */
public interface IPassGenerator {
    /**
     * Generate website specific password from master password and website name
     * 
     * @param master
     *            Master password
     * @param site
     *            Website name
     * @return String with password
     */
    String generatePassword(String master, String site);
}
