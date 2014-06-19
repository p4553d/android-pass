package frontend.gui;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
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
	frame.setResizable(false);
	frame.setBounds(100, 100, 361, 154);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(
		new MigLayout("", "[grow][][grow]", "[][][][]"));

	passwordMaster = new JPasswordField();
	frame.getContentPane().add(passwordMaster, "cell 0 0,growx");

	passwordMasterRpt = new JPasswordField();
	frame.getContentPane().add(passwordMasterRpt, "cell 2 0,growx");

	textSiteName = new JTextField();
	textSiteName.setHorizontalAlignment(SwingConstants.CENTER);
	frame.getContentPane().add(textSiteName, "cell 0 1 3 1,growx");
	textSiteName.setColumns(10);

	textPassword = new JTextField();
	textPassword.setHorizontalAlignment(SwingConstants.CENTER);
	textPassword.setEditable(false);
	frame.getContentPane().add(textPassword, "cell 0 2 3 1,growx");
	textPassword.setColumns(10);

	btnGenerate = new JButton("generate");
	frame.getContentPane().add(btnGenerate, "cell 2 3,alignx trailing");
    }

    public void setVisible() {
	this.frame.setVisible(true);
    }

    public void setGenerateButtonListener(ActionListener l) {
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
