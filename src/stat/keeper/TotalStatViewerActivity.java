package stat.keeper;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class TotalStatViewerActivity extends MySettingsActivity implements OnClickListener{
	
	private TextView title, wlt, HCount, ABCount, PACount, BBCount, OneBCount, TwoBCount, ThreeBCount,
	 HRCount, RBICount, SBCount, RUNSCount, KCount, HBPCount, ROECount, SACfCount, FCCount, ECount,
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
		getTotalsFromDB();
		
		dba.open();
		String teamName = dba.getTeam(TID).getTeamName();
		title.setText(teamName + " Totals");
		wlt.setText(dba.getWLT(TID));
		dba.close();

	}

	private void initializeRefs() {
		title = (TextView) findViewById(R.id.tvTitle);
		wlt = (TextView) findViewById(R.id.tvTOTWLT);
		wlt.setVisibility(View.VISIBLE);
		
		Edit = (Button) findViewById(R.id.bEdit);
		Edit.setVisibility(View.GONE);
		Save = (Button) findViewById(R.id.bSave);
		Save.setVisibility(View.GONE);
		Back = (Button) findViewById(R.id.bBack);
		Back.setOnClickListener(this);
		Back.setText("Back To Teams");
		
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
		SBCount = (TextView)findViewById(R.id.tvSBCount);
		ROECount = (TextView) findViewById(R.id.tvROECount);
		FCCount = (TextView) findViewById(R.id.tvFCCount);
		ECount = (TextView) findViewById(R.id.tvECount);
		CSCount = (TextView) findViewById(R.id.tvCSCount);
		SBACount = (TextView) findViewById(R.id.tvSBACount);
		calcAVG = (TextView) this.findViewById(R.id.tvAVGVal);
		calcOBP = (TextView) this.findViewById(R.id.tvOBPVal);
		calcCSPerc = (TextView) this.findViewById(R.id.tvCSPVal);
	}
	
	private void getTotalsFromDB() {
		
		// store these values to calculate AVG, OBP, CSP
		int totH, totAB, totBB, totHBP, totSACf, totCS, totSBA; 
		
		dba.open();
		totH = dba.getTotalStat(TID, "H");
		totAB = dba.getTotalStat(TID, "AB");
		totBB = dba.getTotalStat(TID, "BB");
		totHBP = dba.getTotalStat(TID, "HBP");
		totSACf = dba.getTotalStat(TID, "SACf");
		PACount.setText("" + dba.getTotalStat(TID, "PA"));
		KCount.setText("" + dba.getTotalStat(TID, "K"));
		OneBCount.setText("" + dba.getTotalStat(TID, "OneB"));
		TwoBCount.setText("" + dba.getTotalStat(TID, "TwoB"));
		ThreeBCount.setText("" + dba.getTotalStat(TID, "ThreeB"));
		HRCount.setText("" + dba.getTotalStat(TID, "HR"));
		RUNSCount.setText("" + dba.getTotalStat(TID, "R"));
		RBICount.setText("" + dba.getTotalStat(TID, "RBI"));
		SBCount.setText("" + dba.getTotalStat(TID, "SB"));
		ROECount.setText("" + dba.getTotalStat(TID, "ROE"));
		FCCount.setText("" + dba.getTotalStat(TID, "FC"));
		ECount.setText("" + dba.getTotalStat(TID, "E"));
		totCS = dba.getTotalStat(TID, "CS");
		totSBA = dba.getTotalStat(TID, "SBA");
		dba.close();
		
		HCount.setText("" + totH);
		ABCount.setText("" + totAB);
		BBCount.setText("" + totBB);
		HBPCount.setText("" + totHBP);
		SACfCount.setText("" + totSACf);
		CSCount.setText("" + totCS);
		SBACount.setText("" + totSBA);
		
		double totAVG = (totH * 1.0) / totAB;
		double totOBP = ( (totH + totBB + totHBP) * 1.0 ) 
							/ (totAB + totBB + totHBP + totSACf);
		double totCSP = (totCS * 1.0) / (totSBA) * 100.0;
		
		totAVG = (totAB == 0) ? 0 : totAVG;
		totOBP = ( (totAB + totBB + totHBP + totSACf) == 0) ? 0 : totOBP;
		totCSP = (totSBA == 0) ? 0 : totCSP;
	
		calcAVG.setText(String.format("%.3f", totAVG));
		calcOBP.setText(String.format("%.3f", totOBP));
		calcCSPerc.setText(String.format("%.1f", totCSP));
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
