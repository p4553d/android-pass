/**
 * 
 */
package frontend.android;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.p4553d.youshallpass.R;

/**
 * @author p4553d
 * 
 */
public class YouShallPassActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.passview);
    }

    public void generate(View v) {
	EditText etPassword = (EditText) findViewById(R.id.editTextPassword);

	etPassword.setText("MUHAHA!");

    }

}
