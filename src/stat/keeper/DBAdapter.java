package stat.keeper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class DBAdapter {
	
	// set up database
	private static final String DATABASE_NAME = "DBBaseball";
	private static final int DATABASE_VERSION = 5;

	private DatabaseHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	// Games table
	private static final String GAMES_TABLE = "games";
	public static final String KEY_GID = "g_id";
	public static final String KEY_FK_TID = "foreign_team_id";
	public static final String KEY_OPPONENT = "opponent_team";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_DATE = "game_date";
	public static final String KEY_YOUR_SCORE = "your_score";
	public static final String KEY_OPPONENT_SCORE = "opponent_score";
	public static final String KEY_STAT_H = "H";
	public static final String KEY_STAT_AB = "AB";
	public static final String KEY_STAT_BB = "BB";
	public static final String KEY_STAT_HBP = "HBP";
	public static final String KEY_STAT_SACf = "SACf";
	public static final String KEY_STAT_PA = "PA";
	public static final String KEY_STAT_K = "K";
	public static final String KEY_STAT_1B = "OneB";
	public static final String KEY_STAT_2B = "TwoB";
	public static final String KEY_STAT_3B = "ThreeB";
	public static final String KEY_STAT_HR = "HR";
	public static final String KEY_STAT_R = "R";
	public static final String KEY_STAT_RBI = "RBI";
	public static final String KEY_STAT_SB = "SB";
	public static final String KEY_STAT_ROE = "ROE";
	public static final String KEY_STAT_FC = "FC";
	public static final String KEY_STAT_E = "E";
	public static final String KEY_STAT_CS = "CS";
	public static final String KEY_STAT_SBA = "SBA";
	
	
	// Teams table
	private static final String TEAMS_TABLE = "teams";
	public static final String KEY_TID = "t_id";
	public static final String KEY_TEAMNAME = "team_name";
	public static final String KEY_SEASONNAME = "season_name";
	public static final String KEY_YEAR = "year";
	
	private static class DatabaseHelper extends SQLiteOpenHelper {
		
	    DatabaseHelper(Context context) 
	    {
	    	super(context, DATABASE_NAME, null, DATABASE_VERSION);
//	    	super(new ContextWrapper(context) {
//	            @Override public SQLiteDatabase openOrCreateDatabase(String name, 
//	                    int mode, SQLiteDatabase.CursorFactory factory) {
//
//	            	File sdcard = Environment.getExternalStorageDirectory();
//	                // allow database directory to be specified
//	                File dir = new File(sdcard.getAbsolutePath() + "/StatKeeper2/data");
//	            
//	                if(!dir.exists()) {
//	                	Log.i("emulator", "making dir");
//	                    dir.mkdirs();
//	                }
//	                
//	                return SQLiteDatabase.openDatabase(dir + "/" + DATABASE_NAME, null,
//	                    SQLiteDatabase.CREATE_IF_NECESSARY);
//	            }
//	        }, DATABASE_NAME, null, DATABASE_VERSION);
	    }

		@Override
		public void onCreate(SQLiteDatabase db) {
			// create our database tables
			db.execSQL("CREATE TABLE " + TEAMS_TABLE + " (" +
					KEY_TID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_TEAMNAME + " TEXT NOT NULL, " +
					KEY_SEASONNAME + " TEXT NOT NULL, " +
					KEY_YEAR + " INTEGER NOT NULL)"
			);
			
			db.execSQL("CREATE TABLE " + GAMES_TABLE + " (" +
					KEY_GID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
					KEY_FK_TID + " INTEGER, " +
					//"FOREIGN KEY(" + KEY_FK_TID + ") REFERENCES " + TEAMS_TABLE + "(" + KEY_TID + ") ON DELETE CASCADE, " +
					KEY_OPPONENT + " TEXT NOT NULL, " +
					KEY_LOCATION + " TEXT NOT NULL, " +
					KEY_DATE + " TEXT NOT NULL, " +
					KEY_YOUR_SCORE + " INTEGER NOT NULL, " +
					KEY_OPPONENT_SCORE + " INTEGER NOT NULL," +
					KEY_STAT_H + " INTEGER NOT NULL," +
					KEY_STAT_AB + " INTEGER NOT NULL," +
					KEY_STAT_BB + " INTEGER NOT NULL," +
					KEY_STAT_HBP + " INTEGER NOT NULL," +
					KEY_STAT_SACf + " INTEGER NOT NULL," +
					KEY_STAT_PA + " INTEGER NOT NULL," +
					KEY_STAT_K + " INTEGER NOT NULL," +
					KEY_STAT_1B + " INTEGER NOT NULL," +
					KEY_STAT_2B + " INTEGER NOT NULL," +
					KEY_STAT_3B + " INTEGER NOT NULL," +
					KEY_STAT_HR + " INTEGER NOT NULL," +
					KEY_STAT_R + " INTEGER NOT NULL," +
					KEY_STAT_RBI + " INTEGER NOT NULL," +
					KEY_STAT_SB + " INTEGER NOT NULL," +
					KEY_STAT_ROE + " INTEGER NOT NULL," +
					KEY_STAT_FC + " INTEGER NOT NULL," +
					KEY_STAT_E + " INTEGER NOT NULL," +
					KEY_STAT_CS + " INTEGER NOT NULL," +
					KEY_STAT_SBA + " INTEGER NOT NULL)"
			);
			
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			 Log.w("emulator", oldVersion + " to " + newVersion
			          + ", which will destroy all old data");
			 db.execSQL("DROP TABLE IF EXISTS " + TEAMS_TABLE);
			 db.execSQL("DROP TABLE IF EXISTS " + GAMES_TABLE);
			 onCreate(db);
		}
	} // Database helper class end
	
	// DBAdapter constructor
	public DBAdapter(Context context){
		ourContext = context;
		ourHelper = new DatabaseHelper(ourContext);
	}
	
	public DBAdapter open() throws SQLException {
		
	    ourDatabase = ourHelper.getWritableDatabase();
	    //ourDatabase.openDatabase(path, factory, flags);

	    if (!ourDatabase.isReadOnly()) {
	    	ourDatabase.execSQL("PRAGMA foreign_keys=ON;");
	    }
	    return this;
	}
	
	public void close() {
		ourHelper.close();
		//copyDBtoSD();
	}
	
	private void copyDBtoSD() {
		try {
	        File sd = Environment.getExternalStorageDirectory();
	        File data = Environment.getDataDirectory();

	        if (sd.canWrite()) {
	            String currentDBPath = "//data//stat//keeper//databases//" + DATABASE_NAME;
	            File currentDB = new File(data, currentDBPath);
	            File backupDB = new File(sd.getAbsolutePath() + "//StatKeeper//Data//" + DATABASE_NAME);

	            if(!backupDB.exists()){
	            	Log.i("emulator", " ADDING DIRS");
	            	backupDB.mkdirs();
	            }
	            
	            if (currentDB.exists()) {
	            	Log.i("emulator", "CURRENT DB EXISTS");
	                FileChannel src = new FileInputStream(currentDB).getChannel();
	                FileChannel dst = new FileOutputStream(backupDB).getChannel();
	                dst.transferFrom(src, 0, src.size());
	                src.close();
	                dst.close();
	            }
	        }
	    } catch (Exception e) {
	    }
	}

	public long addGameEntry(Game newGame){
		ContentValues cv = new ContentValues();
		cv.put(KEY_FK_TID, newGame.getTeamID());
		cv.put(KEY_OPPONENT, newGame.getOpponent());
		cv.put(KEY_LOCATION, newGame.getLocation());
		cv.put(KEY_DATE, newGame.getGameDate());
		cv.put(KEY_YOUR_SCORE, newGame.getYourScore());
		cv.put(KEY_OPPONENT_SCORE, newGame.getOppScore());
		cv.put( KEY_STAT_H , 0 );
		cv.put( KEY_STAT_AB , 0 );
		cv.put( KEY_STAT_BB , 0);
		cv.put( KEY_STAT_HBP, 0);
		cv.put( KEY_STAT_SACf , 0);
		cv.put( KEY_STAT_PA , 0);
		cv.put( KEY_STAT_K , 0);
		cv.put( KEY_STAT_1B , 0);
		cv.put( KEY_STAT_2B , 0);
		cv.put( KEY_STAT_3B , 0);
		cv.put( KEY_STAT_HR , 0);
		cv.put( KEY_STAT_R , 0);
		cv.put( KEY_STAT_RBI , 0);
		cv.put( KEY_STAT_SB , 0);
		cv.put( KEY_STAT_ROE , 0);
		cv.put( KEY_STAT_FC , 0);
		cv.put( KEY_STAT_E , 0);
		cv.put( KEY_STAT_CS , 0);
		cv.put( KEY_STAT_SBA , 0);

		return ourDatabase.insert(GAMES_TABLE, null, cv);
	}
	
	public long addTeamEntry(Team addTeam){
		ContentValues cv = new ContentValues();
		cv.put(KEY_TEAMNAME, addTeam.getTeamName());
		cv.put(KEY_SEASONNAME, addTeam.getSeasonName());
		cv.put(KEY_YEAR, addTeam.getYear());
		
		
		// insert cv into database
		// table we want to write to, 
		return ourDatabase.insert(TEAMS_TABLE, null, cv);
	}

	public ArrayList<Game> getAllGames(long f_TID) {
		ArrayList<Game> games = new ArrayList<Game>();
		//" WHERE " + KEY_FK_TID + "=" + f_TID
		Cursor gc = ourDatabase.rawQuery("SELECT * FROM " + GAMES_TABLE + " WHERE " + KEY_FK_TID + "=" + f_TID, null);
		if (gc == null)
			return null;
		else
			gc.moveToFirst();
		
		int iOpponentName = gc.getColumnIndex(KEY_OPPONENT);
		int iLocation = gc.getColumnIndex(KEY_LOCATION);
		int iDate = gc.getColumnIndex(KEY_DATE);
		int iYourScore = gc.getColumnIndex(KEY_YOUR_SCORE);
		int iOppScore = gc.getColumnIndex(KEY_OPPONENT_SCORE);
		
		// cycle our cursor through the data until the database query is empty
		for(gc.moveToFirst(); !gc.isAfterLast(); gc.moveToNext()){
			
			Log.i("emulator", "GID: " + gc.getString(gc.getColumnIndex(KEY_GID)) + " Opp " +   gc.getString(iOpponentName) + " Loc " +  gc.getString(iLocation) + " date " +
					gc.getString(iDate) + " YScore " + gc.getInt(iYourScore) + " OScore "  + gc.getInt(iOppScore) );
			
			Game thisGame = new Game(f_TID, gc.getString(iOpponentName), gc.getString(iLocation), 
					gc.getString(iDate), gc.getInt(iYourScore), gc.getInt(iOppScore) );
			games.add( thisGame );
		}
		
		gc.close();
		return games;
	}
	
	public ArrayList<Team> getAllTeams() {
		
		ArrayList<Team> teams = new ArrayList<Team>();
		
		String[] attributes = new String[]{KEY_TID, KEY_TEAMNAME, KEY_SEASONNAME, KEY_YEAR};
		Cursor c = ourDatabase.query(TEAMS_TABLE, attributes, null, null, null, null, null);
		
		int iTID = c.getColumnIndex(KEY_TID);
		int iTeamName = c.getColumnIndex(KEY_TEAMNAME);
		int iSeasonName = c.getColumnIndex(KEY_SEASONNAME);
		int iYear = c.getColumnIndex(KEY_YEAR);
		
		// cycle our cursor through the data until the database query is empty
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			
			Log.i("emulator", "TID: " + c.getString(iTID) + " TEAM: " + c.getString(iTeamName) +
					" SEASON: " + c.getString(iSeasonName) + " YEAR: " + c.getString(iYear));
			
			Team thisTeam = new Team(c.getString(iTeamName), c.getString(iSeasonName), c.getInt(iYear));
			teams.add( thisTeam );
		}
		
		c.close();
		return teams;
	}
	
	public Team getTeam(long team_ID){
		Cursor teamCursor = ourDatabase.rawQuery("SELECT * FROM " + TEAMS_TABLE +
										" WHERE " + KEY_TID + "=" + team_ID, null);
		
		if (teamCursor == null)
			return null;
		else
			teamCursor.moveToFirst();
		
		int iTeamName = teamCursor.getColumnIndex(KEY_TEAMNAME);
		int iSeasonName = teamCursor.getColumnIndex(KEY_SEASONNAME);
		int iYear = teamCursor.getColumnIndex(KEY_YEAR);
		
		Team foundTeam = new Team(teamCursor.getString(iTeamName), teamCursor.getString(iSeasonName), teamCursor.getInt(iYear));
		teamCursor.close();
		
		return foundTeam;
	}

	public boolean deleteTeamEntry(long row_id) {
		return ourDatabase.delete(TEAMS_TABLE, KEY_TID + "=" + row_id, null) > 0;
	}
	
	public long getTID(String teamName){
		String[] attributes = new String[]{KEY_TID, KEY_TEAMNAME};
		Cursor c = ourDatabase.query(TEAMS_TABLE, attributes, null, null, null, null, null);
		
		int iTID = c.getColumnIndex(KEY_TID);
		int iTeamName = c.getColumnIndex(KEY_TEAMNAME);
		String iTIDFound = null;
		
		for(c.moveToFirst(); !c.isAfterLast(); c.moveToNext()){
			if(c.getString(iTeamName).equals(teamName)) {
				iTIDFound = c.getString(iTID);
				break;
			}
		}
		c.close();
		return Long.parseLong(iTIDFound);
	}

	public void updateTeam(long tid, Team newTeam) {
		// make new content values with passed information
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_TEAMNAME, newTeam.getTeamName());
		cvUpdate.put( KEY_SEASONNAME, newTeam.getSeasonName());
		cvUpdate.put( KEY_YEAR, newTeam.getYear());
		
		// update database with new CV
		ourDatabase.update(TEAMS_TABLE, cvUpdate, KEY_TID + "=" + tid, null);
	}

	public long getGID(String gameInQuestion) {
		Cursor gc = ourDatabase.rawQuery("SELECT * FROM " + GAMES_TABLE, null);
		if (gc == null)
			return -1;
		
		int iGID = gc.getColumnIndex(KEY_GID);
		int iOppTeam = gc.getColumnIndex(KEY_OPPONENT);
		String iGIDFound = null;
		
		for(gc.moveToFirst(); !gc.isAfterLast(); gc.moveToNext()){
			if(gc.getString(iOppTeam).equals(gameInQuestion)) {
				iGIDFound = gc.getString(iGID);
				break;
			}
		}
		gc.close();
		return Long.parseLong(iGIDFound);
	}
	
	public Game getGame(long GID){
		Cursor gc = ourDatabase.rawQuery("SELECT * FROM " + GAMES_TABLE
				+ " WHERE " + KEY_GID + "=" + GID, null);

		if (gc == null)
			return null;
		else
			gc.moveToFirst();

		int iTID = gc.getColumnIndex(KEY_FK_TID);
		int iOpp = gc.getColumnIndex(KEY_OPPONENT);
		int iLoc = gc.getColumnIndex(KEY_LOCATION);
		int iDate = gc.getColumnIndex(KEY_DATE);
		int iYScore = gc.getColumnIndex(KEY_YOUR_SCORE);
		int iOScore = gc.getColumnIndex(KEY_OPPONENT_SCORE);
		int iStatH = gc.getColumnIndex(KEY_STAT_H);
		int iStatAB = gc.getColumnIndex(KEY_STAT_AB);
		int istatBB = gc.getColumnIndex(KEY_STAT_BB);
		int istatHBP = gc.getColumnIndex(KEY_STAT_HBP);
		int istatSACf = gc.getColumnIndex(KEY_STAT_SACf);
		int istatPA = gc.getColumnIndex(KEY_STAT_PA);
		int istatK = gc.getColumnIndex(KEY_STAT_K);
		int istat1B = gc.getColumnIndex(KEY_STAT_1B);
		int istat2B = gc.getColumnIndex(KEY_STAT_2B);
		int istat3B = gc.getColumnIndex(KEY_STAT_3B);
		int istatHR = gc.getColumnIndex(KEY_STAT_HR);
		int istatR = gc.getColumnIndex(KEY_STAT_R);
		int istatRBI = gc.getColumnIndex(KEY_STAT_RBI);
		int istatSB = gc.getColumnIndex(KEY_STAT_SB);
		int istatROE = gc.getColumnIndex(KEY_STAT_ROE);
		int istatFC = gc.getColumnIndex(KEY_STAT_FC);
		int istatE = gc.getColumnIndex(KEY_STAT_E);
		int istatCS = gc.getColumnIndex(KEY_STAT_CS);
		int istatSBA = gc.getColumnIndex(KEY_STAT_SBA);

		Game foundGame = new Game(gc.getLong(iTID), gc.getString(iOpp), gc.getString(iLoc), 
								  gc.getString(iDate), gc.getInt(iYScore), gc.getInt(iOScore),
								  gc.getInt(iStatH), gc.getInt(iStatAB), gc.getInt(istatBB),
								  gc.getInt(istatHBP), gc.getInt(istatSACf), gc.getInt(istatPA),
								  gc.getInt(istatK), gc.getInt(istat1B), gc.getInt(istat2B),
								  gc.getInt(istat3B), gc.getInt(istatHR), gc.getInt(istatR),
								  gc.getInt(istatRBI), gc.getInt(istatSB), gc.getInt(istatROE),
								  gc.getInt(istatFC), gc.getInt(istatE), gc.getInt(istatCS), 
								  gc.getInt(istatSBA)
								  );
		
		gc.close();

		return foundGame;
	}
	
	public void updateGame(long gid, Game newGame) {
		// make new content values with passed information
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put(KEY_OPPONENT, newGame.getOpponent() );
		cvUpdate.put(KEY_LOCATION, newGame.getLocation() );
		cvUpdate.put(KEY_DATE, newGame.getGameDate() );
		cvUpdate.put(KEY_YOUR_SCORE, newGame.getYourScore() );
		cvUpdate.put(KEY_OPPONENT_SCORE, newGame.getOppScore() );
		
		// update database with new CV
		ourDatabase.update(GAMES_TABLE, cvUpdate, KEY_GID + "=" + gid, null);
	}
	
	public void updateGameStats( long gid, int statH, int statAB, int statBB, int statHBP, int statSACf,
			int statPA, int statK, int stat1B, int stat2B, int stat3B, int statHR, 
			int statR, int statRBI, int statSB, int statROE, int statFC, int statE, int statCS, int statSBA  )
	{
		ContentValues cvUpdate = new ContentValues();
		cvUpdate.put( KEY_STAT_H , statH );
		cvUpdate.put( KEY_STAT_AB , statAB );
		cvUpdate.put( KEY_STAT_BB , statBB);
		cvUpdate.put( KEY_STAT_HBP, statHBP);
		cvUpdate.put( KEY_STAT_SACf , statSACf);
		cvUpdate.put( KEY_STAT_PA , statPA);
		cvUpdate.put( KEY_STAT_K , statK);
		cvUpdate.put( KEY_STAT_1B , stat1B);
		cvUpdate.put( KEY_STAT_2B , stat2B);
		cvUpdate.put( KEY_STAT_3B , stat3B);
		cvUpdate.put( KEY_STAT_HR , statHR);
		cvUpdate.put( KEY_STAT_R , statR);
		cvUpdate.put( KEY_STAT_RBI , statRBI);
		cvUpdate.put( KEY_STAT_SB , statSB);
		cvUpdate.put( KEY_STAT_ROE , statROE);
		cvUpdate.put( KEY_STAT_FC , statFC);
		cvUpdate.put( KEY_STAT_E , statE);
		cvUpdate.put( KEY_STAT_CS , statCS);
		cvUpdate.put( KEY_STAT_SBA , statSBA);
		
		ourDatabase.update(GAMES_TABLE, cvUpdate, KEY_GID + "=" + gid, null);
	}

	public boolean deleteGameEntry(long gid) {
		return ourDatabase.delete(GAMES_TABLE, KEY_GID + "=" + gid, null) > 0;
	}

	public int getTotalStat(long TID, String stat) {
		int total = -1;
		
		String sql="select Sum("+stat+") as TOTAL from games where foreign_team_id=" + TID;
		Cursor tc = ourDatabase.rawQuery(sql, null);

		if (tc == null)
			return -1;
	
		tc.moveToFirst();
		total = tc.getInt(tc.getColumnIndex("TOTAL"));
		tc.close();
		
		return total;
	}

	public String getWLT(long team_id) {
		int wins=0, losses=0, ties=0;
		
		String SQLgetAllGames="select * from games where "+KEY_FK_TID+"=" + team_id;
		Cursor gc = ourDatabase.rawQuery(SQLgetAllGames, null);
		
		int iYScore = gc.getColumnIndex(KEY_YOUR_SCORE);
		int iOScore = gc.getColumnIndex(KEY_OPPONENT_SCORE);

		if (gc == null)
			return "0-0-0";
		
		for(gc.moveToFirst(); !gc.isAfterLast(); gc.moveToNext())
		{
			if(gc.getInt(iYScore) > gc.getInt(iOScore))
				wins++;
			else if (gc.getInt(iYScore) < gc.getInt(iOScore))
				losses++;
			else
				ties++;
		}
		
		gc.close();
		
		return wins + "-" + losses + "-" + ties;
	}

}
