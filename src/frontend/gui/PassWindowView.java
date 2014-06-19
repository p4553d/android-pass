package frontend.gui;

import java.awt.EventQueue;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

/**
 * 
 * @author p4553d
 * 
 */
public class PassWindowView {

    // TODO: Refactor to meaningfull names
    // TODO: Check usability
    private JFrame frame;
    private JTextField textField_2;
    private JTextField textField_3;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;
    private JButton btnNewButton;

    /**
     * Launch the application.
     */
    // TODO: to remove
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    PassWindowView window = new PassWindowView();
		    window.frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

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

	passwordField = new JPasswordField();
	frame.getContentPane().add(passwordField, "cell 0 0,growx");

	passwordField_1 = new JPasswordField();
	frame.getContentPane().add(passwordField_1, "cell 2 0,growx");

	textField_2 = new JTextField();
	frame.getContentPane().add(textField_2, "cell 0 1 3 1,growx");
	textField_2.setColumns(10);

	textField_3 = new JTextField();
	textField_3.setEnabled(false);
	textField_3.setEditable(false);
	frame.getContentPane().add(textField_3, "cell 0 2 3 1,growx");
	textField_3.setColumns(10);

	btnNewButton = new JButton("generate");
	frame.getContentPane().add(btnNewButton, "cell 2 3,growx");
    }

    void setGenerateButtonListener(ActionListener l) {
	this.btnNewButton.addActionListener(l);
    }
}
