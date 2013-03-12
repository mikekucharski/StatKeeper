package stat.keeper;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewTeamActivity extends MySettingsActivity implements OnClickListener{
	
	private EditText etTeamName, etSeasonName, etYear;
	private Button cancel, saveTeam;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newteam);
		
		etTeamName = (EditText) this.findViewById(R.id.etTeam_Name);
		etSeasonName = (EditText) this.findViewById(R.id.etSeason_Name);
		etYear = (EditText) this.findViewById(R.id.etYear);
		
		cancel = (Button) this.findViewById(R.id.bCancel);
		saveTeam = (Button) this.findViewById(R.id.bSaveTeam);
		
		cancel.setOnClickListener(this);
		saveTeam.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bCancel:
			this.finish();
			break;
		case R.id.bSaveTeam:
			String teamName = etTeamName.getText().toString().trim();
			String seasonName = etSeasonName.getText().toString().trim();
			String yearString = etYear.getText().toString().trim();
			int year = Integer.parseInt(yearString);
			
			DBAdapter dba = new DBAdapter(NewTeamActivity.this);
			dba.open();
			Team addTeam = new Team(teamName, seasonName, year);
			dba.addTeamEntry(addTeam);
			dba.close();
			
			Toast.makeText(NewTeamActivity.this, teamName + " Team Created", Toast.LENGTH_LONG).show();
			
			this.finish();
			break;
		}
	}
}
