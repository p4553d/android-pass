package frontend.gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author p4553d
 * 
 */
public class PassWindowView {

    // TODO: Check usability
    private JFrame frame;
    private JTextField textSiteName;
    private JTextField textPassword;
    private JPasswordField passwordMaster;
    private JPasswordField passwordMasterRpt;
    private JButton btnGenerate;
    private JLabel lblMaster;
    private JLabel lblMasterRpt;
    private JLabel lblSiteName;
    private JLabel lblNGenerated;

    /**
     * Create the application.
     */
    public PassWindowView() {
	initialize(); // visible part of GUI
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frame = new JFrame();
	frame.setBounds(100, 100, 361, 235);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(
		new MigLayout("", "[grow][][grow]", "[][][][][][][][]"));

	lblMaster = new JLabel("Master password");
	frame.getContentPane().add(lblMaster, "cell 0 1");

	lblMasterRpt = new JLabel("Repeat (optional)");
	frame.getContentPane().add(lblMasterRpt, "cell 2 1");

	passwordMaster = new JPasswordField();
	passwordMaster
		.setToolTipText("Master password to keep your secrets. Chose fair length of 10 and above.");
	frame.getContentPane().add(passwordMaster, "cell 0 2,growx");

	passwordMasterRpt = new JPasswordField();
	passwordMasterRpt
		.setToolTipText("Optional - Use thist one if you create password for first time!");
	frame.getContentPane().add(passwordMasterRpt, "cell 2 2,growx");

	lblSiteName = new JLabel("Site name");
	frame.getContentPane().add(lblSiteName, "cell 0 3 3 1");

	textSiteName = new JTextField();
	textSiteName.setHorizontalAlignment(SwingConstants.CENTER);
	textSiteName.setToolTipText("Name of site or target");
	frame.getContentPane().add(textSiteName, "cell 0 4 3 1,growx");
	textSiteName.setColumns(10);

	lblNGenerated = new JLabel("Generated password");
	frame.getContentPane().add(lblNGenerated, "cell 0 5 3 1");

	textPassword = new JTextField();
	textPassword.setHorizontalAlignment(SwingConstants.CENTER);
	textPassword.setToolTipText("Generated password for site");
	textPassword.setEditable(false);
	frame.getContentPane().add(textPassword, "cell 0 6 3 1,growx");
	textPassword.setColumns(10);

	btnGenerate = new JButton(" generate & copy");
	frame.getContentPane().add(btnGenerate, "cell 2 7,alignx trailing");

	frame.getRootPane().setDefaultButton(btnGenerate);

    }

    public void setVisible() {
	this.frame.setVisible(true);
    }

    public void setGenerateButtonListener(ActionListener l) {
	// action listener for button
	this.btnGenerate.addActionListener(l);

    }

    public String getMaster() {
	return new String(passwordMaster.getPassword());
    }

    public String getMasterRpt() {
	return new String(passwordMasterRpt.getPassword());
    }

    public String getSiteName() {
	return textSiteName.getText();
    }

    public void setPassword(String text) {
	textPassword.setText(text);
    }
}
