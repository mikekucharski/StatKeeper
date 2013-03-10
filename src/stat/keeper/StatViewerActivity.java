package stat.keeper;


import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StatViewerActivity extends MySettingsActivity implements OnClickListener{
	
	private ArrayList<Stat> stats = new ArrayList<Stat>();
	
	private Stat H, AB, BB, HBP, SACf, PA, K, ONEB, TWOB, THREEB, HR, RUNS, RBI, ROE, FC, E, CS, SBA;
	
	private TextView title, HCount, ABCount, PACount, BBCount, OneBCount, TwoBCount, ThreeBCount,
	 		 HRCount, RBICount, RUNSCount, KCount, HBPCount, ROECount, SACfCount, FCCount, ECount,
	 		 CSCount, SBACount, calcAVG, calcOBP, calcCSPerc;
	
	private Button HAdd, HSub, ABAdd, ABSub,  PAAdd,  PASub,  BBAdd,  BBSub, OneBAdd, OneBSub, 
		   TwoBAdd, TwoBSub, ThreeBAdd, ThreeBSub, HRAdd, HRSub, RBIAdd, RBISub,
	       RUNSAdd, RUNSSub, KAdd, KSub, HBPAdd, HBPSub, ROEAdd, ROESub, SACfAdd, SACfSub,
	       FCAdd, FCSub, EAdd, ESub, CSAdd, CSSub, SBAAdd, SBASub, Edit, Back, Save;
	
	private DBAdapter dba;
	private long GID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stats);
		
		GID = this.getIntent().getExtras().getLong("game_ID");
		dba = new DBAdapter(StatViewerActivity.this);
		initializeRefs();
		
		dba.open();
		String oppName = dba.getGame(GID).getOpponent();
		title.setText("" + oppName + " Stats");
		dba.close();
		
		pullStatsFromDB();
	}



	private void pullStatsFromDB() {
		dba.open();
		Game game = dba.getGame(GID);
		dba.close();
		
		HCount.setText("" + game.getstatH());
		ABCount.setText("" + game.getstatAB());
		BBCount.setText("" + game.getstatBB());
		HBPCount.setText("" + game.getstatHBP());
		SACfCount.setText("" + game.getstatSACf());
		PACount.setText("" + game.getstatPA());
		KCount.setText("" + game.getstatK());
		OneBCount.setText("" + game.getstat1B());
		TwoBCount.setText("" + game.getstat2B());
		ThreeBCount.setText("" + game.getstat3B());
		HRCount.setText("" + game.getstatHR());
		RUNSCount.setText("" + game.getstatR());
		RBICount.setText("" + game.getstatRBI());
		ROECount.setText("" + game.getstatROE());
		FCCount.setText("" + game.getstatFC());
		ECount.setText("" + game.getstatE());
		CSCount.setText("" + game.getstatCS());
		SBACount.setText("" + game.getstatSBA());
		
		double AVGVal = (game.getstatH() * 1.0) / game.getstatAB();
		double OBPVal = ( (game.getstatH() + game.getstatBB() + game.getstatHBP()) * 1.0 ) 
							/ (game.getstatAB() + game.getstatBB() + game.getstatHBP() + game.getstatSACf());
		double CSPercent = (game.getstatCS() * 1.0) / (game.getstatSBA()) * 100.0;
		
		calcAVG.setText(String.format("%.3f", AVGVal));
		calcOBP.setText(String.format("%.3f", OBPVal));
		calcCSPerc.setText(String.format("%.1f", CSPercent));
	}



	private void initializeRefs() {
		title = (TextView) findViewById(R.id.tvTitle);
		
		Edit = (Button) findViewById(R.id.bEdit);
		Edit.setOnClickListener(this);
		Back = (Button) findViewById(R.id.bBack);
		Back.setOnClickListener(this);
		Save = (Button) findViewById(R.id.bSave);
		Save.setOnClickListener(this);
		
		HAdd = (Button) findViewById(R.id.bADDH);
		HSub = (Button) findViewById(R.id.bSUBH);
		HCount = (TextView) findViewById(R.id.tvHCount);
		H = new Stat(HAdd, HSub, HCount);
		stats.add(H);
		
		ABAdd = (Button) findViewById(R.id.bADDAB);
		ABSub = (Button) findViewById(R.id.bSUBAB);
		ABCount = (TextView) findViewById(R.id.tvABCount);
		AB = new Stat(ABAdd, ABSub, ABCount);
		stats.add(AB);
		
		BBAdd = (Button) findViewById(R.id.bADDBB);
		BBSub = (Button) findViewById(R.id.bSUBBB);
		BBCount = (TextView) findViewById(R.id.tvBBCount);
		BB = new Stat(BBAdd, BBSub, BBCount);
		stats.add(BB);
		
		HBPAdd = (Button) findViewById(R.id.bADDHBP);
		HBPSub = (Button) findViewById(R.id.bSUBHBP);
		HBPCount = (TextView) findViewById(R.id.tvHBPCount);
		HBP = new Stat(HBPAdd, HBPSub, HBPCount);
		stats.add(HBP);
		
		SACfAdd = (Button) findViewById(R.id.bADDSACf);
		SACfSub = (Button) findViewById(R.id.bSUBSACf);
		SACfCount = (TextView) findViewById(R.id.tvSACfCount);
		SACf = new Stat(SACfAdd, SACfSub, SACfCount);
		stats.add(SACf);
		
		PAAdd = (Button) findViewById(R.id.bADDPA);
		PASub = (Button) findViewById(R.id.bSUBPA);
		PACount = (TextView) findViewById(R.id.tvPACount);
		PA = new Stat(PAAdd, PASub, PACount);
		stats.add(PA);
		
		KAdd = (Button) findViewById(R.id.bADDK);
		KSub = (Button) findViewById(R.id.bSUBK);
		KCount = (TextView) findViewById(R.id.tvKCount);
		K = new Stat(KAdd, KSub, KCount);
		stats.add(K);
		
		OneBAdd = (Button) findViewById(R.id.bADD1B);
		OneBSub = (Button) findViewById(R.id.bSUB1B);
		OneBCount = (TextView) findViewById(R.id.tv1BCount);
		ONEB = new Stat(OneBAdd, OneBSub, OneBCount);
		stats.add(ONEB);
		
		TwoBAdd = (Button) findViewById(R.id.bADD2B);
		TwoBSub = (Button) findViewById(R.id.bSUB2B);
		TwoBCount = (TextView) findViewById(R.id.tv2BCount);
		TWOB = new Stat(TwoBAdd, TwoBSub, TwoBCount);
		stats.add(TWOB);
		
		ThreeBAdd = (Button) findViewById(R.id.bADD3B);
		ThreeBSub = (Button) findViewById(R.id.bSUB3B);
		ThreeBCount = (TextView) findViewById(R.id.tv3BCount);
		THREEB = new Stat(ThreeBAdd, ThreeBSub, ThreeBCount);
		stats.add(THREEB);
		
		HRAdd = (Button) findViewById(R.id.bADDHR);
		HRSub = (Button) findViewById(R.id.bSUBHR);
		HRCount = (TextView)findViewById(R.id.tvHRCount);
		HR = new Stat(HRAdd, HRSub, HRCount);
		stats.add(HR);
		
		RUNSAdd = (Button) findViewById(R.id.bADDR);
		RUNSSub = (Button) findViewById(R.id.bSUBR);
		RUNSCount = (TextView) findViewById(R.id.tvRCount);
		RUNS = new Stat(RUNSAdd, RUNSSub, RUNSCount);
		stats.add(RUNS);
		
		RBIAdd = (Button) findViewById(R.id.bADDRBI);
		RBISub = (Button) findViewById(R.id.bSUBRBI);
		RBICount = (TextView)findViewById(R.id.tvRBICount);
		RBI = new Stat(RBIAdd, RBISub, RBICount);
		stats.add(RBI);
		
		ROEAdd = (Button) findViewById(R.id.bADDROE);
		ROESub = (Button) findViewById(R.id.bSUBROE);
		ROECount = (TextView) findViewById(R.id.tvROECount);
		ROE = new Stat(ROEAdd, ROESub, ROECount);
		stats.add(ROE);
		
		FCAdd = (Button) findViewById(R.id.bADDFC);
		FCSub = (Button) findViewById(R.id.bSUBFC);
		FCCount = (TextView) findViewById(R.id.tvFCCount);
		FC = new Stat(FCAdd, FCSub, FCCount);
		stats.add(FC);
		
		EAdd = (Button) findViewById(R.id.bADDE);
		ESub = (Button) findViewById(R.id.bSUBE);
		ECount = (TextView) findViewById(R.id.tvECount);
		E = new Stat(EAdd, ESub, ECount);
		stats.add(E);
		
		CSAdd = (Button) findViewById(R.id.bADDCS);
		CSSub = (Button) findViewById(R.id.bSUBCS);
		CSCount = (TextView) findViewById(R.id.tvCSCount);
		CS = new Stat(CSAdd, CSSub, CSCount);
		stats.add(CS);
		
		SBAAdd = (Button) findViewById(R.id.bADDSBA);
		SBASub = (Button) findViewById(R.id.bSUBSBA);
		SBACount = (TextView) findViewById(R.id.tvSBACount);
		SBA = new Stat(SBAAdd, SBASub, SBACount);
		stats.add(SBA);
		
		calcAVG = (TextView) this.findViewById(R.id.tvAVGVal);
		calcOBP  = (TextView) this.findViewById(R.id.tvOBPVal);
		calcCSPerc = (TextView) this.findViewById(R.id.tvCSPVal);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bEdit:
				if(Edit.getText().equals("Edit"))
					Edit.setText("Done");
				else								
					Edit.setText("Edit");
				
				for(Stat s : stats) 
					s.toggleButtons();
				
				break;
			case R.id.bBack:
				this.finish();
				break;
			case R.id.bSave:
				
				updateDB();
				pullStatsFromDB();
				break;
		}
	}
	
	public void updateDB(){
		dba.open();
		dba.updateGameStats(GID, stats.get(0).getCount(), stats.get(1).getCount(), stats.get(2).getCount(), stats.get(3).getCount(), stats.get(4).getCount(),
				 stats.get(5).getCount(), stats.get(6).getCount(), stats.get(7).getCount(), stats.get(8).getCount(), stats.get(9).getCount(),
				 stats.get(10).getCount(), stats.get(11).getCount(), stats.get(12).getCount(), stats.get(13).getCount(), stats.get(14).getCount(), 
				 stats.get(15).getCount(), stats.get(16).getCount(), stats.get(17).getCount()
				);
		dba.close();
	}
}

