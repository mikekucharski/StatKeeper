package stat.keeper;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemLongClickListener;

public class GameListActivity extends ListActivity implements View.OnClickListener{

	private ArrayList<String> games;
	private ArrayAdapter<String> adapter;
	private Button newItem, goBack;
	private TextView title;
	private DBAdapter dba;
	private long team_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.menu);
		
		dba = new DBAdapter(GameListActivity.this);
		
		team_id = this.getIntent().getExtras().getLong("team_id");
		dba.open();
		Team currentTeam = dba.getTeam(team_id);
		dba.close();
		
		
		// set up button references to xml
		title =  (TextView) this.findViewById(R.id.tvTitle);
		title.setText(currentTeam.getTeamName() + " Games");
		newItem = (Button) this.findViewById(R.id.bAddEntry);
		newItem.setText("New Game");
		newItem.setOnClickListener(this);
		goBack = (Button) this.findViewById(R.id.bGoBack);
		goBack.setText("Back To Teams");
		goBack.setOnClickListener(this);
				

		games = new ArrayList<String>();
		// populate games table
		dba.open();
		ArrayList<Game> dbGames = dba.getAllGames(team_id);
		dba.close();
		
		games = new ArrayList<String>();
		for(Game g : dbGames)
			games.add(g.toString());

		// create our menu in java not xml
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, games);
		this.setListAdapter(adapter);
		
		// set up on lon holds
		ListView lv = this.getListView();
		this.registerForContextMenu(lv);
		lv.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View v, int position, long id) {
				String gamePressed = games.get(position);
				gamePressed = gamePressed.substring(0, gamePressed.indexOf(","));
				editOrDelete(gamePressed);
				return false;
			}
		});
	}
	
	private void updateGameList() {
		dba.open();
		ArrayList<Game> dbGames = dba.getAllGames(team_id);
		dba.close();
		
		games.clear();
		for(Game g : dbGames){
			Log.i("emulator", "Updating GAME : " + g.printMeOut());
			games.add(g.toString());
		}

		adapter.notifyDataSetChanged();
	}
	
	private void editOrDelete(final String gameInQuestion){
		AlertDialog.Builder dialog = new AlertDialog.Builder(GameListActivity.this);
		dialog.setTitle("" + gameInQuestion);
		dialog.setMessage("Would you like to edit or delete this game?");
		dialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				deleteListItem(gameInQuestion);
			}
		});
		
		dialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface d, int arg1) {
				editListItem(gameInQuestion);
			}

		});
		dialog.show();
	}
	
	private void editListItem(String gameInQuestion) {
		Intent ourIntent = new Intent(GameListActivity.this, EditGameActivity.class);
		Bundle b = new Bundle();
		dba.open();
		long gid = dba.getGID(gameInQuestion);
		b.putLong("game_ID", gid);
		ourIntent.putExtras(b);
		startActivity(ourIntent);
	}
	
	private void deleteListItem(final String itemToDelete) {
		AlertDialog.Builder dialog = new AlertDialog.Builder(GameListActivity.this);
		dialog.setTitle("Removing " + itemToDelete);
		dialog.setMessage("Are you sure you want to remove this game?");
		dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dba.open();
				dba.deleteGameEntry(dba.getGID(itemToDelete));
				dba.close();
				
				updateGameList();
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
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		Intent ourIntent = new Intent(GameListActivity.this, StatViewerActivity.class);
		String gamePressed = games.get(position);
		gamePressed = gamePressed.substring(0, gamePressed.indexOf(","));
		dba.open();
		long GID = dba.getGID(gamePressed);
		dba.close();
		ourIntent.putExtra("game_ID", GID);
		startActivity(ourIntent);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.bGoBack:
				this.finish();
				break;
			case R.id.bAddEntry:
				Intent addGameIntent = new Intent(GameListActivity.this, NewGameActivity.class);
				addGameIntent.putExtra("team_id", team_id );
				startActivity(addGameIntent);
				break;
		}
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		updateGameList();
	}

}
