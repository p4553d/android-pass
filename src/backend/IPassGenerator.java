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
	String generatePassword(String master, String site);
}
