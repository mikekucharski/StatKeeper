package stat.keeper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditTeamActivity extends MySettingsActivity implements OnClickListener{
	
	private EditText etTeamName, etSeasonName, etYear;
	private Button cancel, updateTeam;
	private String oldTeamName;
	private DBAdapter dba;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newteam);
		
		oldTeamName = this.getIntent().getExtras().getString("team_name");
	
		etTeamName = (EditText) this.findViewById(R.id.etTeam_Name);
		etSeasonName = (EditText) this.findViewById(R.id.etSeason_Name);
		etYear = (EditText) this.findViewById(R.id.etYear);
		cancel = (Button) this.findViewById(R.id.bCancel);
		updateTeam = (Button) this.findViewById(R.id.bSaveTeam);
		updateTeam.setText("Update");
		
		cancel.setOnClickListener(this);
		updateTeam.setOnClickListener(this);
		
		// pull info
		dba = new DBAdapter(EditTeamActivity.this);
		dba.open();
		// GET DATA!?
		Team team = dba.getTeam( dba.getTID(oldTeamName) );
		dba.close();
		
		etTeamName.setText( team.getTeamName() );
		etSeasonName.setText( team.getSeasonName() );
		etYear.setText( "" + team.getYear() );

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bCancel:
				this.finish();
				break;
			case R.id.bSaveTeam:
				// create a bundle of data
				String teamName = etTeamName.getText().toString().trim();
				String seasonName = etSeasonName.getText().toString().trim();
				String yearString = etYear.getText().toString().trim();
				int year = Integer.parseInt(yearString);
				
				// update info
				dba.open();
				Log.i("emulator", "Updating ID: " + dba.getTID(oldTeamName));
				Team newTeam = new Team(teamName, seasonName, year);
				dba.updateTeam(dba.getTID(oldTeamName), newTeam);
				dba.close();
				
				this.finish();
				break;
		}
	}
}
