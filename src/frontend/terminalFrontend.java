package frontend;

import backend.MD5EnglishGenerator;

/**
 * Basic terminal frontend
 * 
 * @author p4553d
 * 
 */
public class terminalFrontend {

    /**
     * @param args
     */
    public static void main(String[] args) {
	MD5EnglishGenerator gen = new MD5EnglishGenerator();
	System.out.print(gen.generatePassword("", ""));
    }

}
