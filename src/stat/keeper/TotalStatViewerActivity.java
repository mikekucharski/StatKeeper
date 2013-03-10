package stat.keeper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TotalStatViewerActivity extends MySettingsActivity implements OnClickListener{
	private TextView title, HCount, ABCount, PACount, BBCount, OneBCount, TwoBCount, ThreeBCount,
	 HRCount, RBICount, RUNSCount, KCount, HBPCount, ROECount, SACfCount, FCCount, ECount,
	 CSCount, SBACount, calcAVG, calcOBP, calcCSPerc;
	private Button Edit, Back, Save;
	
	private DBAdapter dba;
	private long TID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		
		TID = this.getIntent().getExtras().getLong("team_id");
		dba = new DBAdapter(TotalStatViewerActivity.this);
		initializeRefs();
		
		Log.i("emulator", "TID in statviewer: " + TID);
		dba.open();
		String teamName = dba.getTeam(TID).getTeamName();
		title.setText(teamName + " Totals");
		dba.close();

	}

	private void initializeRefs() {
		title = (TextView) findViewById(R.id.tvTitle);
		
		Edit = (Button) findViewById(R.id.bEdit);
		Edit.setVisibility(View.GONE);
		Save = (Button) findViewById(R.id.bSave);
		Save.setVisibility(View.GONE);
		Back = (Button) findViewById(R.id.bBack);
		Back.setOnClickListener(this);
		
		HCount = (TextView) findViewById(R.id.tvHCount);
		ABCount = (TextView) findViewById(R.id.tvABCount);
		BBCount = (TextView) findViewById(R.id.tvBBCount);
		HBPCount = (TextView) findViewById(R.id.tvHBPCount);
		SACfCount = (TextView) findViewById(R.id.tvSACfCount);
		PACount = (TextView) findViewById(R.id.tvPACount);
		KCount = (TextView) findViewById(R.id.tvKCount);
		OneBCount = (TextView) findViewById(R.id.tv1BCount);
		TwoBCount = (TextView) findViewById(R.id.tv2BCount);
		ThreeBCount = (TextView) findViewById(R.id.tv3BCount);
		HRCount = (TextView)findViewById(R.id.tvHRCount);
		RUNSCount = (TextView) findViewById(R.id.tvRCount);
		RBICount = (TextView)findViewById(R.id.tvRBICount);
		ROECount = (TextView) findViewById(R.id.tvROECount);
		FCCount = (TextView) findViewById(R.id.tvFCCount);
		ECount = (TextView) findViewById(R.id.tvECount);
		CSCount = (TextView) findViewById(R.id.tvCSCount);
		SBACount = (TextView) findViewById(R.id.tvSBACount);
		calcCSPerc = (TextView) this.findViewById(R.id.tvCSPVal);
		
		dba.open();
		HCount.setText("" + dba.getAllHits(TID));
		dba.close();
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bBack:
				this.finish();
				break;
		}
	}
}
