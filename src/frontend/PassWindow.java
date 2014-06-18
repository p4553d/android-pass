package frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;

public class PassWindow {

    private JFrame frame;
    private JTextField textField_2;
    private JTextField textField_3;
    private JLabel lblNewLabel;
    private JPasswordField passwordField;
    private JPasswordField passwordField_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    public void run() {
		try {
		    PassWindow window = new PassWindow();
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
    public PassWindow() {
	initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
	frame = new JFrame();
	frame.setResizable(false);
	frame.setBounds(100, 100, 361, 112);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(
		new MigLayout("", "[grow][][grow]", "[][][]"));

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
    }
}
