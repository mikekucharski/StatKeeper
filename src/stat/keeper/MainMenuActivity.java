package stat.keeper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainMenuActivity extends MySettingsActivity implements OnClickListener {

	private Button teams, totals, settings, about;
	private LinearLayout ll;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.mainmenu);
		
		if(getIntent().getBooleanExtra("finishApplication", false) )
			   finish();
		
		initVars();
	}

	private void initVars() {
		
		ll=(LinearLayout)findViewById(R.id.llHome);
		ll.setBackgroundResource(R.drawable.fenway);
		
		teams = (Button) this.findViewById(R.id.bTeams);
		totals = (Button) this.findViewById(R.id.bTotals);
		settings = (Button) this.findViewById(R.id.bSettings);
		about = (Button) this.findViewById(R.id.bAbout);
		
		teams.setOnClickListener(this);
		totals.setOnClickListener(this);
		settings.setOnClickListener(this);
		about.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bTeams:
			
			Intent ourIntent = new Intent(MainMenuActivity.this, TeamListActivity.class);
			startActivity(ourIntent);
			
			break;
		case R.id.bTotals:
			
			Intent totsIntent = new Intent(MainMenuActivity.this, TotalsTeamListActivity.class);
			startActivity(totsIntent);
			
			break;
		case R.id.bSettings:
			
			Intent prefIntent = new Intent(MainMenuActivity.this, PreferencesActivity.class);
			startActivity(prefIntent);
			
			break;
		case R.id.bAbout:
			
			AlertDialog.Builder dialog = new AlertDialog.Builder(MainMenuActivity.this);
			dialog.setTitle("About Stat Keeper");
			dialog.setMessage("This is some info about this app");
			
			dialog.setNeutralButton("OK", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {}
			});
			
			dialog.show();
			
			break;
			
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(getIntent().getBooleanExtra("finishApplication", false) )
			   finish();
	}
}
