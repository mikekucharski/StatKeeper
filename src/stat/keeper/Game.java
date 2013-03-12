package stat.keeper;

public class Game {
	private long foreign_TID;
	private String opponent, location, gameDate;
	private int yourScore, oppScore;
	private int statH, statAB, statBB, statHBP, statSACf, statPA, statK, 
				stat1B, stat2B, stat3B, statHR, statR, statRBI, statSB,
				statROE, statFC, statE, statCS, statSBA;
	
	public Game(long foreign_TID, String opponent, String location, String gameDate, 
			int yourScore, int oppScore){
		this(foreign_TID, opponent, location, gameDate, yourScore, oppScore, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
	}
	
	public Game(long foreign_TID, String opponent, String location, String gameDate, 
			int yourScore, int oppScore, int statH, int statAB, int statBB, int statHBP, int statSACf,
			int statPA, int statK, int stat1B, int stat2B, int stat3B, int statHR, 
			int statR, int statRBI, int statSB, int statROE, int statFC, int statE, int statCS, int statSBA){
		
		this.foreign_TID = foreign_TID;
		this.opponent = opponent;
		this.location = location;
		this.gameDate = gameDate;
		this.yourScore = yourScore;
		this.oppScore = oppScore;
		
		this.statH = statH;
		this.statAB = statAB;
		this.statBB = statBB;
		this.statHBP = statHBP;
		this.statSACf = statSACf;
		this.statPA = statPA;
		this.statK = statK;
		this.stat1B = stat1B;
		this.stat2B = stat2B;
		this.stat3B = stat3B;
		this.statHR = statHR;
		this.statR = statR;
		this.statRBI = statRBI;
		this.statSB = statSB;
		this.statROE = statROE;
		this.statFC = statFC;
		this.statE = statE;
		this.statCS = statCS;
		this.statSBA = statSBA;

	}
	
	public long getTeamID() {return foreign_TID;}
	public String getOpponent() {return opponent;}
	public String getLocation() {return location;}
	public String getGameDate() {return gameDate;}
	public int getYourScore() {return yourScore;}
	public int getOppScore() {return oppScore;}
	public int getstatH() {return statH;}
	public int getstatAB() {return statAB;}
	public int getstatBB() {return statBB;}
	public int getstatHBP() {return statHBP;}
	public int getstatSACf() {return statSACf;}
	public int getstatPA() {return statPA;}
	public int getstatK()  {return statK;}
	public int getstat1B() {return stat1B;}
	public int getstat2B() {return stat2B;}
	public int getstat3B() {return stat3B;}
	public int getstatHR() {return statHR;}
	public int getstatR()   {return statR;}
	public int getstatRBI() {return statRBI;}
	public int getstatSB() {return statSB;}
	public int getstatROE() {return statROE;}
	public int getstatFC() {return statFC;}
	public int getstatE()  {return statE;}
	public int getstatCS() {return statCS;}
	public int getstatSBA() {return statSBA;}
	
	public String toString(){
		String winOrLoss;
		if(yourScore > oppScore)
			winOrLoss = "W";
		else if(yourScore < oppScore)
			winOrLoss = "L";
		else
			winOrLoss = "T";
		
		return "" + opponent + ",       " + winOrLoss;
	}
	
	public String printMeOut(){
		return "foreign_TID: " + foreign_TID + " opp: " + opponent + " loc: " + location + " date: " + gameDate;
	}
}
