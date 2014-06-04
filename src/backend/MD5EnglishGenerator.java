package backend;

/**
 * Class which provide entropy with basic md5
 * 
 * @author p4553d
 * 
 */
public class MD5EnglishGenerator extends AbstractEnglishGenerator implements
	IPassGenerator {

    @Override
    protected long generateHash(String master, String site) {
	// TODO Auto-generated method stub
	return 1231235000L;
    }

}
