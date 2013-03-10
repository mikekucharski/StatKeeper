package stat.keeper;

public class Team {
	
	private String teamName, seasonName;
	private int year;
	
	public Team(String teamName, String seasonName, int year){
		this.teamName = teamName;
		this.seasonName = seasonName;
		this.year = year;
	}
	
	public String getTeamName() {return teamName;}
	public String getSeasonName() {return seasonName;}
	public int getYear() {return year;}
	
	public String toString(){
		return teamName + ", " + seasonName + ", " + year;
	}
}
