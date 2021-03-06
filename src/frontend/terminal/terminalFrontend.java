package frontend.terminal;

import backend.PassGenerator;
import backend.humanizer.IHumanizer;
import backend.humanizer.MarkovEnglishHumanizer;
import backend.seedgenerator.ISeedGenerator;
import backend.seedgenerator.SHASeedGenerator;

/**
 * TODO: Basic terminal frontend
 * 
 * @author p4553d
 * 
 */
public class terminalFrontend {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

	ISeedGenerator sg = new SHASeedGenerator(5);
	IHumanizer h = new MarkovEnglishHumanizer();

	PassGenerator gen = new PassGenerator(sg, h);
	System.out.print(gen.generatePassword("12345678901", "google.de")); // theitheuseeingerms-463
	System.out.println();
	System.out.print(gen.generatePassword("abcdefghijk", "google.de")); // spalay-nys-191
    }
}
