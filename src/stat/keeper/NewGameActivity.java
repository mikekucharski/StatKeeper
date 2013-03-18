package stat.keeper;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
		etDate.setOnClickListener(this);
		
		
		
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
			String loc = etLocation.getText().toString().trim();
			String date = etDate.getText().toString().trim();
			String yScore = etYourScore.getText().toString().trim();
			String oScore = etOppScore.getText().toString().trim();
			
			DBAdapter dba = new DBAdapter(NewGameActivity.this);
			dba.open();
			Game addGame = new Game(tid, opp, loc, date, Integer.parseInt(yScore), Integer.parseInt(oScore));
			dba.addGameEntry(addGame);
			dba.close();

			Toast.makeText(NewGameActivity.this, opp + " Game Created", Toast.LENGTH_LONG).show();
			
			this.finish();
			break;
		case R.id.etGameDate:
			 int year, month, day;
			 String dateText = ((TextView) v).getText().toString().trim();
			 
			 if( dateText.length() == 0 ){
				 Calendar now = Calendar.getInstance();
				 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd"); 
				 String nowDate = formatter.format(now.getTime());
				 String[] separateCurrentDate = nowDate.split("-");
				 String sYear = separateCurrentDate[0];
				 String sMonth = separateCurrentDate[1];
				 String sDay = separateCurrentDate[2];
				 year = Integer.parseInt(sYear);
				 month = Integer.parseInt(sMonth);
				 day = Integer.parseInt(sDay);
			 }else{
	             String temp[] = dateText.split("/");
	             month = Integer.valueOf(temp[0]);
	             day = Integer.valueOf(temp[1]);
	             year= Integer.valueOf(temp[2]);
	             Log.i("emulator", "DATEGOT: " + "YR: " + year + " MON: " + month + " DAY: " + day);
			 }
			 
             DatePickerDialog dtpkrdlg = new DatePickerDialog(NewGameActivity.this, new OnDateSetListener(){

					@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						etDate.setText(month+1 + "/" + day + "/" + year);
					}
            	 
             } , year, month-1, day);
             dtpkrdlg.setMessage("Enter Game Date");
             dtpkrdlg.show(); 
			break;
		}
	}

}
