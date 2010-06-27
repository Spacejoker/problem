import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class One {
	InputStreamReader  inp = new InputStreamReader(System.in);
	BufferedReader in = new BufferedReader(inp);
	boolean test = false;

//	String[] inData = { "4", "A", "B", "C", "D", "A-B 1:1", "A-C 2:2", "A-D 1:0", "B-C 1:0", "B-D 0:3", "C-D 0:3" };
	String[] inData = { "2","a","A","a-A 2:1"};
	
	

	class Team {
		int score = 0;
		int goalsFor = 0;
		int goalsAgains = 0;
		String name = "";

		public Team(String s) {
			name = s;
		}

		@Override
		public String toString() {
			return "Team [goalsAgains=" + goalsAgains + ", goalsFor=" + goalsFor + ", name=" + name + ", score=" + score + "]";
		}
	}

	static int id = -1;

	public String readLine() throws IOException {
		id++;
		if(test)
			return inData[id];
		else
			return in.readLine();
	}

	public void solve() throws Exception{
		int nrOfTeams = Integer.valueOf(readLine());

		List<Team> teams = new ArrayList();

		for (int i = 0; i < nrOfTeams; i++) {
			teams.add(new Team(readLine()));
		}

		List<String> matches = new ArrayList();

		int matchNr =  nrOfTeams * (nrOfTeams - 1)/2;
		for (int j = 0; j < matchNr; j++) {
			matches.add(readLine());
		}

		for (String s : matches) {

			String t1 = s.split(" ")[0].split("-")[0];
			String t2 = s.split(" ")[0].split("-")[1];

			int s1 = Integer.valueOf(s.split(" ")[1].split(":")[0]);
			int s2 = Integer.valueOf(s.split(" ")[1].split(":")[1]);

			int t1Score = 0;
			int t1GoalsFor = 0;
			int t1GoalsAgainst = 0;

			int t2Score = 0;
			int t2GoalsFor = 0;
			int t2GoalsAgainst = 0;

			if (s1 > s2){
				t1Score += 3;
			} else if (s2 > s1){
				t2Score += 3;
			} else{
				t1Score += 1;
				t2Score += 1;
			}
			
			t1GoalsFor += s1;
			t2GoalsAgainst += s1;
			
			t2GoalsFor += s2;
			t1GoalsAgainst += s2;
			
			Team team1 = getTeam(t1, teams);
			Team team2 = getTeam(t2, teams);
			
			team1.score += t1Score;
			team1.goalsFor += t1GoalsFor;
			team1.goalsAgains += t1GoalsAgainst;
			
			team2.score += t2Score;
			team2.goalsFor += t2GoalsFor;
			team2.goalsAgains += t2GoalsAgainst;
		}
		
		Collections.sort(teams, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				if(o1.score > o2.score)
					return -1;
				if(o1.score < o2.score)
					return 1;
				if(o1.goalsFor-o1.goalsAgains > o2.goalsFor - o2.goalsAgains)
					return -1;
				if(o1.goalsFor-o1.goalsAgains < o2.goalsFor - o2.goalsAgains)
					return 1;
				if(o1.goalsFor > o2.goalsFor)
					return -1;
				if(o1.goalsFor < o2.goalsFor)
					return 1;
				
				return 0;
			}
		});
		
		List<Team> winTeams = new ArrayList<Team>();
		
		for (int i = 0; i < nrOfTeams / 2; i++) {
			
			winTeams.add(teams.get(i));
		}
		
		Collections.sort(winTeams, new Comparator<Team>() {

			@Override
			public int compare(Team o1, Team o2) {
				return o1.name.compareTo(o2.name);
			}
		});
		
		
		for (Team team : winTeams) {
			System.out.println(team.name);
		}
		
	}

	private Team getTeam(String t1, List<Team> teams) {
		for (Team team : teams) {
			if(team.name.equals(t1)){
				return team;
			}
		}
		return null;
	}

	public static void main(String[] args) throws Exception {

		new One().solve();

	}
}
