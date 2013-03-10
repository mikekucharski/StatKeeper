package stat.keeper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NewGameActivity extends MySettingsActivity implements OnClickListener{
	
	private EditText etOppTeam, etDate, etLocation, etYourScore, etOppScore;
	private Button bCancel, bCreate;
	private long tid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newgame);
		
		tid = this.getIntent().getExtras().getLong("team_id");
		
		etOppTeam = (EditText) this.findViewById(R.id.etOpTeam);
		etDate = (EditText) this.findViewById(R.id.etGameDate);
		etLocation = (EditText) this.findViewById(R.id.etLocation);
		etYourScore = (EditText) this.findViewById(R.id.etYourScore);
		etOppScore = (EditText) this.findViewById(R.id.etOpponentScore);
		
		
		bCancel = (Button) this.findViewById(R.id.bCancel);
		bCreate = (Button) this.findViewById(R.id.bCreate);
		
		bCancel.setOnClickListener(this);
		bCreate.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bCancel:
			this.finish();
			break;
		case R.id.bCreate:
			String opp = etOppTeam.getText().toString().trim();
			String loc = etDate.getText().toString().trim();
			String date = etLocation.getText().toString().trim();
			String yScore = etYourScore.getText().toString().trim();
			String oScore = etOppScore.getText().toString().trim();
			
			DBAdapter dba = new DBAdapter(NewGameActivity.this);
			dba.open();
			Game addGame = new Game(tid, opp, loc, date, Integer.parseInt(yScore), Integer.parseInt(oScore));
			dba.addGameEntry(addGame);
			dba.close();

			this.finish();
			break;
		}
	}

}
