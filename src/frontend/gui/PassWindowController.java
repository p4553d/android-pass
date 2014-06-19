package frontend.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	ActionListener al = new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		generate();
	    }
	};
	m_view.setGenerateButtonListener(al);

	// go visible
	m_view.setVisible();
    }

    public void generate() {

	boolean error = false; // not yet
	String output = "+ + +";

	// get input
	String master = m_view.getMaster();
	String masterRpt = m_view.getMasterRpt();

	String siteName = m_view.getSiteName();

	// check plausibility
	if (master.isEmpty() || siteName.isEmpty()) { // neither master, nor site should be empty
	    error = true;
	    output = "Provide both, master password and site name!";
	}

	if (!masterRpt.isEmpty() && !masterRpt.equals(master)) { // if master repeat provided, have to be equal
	    error = true;
	    output = "Master password and confirmation differ!";
	}

	// TODO: master length?

	// generate password
	if (!error) {
	    output = m_generator.generatePassword(master, siteName);
	}

	// output
	m_view.setPassword(output);

	// copy to buffer if success
	// TODO

	// blend out based on timer(?)
	// TODO
    }
}
