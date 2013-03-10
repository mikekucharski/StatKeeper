package stat.keeper;

import android.app.Activity;
import android.content.Intent;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MySettingsActivity extends Activity{
	// used to create options menu
	@Override
	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		super.onCreateOptionsMenu(menu);
		
		// use inflater to set up menu using the menu xml file
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.settingsmenu, menu);

		return true;
	}

	// used when an options menu item was clicked
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.home:
				
				if( this.getClass().getSimpleName().equals("MainMenuActivity") ) {break;}
				Intent homeIntent = new Intent(this, MainMenuActivity.class);
				startActivity(homeIntent);
				break;
			case R.id.prefs:
				Intent prefIntent = new Intent("stat.keeper.PREFERENCESACTIVITY");
				startActivity(prefIntent);
				break;
			case R.id.aboutApp:
				// set up an activity the old way
				
				break;
			case R.id.exit:
				Intent intent = new Intent(this, MainMenuActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtra("finishApplication", true);
				startActivity(intent);
				break;
		}

		return false;
	}
}
