package stat.keeper;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferencesActivity extends PreferenceActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// add the preferences xml we created to this Activity
		this.addPreferencesFromResource(R.xml.preferences);
	}
}
