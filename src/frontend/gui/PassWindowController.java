package frontend.gui;

import backend.PassGenerator;
import backend.humanizer.IHumanizer;
import backend.humanizer.MarkovEnglishHumanizer;
import backend.seedgenerator.ISeedGenerator;
import backend.seedgenerator.SHASeedGenerator;

/**
 * 
 * @author p4553d
 * 
 */
public class PassWindowController {
    private PassWindowView m_view;
    private PassGenerator m_generator;

    public PassWindowController() throws Exception {
	// create password generator
	// TODO: to make it configurable
	IHumanizer h = new MarkovEnglishHumanizer();
	ISeedGenerator sg = new SHASeedGenerator(5);
	m_generator = new PassGenerator(sg, h);

	// create gui
	m_view = new PassWindowView();

	// register listener

	// go visible
    }
}
