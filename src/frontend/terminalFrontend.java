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
	System.out.print(gen.generatePassword("12345678901", "google.de")); // theitheuseeingerms-463
	System.out.println();
	System.out.print(gen.generatePassword("abcdefghijk", "google.de")); // spalay-nys-191
    }

}
