/**
 * 
 */
package frontend.android;

import java.io.InputStream;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.widget.EditText;
import backend.PassGenerator;
import backend.humanizer.IHumanizer;
import backend.humanizer.MarkovEnglishHumanizer;
import backend.seedgenerator.ISeedGenerator;
import backend.seedgenerator.SHASeedGenerator;

import com.p4553d.youshallpass.R;

/**
 * @author p4553d
 * 
 */
public class YouShallPassActivity extends Activity {
    private PassGenerator m_generator;

    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);

	InputStream inputStream = getResources().openRawResource(R.raw.english);

	IHumanizer h = new MarkovEnglishHumanizer(inputStream);
	ISeedGenerator sg = null;
	try {
	    sg = new SHASeedGenerator(5);
	    m_generator = new PassGenerator(sg, h);
	} catch (Exception e) {
	    e.printStackTrace();
	    finish();
	}

	setContentView(R.layout.passview);
    }

    public void generate(View v) {
	generatePassword(false);
    }

    public void generateAndCopy(View v) {
	generatePassword(true);
    }

    private String getMaster() {
	EditText etmp = (EditText) findViewById(R.id.editTextMasterPassword);
	return etmp.getText().toString();
    }

    private String getMasterRpt() {
	EditText etmp = (EditText) findViewById(R.id.editTextMasterPasswordRepeat);
	return etmp.getText().toString();
    }

    private String getSiteName() {
	EditText etmp = (EditText) findViewById(R.id.editTextSiteName);
	return etmp.getText().toString();
    }

    private void setPassword(String value) {
	EditText etmp = (EditText) findViewById(R.id.editTextPassword);
	etmp.setText(value);
    }

    private void generatePassword(boolean copyToClipboard) {

	boolean error = false; // not yet
	String output = "+ + +";

	// get input
	String master = getMaster();
	String masterRpt = getMasterRpt();

	String siteName = getSiteName();

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

	    if (copyToClipboard) {
		ClipboardManager cm = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
		cm.setText(output);
	    }
	}

	// output
	setPassword(output);
    }
}
