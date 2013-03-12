package stat.keeper;

import java.util.ArrayList;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class TeamListActivity extends ListActivity implements View.OnClickListener{
	
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
		newItem.setText("New Team");
		newItem.setOnClickListener(this);
		goBack = (Button) this.findViewById(R.id.bGoBack);
		goBack.setText("Back To Home");
		goBack.setOnClickListener(this);
		
		dba = new DBAdapter(TeamListActivity.this);
		dba.open();
		ArrayList<Team> dbTeams = dba.getAllTeams();
		dba.close();
		
		teams = new ArrayList<String>();
		for(Team t : dbTeams)
			teams.add(t.toString());

		// create our menu in java not xml
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, teams);
		this.setListAdapter(adapter);
		
		// set up on long holds
		ListView lv = this.getListView();
		this.registerForContextMenu(lv);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
				String teamPressed = teams.get(position);
				teamPressed = teamPressed.substring(0, teamPressed.indexOf(","));
				editOrDelete(teamPressed);
				return false;
			}
		});
	}

	private void updateTeamList() {
		dba.open();
		ArrayList<Team> dbTeams = dba.getAllTeams();
		dba.close();
		
		teams.clear();
		for(Team t : dbTeams){
			Log.i("emulator", "Adding TEAM: " + t);
			teams.add(t.toString());
		}

		adapter.notifyDataSetChanged();
	}

	private void editOrDelete(final String teamInQuestion){
		AlertDialog.Builder dialog = new AlertDialog.Builder(TeamListActivity.this);
		dialog.setTitle("" + teamInQuestion);
		dialog.setMessage("Would you like to edit or delete this team?");
		dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteListItem(teamInQuestion);
			}
		});
		dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int arg1) {
				editListItem(teamInQuestion);
			}
		});
		dialog.show();
	}
	
	protected void editListItem(String teamName) {
			Intent ourIntent = new Intent(TeamListActivity.this, EditTeamActivity.class);
			Bundle b = new Bundle();
			b.putString("team_name", teamName);
			ourIntent.putExtras(b);
			TeamListActivity.this.startActivity(ourIntent);
		
	}

	private void deleteListItem(final String itemToDelete){
		AlertDialog.Builder dialog = new AlertDialog.Builder(TeamListActivity.this);
		dialog.setTitle("Removing " + itemToDelete);
		dialog.setMessage("Are you sure you want to remove this team?");
		dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dba.open();
				dba.deleteTeamEntry(dba.getTID(itemToDelete));
				dba.close();
				
				updateTeamList();
			}
		});
		dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int arg1) {
				// do nothing
			}
		});
		dialog.show();
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bGoBack:
				this.finish();
				break;
			case R.id.bAddEntry:
				addNewEntry();
				break;
			}
	}
	
	private void addNewEntry(){
		// start new Team activity
		Intent ourIntent = new Intent(TeamListActivity.this, NewTeamActivity.class);
		TeamListActivity.this.startActivity(ourIntent);
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

		Intent ourIntent = new Intent(TeamListActivity.this, GameListActivity.class);
		ourIntent.putExtra("team_id", team_id);
		startActivity(ourIntent);

	}

	@Override
	protected void onResume() {
		super.onResume();
		updateTeamList();
	}

}