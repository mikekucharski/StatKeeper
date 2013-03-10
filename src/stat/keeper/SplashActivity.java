package stat.keeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SplashActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// load the splash screen into the content view of activity
		this.setContentView(R.layout.splash);

		// create a new thread to run the splash screen for 5 second
		Thread timer = new Thread() {
			public void run() {
				try {
					sleep(2000); // sleeps for 5000 milliseconds or 5 seconds
					Class ourClass = Class.forName("stat.keeper.MainMenuActivity");
					Intent openStartingPoint = new Intent(SplashActivity.this, ourClass);
					startActivity(openStartingPoint);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		};
		timer.start();
	}

	// called when splash screen goes away,
	@Override
	protected void onPause() {
		super.onPause();

		// call this when activity is done,
		this.finish();
	}

}
