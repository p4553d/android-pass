/**
 * 
 */
package backend.humanizer;


/**
 * @author p4553d Common interface to put pseudo random seed in appropriate and probably human readable form.
 */
public interface IHumanizer {
    /**
     * 
     * @param seed
     * @return
     */
    public String humanize(byte[] seed);
}
