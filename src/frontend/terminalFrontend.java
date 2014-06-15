package frontend;

import backend.IPassGenerator;
import backend.SHAEnglishGenerator;

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
	IPassGenerator gen = new SHAEnglishGenerator(5);
	System.out.print(gen.generatePassword("12345678901", "google.de")); // theitheuseeingerms-463
	System.out.println();
	System.out.print(gen.generatePassword("abcdefghijk", "google.de")); // spalay-nys-191
    }

}
