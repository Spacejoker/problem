import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class MyBot {

	// The DoTurn function is where your code goes. The PlanetWars object
	// contains the state of the game, including information about all planets
	// and fleets that currently exist. Inside this function, you issue orders
	// using the pw.IssueOrder() function. For example, to send 10 ships from
	// planet 3 to planet 8, you would say pw.IssueOrder(3, 8, 10).

	// There is already a basic strategy in place here. You can use it as a
	// starting point, or you can throw it out entirely and replace it with
	// your own. Check out the tutorials and articles on the contest website at
	// http://www.ai-contest.com/resources.

	
	
	static class Move {
		int fromId;
		int toId;
		int qty;
		double weight = 0;
		
		public Move(int fromId, int toId, int qty) {
			super();
			this.fromId = fromId;
			this.toId = toId;
			this.qty = qty;
		}

		public String toString() {
			return "Move [fromId=" + fromId + ", toId=" + toId + ", qty=" + qty
					+ ", weight=" + weight + "]";
		}
		
	}
	
	public static void DoTurn(PlanetWars pw) {

		long start = System.currentTimeMillis();
		try{
//			for (int i = 0; i < 5; i++) {
//				BufferedWriter writer = new BufferedWriter(new FileWriter(new File("debuginfo.txt")));
				List<Move> moves = getMoves(pw);
				
				if(moves == null || moves.size() == 0){
					return;
				}
				
				Move move = moves.get(0);
				if( move.weight > 0 &&  move.fromId > 0 && move.toId > 0 && move.qty > 0){
					pw.IssueOrder(move.fromId, move.toId, move.qty);	
				}
				
				
//				for (int i = 0; i < 3; i++) {
//					if(moves.size() > i ) {
//						Move move = moves.get(i);
//						
//						if( move.weight > 0 &&  move.fromId > 0 && move.toId > 0 && move.qty > 0){
//							pw.IssueOrder(move.fromId, move.toId, move.qty);			
//						}	
//					}
//				}

//				writer.write("time: " + (System.currentTimeMillis()-start));
		} catch(Exception e) {
			
		}

//		int maxMoves = 1;
		
		
//		int moves = maxMoves;
		
//		while(nextMove != null && moves > 0){
//			moves --;
		
//			nextMove = getMoves(pw);
//		}		
		
//		//OLD ALGO
//		// find horizon for the playtime (what should be my break even goal?
//
//		// for each of my planets consider which conquer that is the best, and
//		// how good it is
//		for (Planet myPlanet : pw.MyPlanets()) {
//
//			double bestDist = Double.MAX_VALUE;
//			Planet closestPlanet = null;
//			// find the closest planet i can afford
//			for (Planet planet : pw.Planets()) {
//				if (planet.Owner() == 1) {
//					continue;
//				}
//				int distance = pw.Distance(myPlanet.PlanetID(),
//						planet.PlanetID());
//
//				if (distance < bestDist) {
//					bestDist = distance;
//					closestPlanet = planet;
//				}
//			}
//
//			if (closestPlanet != null) {
//				int cost = costOfPlanet(closestPlanet, myPlanet, pw);
//				if (cost < myPlanet.NumShips()) {
//					// send fleet if we have:)
//					pw.IssueOrder(myPlanet, closestPlanet, cost + 1);
//				}
//			}
//		}

	}


	private static List<Move> getMoves(PlanetWars pw) throws Exception {
		
//		BufferedWriter writer = new BufferedWriter(new FileWriter(new File("debuginfo.txt")));
		
		
		
		List<Move> possibleMoves = new ArrayList<Move>();
		
//		writer.write("1possible movessize: " + possibleMoves.size());
//		writer.flush();
		double maxDist = 0;
		double maxGrowth = 0;
		for (Planet p1 : pw.Planets()) {
			if(p1.GrowthRate()> maxGrowth){
				maxGrowth = p1.GrowthRate();
			}
			for (Planet p2: pw.Planets()) {
				if(pw.Distance(p1, p2)> maxDist){
					maxDist = pw.Distance(p1, p2);
				}
			}
		}
		//params
//		double tresholdForPercentegeToBeACheapPlanet = 0.5;
		
		int enemyIncreaseRate = getIncreasRate(pw.EnemyPlanets());
		int myIncreaseRate = getIncreasRate(pw.MyPlanets());

		if (enemyIncreaseRate < myIncreaseRate && pw.EnemyFleets().size() == 0) {
			return null; // im in the lead, just wait
		}

		// Calculate base data for all planets
		
		//Max value for asingle planet
		double maxValForPlanets = 0;
		for (Planet planet : pw.Planets()) {
			if(planet.getNumShips()> maxValForPlanets){
				maxValForPlanets = planet.getNumShips();
			}
		}
		
		// go though my current planets and see which are in front, also find the mean val and set expected unit count after all fleets:
		double meanVal = 0;
		
		for (Planet myPlanet : pw.MyPlanets()) {
			double totEnemyDist = 0;
			double totFriendlyDist = 0;
			
			for (Planet otherPlanet : pw.Planets()) {
				if (myPlanet.getPlanetID() == otherPlanet.getPlanetID()) {
					continue;
				}
				if(otherPlanet.getOwner() == 2){
					totEnemyDist += pw.Distance(myPlanet, otherPlanet);
				}
				if(otherPlanet.getOwner() == 1){
					totFriendlyDist += pw.Distance(myPlanet, otherPlanet);
				}
			}
			
			if(totFriendlyDist + totEnemyDist != 0){
				double offence = (totFriendlyDist) / (totFriendlyDist + totEnemyDist);
				myPlanet.setOffencePosition(offence);
				meanVal += offence;
			} else {
				myPlanet.setOffencePosition(0);
			}
		}
		
		//set expected unit count for all planets
		for (Planet myPlanet : pw.Planets()) {
			myPlanet.setExpectedUnitCount(myPlanet.getNumShips());
			for (Fleet fleet: pw.Fleets()) {
				if(fleet.DestinationPlanet() == myPlanet.PlanetID()){
					int mod = 0;
					if(fleet.Owner() == myPlanet.Owner()){
						mod = fleet.NumShips();
					} else {
						mod = fleet.NumShips()*-1;
					}
					myPlanet.modExpectedUnitCount(mod);
				}
			}
		}
		
		if(pw.MyPlanets().size() > 0){
			meanVal /= pw.MyPlanets().size();	
		}
		
		//Look for the cheapest neutral planets
		int minCost = Integer.MAX_VALUE;
//		Planet cheapestPlanet = null;
//		
//		outer:for (Planet planet : pw.NeutralPlanets()) {
//			if(planet.NumShips() < minCost){
//				boolean isAttacked = isBeingAttackedByMe(planet, pw);
//				if(isAttacked){
//					continue outer;	
//				}
//				cheapestPlanet = planet;
//				minCost = planet.NumShips();
//			}
//		}
		
		
//		if(cheapestPlanet != null ) {//&& cheapestPlanet.getNumShips() <= tresholdForPercentegeToBeACheapPlanet * maxValForPlanets){
//			//we got a cheap planet, which is the best buyer?
//			
//			int minDist = Integer.MAX_VALUE;
//			Planet bestPlan = null;
//			
//			for (Planet myPlanet : pw.MyPlanets()) {
//				int dist = pw.Distance(myPlanet, cheapestPlanet);
//				if(myPlanet.getNumShips() > cheapestPlanet.getNumShips()*2 && dist < minDist){
//					minDist = dist;
//					bestPlan = myPlanet;
//				}
//			}
//			
//			//Suggest the move
//			
//			
//		}
		
		//TODO: WORK ON THIS EXP STRATEGY
		//find cheap planets
		for (Planet neuPlanet : pw.NeutralPlanets()) {
		
			//find well suited candidates
			for (Planet myPlanet : pw.MyPlanets()) {
				
				double distTerm = distance(myPlanet, neuPlanet) / maxDist/1000;
				double growth = ((double)neuPlanet.GrowthRate())/maxGrowth;
//				double myNrs = ((double)myPlanet.getNumShips()) /maxValForPlanets;
				double cost = neuPlanet.getNumShips()/myPlanet.NumShips();
				double weight = growth + cost - distTerm + 1;
				
				while(weight < 0){
					weight += 0.1;
				}
				
				if(myPlanet.getNumShips() > neuPlanet.getNumShips()){
					Move move = new Move(myPlanet.getPlanetID(), neuPlanet.getPlanetID(), neuPlanet.getExpectedUnitCount()+1);
					move.weight = weight;
					possibleMoves.add(move);
				}
			}
			
			
//			double posTerm = ((double)bestPlan.NumShips())/maxValForPlanets;
//			
//			move.weight = 0.4- negTerm; //0.4 seems to bea good nr here
			
		}
		
		
		for (Planet myPlanet : pw.MyPlanets()) {
			
			for (Planet enemyPlanet : pw.EnemyPlanets()) {
				
				int dist = pw.Distance(myPlanet, enemyPlanet);
				
				int costToConquer = dist * enemyPlanet.getGrowthRate() + enemyPlanet.getExpectedUnitCount() + 1; 
				
				if(myPlanet.getExpectedUnitCount() > costToConquer && myPlanet.NumShips() > costToConquer){
					Move move = new Move(myPlanet.PlanetID(), enemyPlanet.PlanetID(), costToConquer);
					
					//distance is really important, score will be 0-5;
					int distance = pw.Distance(myPlanet, enemyPlanet);
					double relDist = ((double)distance) / maxDist;
					double weight = (1-relDist)*10;
					weight *= ((double)enemyPlanet.GrowthRate()) / maxGrowth; 
					
					move.weight = weight;
					
					possibleMoves.add(move);
				}
			}
			//consider a supporting or defensive move, find a weak offensive node and moveout!
			for (Planet friendPlanet : pw.MyPlanets()) {
				if(friendPlanet.PlanetID() != myPlanet.PlanetID()){
					
					if(friendPlanet.getExpectedUnitCount() < 0){
						
						if(myPlanet.getNumShips() >= myPlanet.getExpectedUnitCount()){
							int diff = friendPlanet.getExpectedUnitCount() + myPlanet.getExpectedUnitCount();
							
							if(diff > 0){ // i could save it, lets check if it is close
								int distance = pw.Distance(myPlanet, friendPlanet);
								double relDist = distance / maxDist;
								
								Move move = new Move(myPlanet.PlanetID(), friendPlanet.PlanetID(), friendPlanet.getExpectedUnitCount() + 1);
								move.weight = (1-relDist) * 3; //up to three
								move.weight *= ((double)friendPlanet.GrowthRate()) / maxGrowth;
								possibleMoves.add(move);
							}
						}
					}
					
					if(friendPlanet.getOffencePosition() > myPlanet.getOffencePosition() && myPlanet.NumShips() > 10 && myPlanet.NumShips() > friendPlanet.NumShips()*2){
						//send half ships to support a fellow planet
						Move move = new Move(myPlanet.PlanetID(), friendPlanet.PlanetID(), myPlanet.NumShips() *2/ 3);
						
						//score should be 0-1 or so
						move.weight = friendPlanet.getOffencePosition() - myPlanet.getOffencePosition();
						move.weight *= ((double)friendPlanet.GrowthRate()) / maxGrowth; 
						possibleMoves.add(move);
					}
				}
			}
		}
		
		//test move
		Move move = new Move(pw.MyPlanets().get(0).PlanetID(), pw.NeutralPlanets().get(0).PlanetID(), 1);
		move.weight = 0.1;
		possibleMoves.add(move);
		
		//Go through the possible moves an sort them in order from best to worst, return the best move!
		Collections.sort(possibleMoves, new Comparator<Move>() {

			public int compare(Move o1, Move o2) {
				return o1.weight > o2.weight ? -1 : 1;
			}
		});
		
//		Set<Integer> fromIds = new HashSet<Integer>();
//		Set<Integer> toIds = new HashSet<Integer>();
//		
//		for (Iterator<Move> iter = possibleMoves.iterator(); iter.hasNext();) {
//			Move move = iter.next();
//			if(fromIds.contains(move.fromId)){
//				iter.remove();
//			} else {
//				fromIds.add(move.fromId);
//			}
//			
//			if(toIds.contains(move.toId)){
//				iter.remove();
//			} else {
//				toIds.add(move.toId);
//			}
//		}
		
		return possibleMoves;
	}

	private static boolean isBeingAttackedByMe(Planet planet, PlanetWars pw) {
		for (Fleet f : pw.MyFleets()) {
			if(f.DestinationPlanet() == planet.PlanetID()){
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
		String line = "";
		String message = "";
		int c;
		try {
			while ((c = System.in.read()) >= 0) {
				switch (c) {
				case '\n':
					if (line.equals("go")) {
						PlanetWars pw = new PlanetWars(message);
						DoTurn(pw);
						pw.FinishTurn();
						message = "";
					} else {
						message += line + "\n";
					}
					line = "";
					break;
				default:
					line += (char) c;
					break;
				}
			}
		} catch (Exception e) {
			// Owned.
		}
	}

	/**
	 * sum the growth rate of all planets
	 */
	public static int getIncreasRate(List<Planet> planets) {
		int x = 0;
		for (Planet planet : planets) {
			x += planet.GrowthRate();
		}
		return x;
	}

	static double distance(Planet source, Planet destination) {
		double dx = source.X() - destination.X();
		double dy = source.Y() - destination.Y();
		return Math.sqrt(dx * dx + dy * dy);
	}
}
