package stat.keeper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class EditGameActivity extends MySettingsActivity implements OnClickListener{

	private EditText etOppTeam, etDate, etLocation, etYourScore, etOppScore;
	private Button bCancel, bUpdate;
	private long GID;
	private DBAdapter dba;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.newgame);
		
		GID = this.getIntent().getExtras().getLong("game_ID");
		
		etOppTeam = (EditText) this.findViewById(R.id.etOpTeam);
		etDate = (EditText) this.findViewById(R.id.etGameDate);
		etLocation = (EditText) this.findViewById(R.id.etLocation);
		etYourScore = (EditText) this.findViewById(R.id.etYourScore);
		etOppScore = (EditText) this.findViewById(R.id.etOpponentScore);
		
		
		bCancel = (Button) this.findViewById(R.id.bCancel);
		bUpdate = (Button) this.findViewById(R.id.bCreate);
		bUpdate.setText("Update");
		bCancel.setOnClickListener(this);
		bUpdate.setOnClickListener(this);
		
		dba = new DBAdapter(EditGameActivity.this);
		dba.open();
		Log.i("emulator", "Trying to get game GID: " + GID);
		Game game = dba.getGame(GID);
		dba.close();
		
		etOppTeam.setText(game.getOpponent());
		etDate.setText(game.getGameDate());
		etLocation.setText(game.getLocation());
		etYourScore.setText("" + game.getYourScore());
		etOppScore.setText("" + game.getOppScore());
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bCancel:
				this.finish();
				break;
			case R.id.bCreate:
				
				String oppName = etOppTeam.getText().toString().trim();
				String loc = etLocation.getText().toString().trim();
				String date = etDate.getText().toString().trim();
				String yScore = etYourScore.getText().toString().trim();
				String oScore = etOppScore.getText().toString().trim();
				int yScoreVal = Integer.parseInt(yScore);
				int oScoreVal = Integer.parseInt(oScore);
				
				dba.open();
				Game newGame = new Game(dba.getGame(GID).getTeamID() ,oppName, loc, date, yScoreVal, oScoreVal);
				dba.updateGame(GID, newGame);
				dba.close();
				
				this.finish();
				break;
		}
	}
	

}
