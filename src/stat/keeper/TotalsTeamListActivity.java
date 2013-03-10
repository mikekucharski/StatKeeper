package stat.keeper;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class TotalsTeamListActivity extends ListActivity implements OnClickListener{
	private ArrayList<String> teams;
	private ArrayAdapter<String> adapter;
	private Button newItem, goBack;
	private TextView title;
	private DBAdapter dba;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.menu);

		title =  (TextView) this.findViewById(R.id.tvTitle);
		title.setText("My Teams");
		
		newItem = (Button) this.findViewById(R.id.bAddEntry);
		newItem.setVisibility(View.GONE);
		
		goBack = (Button) this.findViewById(R.id.bGoBack);
		goBack.setText("Back To Home");
		goBack.setOnClickListener(this);
		
		dba = new DBAdapter(TotalsTeamListActivity.this);
		dba.open();
		ArrayList<Team> dbTeams = dba.getAllTeams();
		dba.close();
		
		teams = new ArrayList<String>();
		for(Team t : dbTeams)
			teams.add(t.toString());

		// create our menu in java not xml
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams);
		this.setListAdapter(adapter);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.bGoBack:
			this.finish();
			break;
		}
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		// Get name of team pressed
		String teampressed = teams.get(position);
		teampressed = teampressed.substring(0, teampressed.indexOf(","));
		// get that teams team_id
		dba.open();
		long team_id = dba.getTID(teampressed);
		dba.close();

		Intent ourIntent = new Intent(TotalsTeamListActivity.this, TotalStatViewerActivity.class);
		ourIntent.putExtra("team_id", team_id);
		startActivity(ourIntent);

	}
}
