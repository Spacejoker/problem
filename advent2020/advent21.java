import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class advent21 {

  public static void main(String[] args) throws Exception {
    new advent21(true);
    new advent21(false);
  }

  public advent21(boolean test) throws Exception {
    run(test);
  }

  private void run(boolean test) throws Exception {
    String[] input = readLines(test);
    long t0 = System.currentTimeMillis();
    long solutionA = solveFirst(input);
    String solutionB = solveSecond(input);
    long t1 = System.currentTimeMillis();

    System.out.printf("Answer 1: %d\nAnswer 2: %s\n", solutionA, solutionB);
    System.out.printf("Time: %dms\n", t1 - t0);
  }

  class Recipe {
    Set<String> ingredient;
    Set<String> allergen;

    public Recipe(Set<String> ingredient, Set<String> allergen) {
      this.ingredient = ingredient;
      this.allergen = allergen;
    }

    @Override
    public String toString() {
      return "Recipe [allergen=" + allergen + ", ingredient=" + ingredient + "]";
    }

  }

  private long solveFirst(String[] input) {
    Recipe[] recs = new Recipe[input.length];
    Set<String> allergenUnion = new HashSet<>();
    Set<String> possibleIngredients = new HashSet<>();
    Set<String> ingredientUnion = new HashSet<>();

    for (int i = 0; i < input.length; i++) {
      String s = input[i];
      s = s.replace("(", "").replace(")", "");
      String[] split = s.split("contains");
      String[] ingredients = split[0].trim().split(" ");
      String[] allergens = split[1].trim().split(", ");
      Set<String> ing = new HashSet<>();
      Set<String> all = new HashSet<>();
      for (String k : ingredients) {
        possibleIngredients.add(k);
        ingredientUnion.add(k);
        ing.add(k);
      }
      for (String k : allergens) {
        allergenUnion.add(k);
        all.add(k);
      }
      recs[i] = new Recipe(ing, all);
    }
    Map<String, List<String>> allegrenCausedByCandidates = new HashMap<>();

    for (String allergen : allergenUnion) {
      Set<String> possibleIngredientSource = new HashSet<>();
      possibleIngredientSource.addAll(ingredientUnion);
      for (Recipe rec : recs) {
        if (rec.allergen.contains(allergen)) {
          List<String> itemsToRemove = new ArrayList<>();
          for (String cand : possibleIngredientSource) {
            if (!rec.ingredient.contains(cand)) {
              itemsToRemove.add(cand);
            }
          }
          possibleIngredientSource.removeAll(itemsToRemove);
        }
      }
      List<String> res = new ArrayList<>();
      for (String p : possibleIngredientSource) {
        res.add(p);
        possibleIngredients.remove(p);
      }
      allegrenCausedByCandidates.put(allergen, res);
    }

    int ans = 0;
    for (Recipe rec : recs) {
      for (String p : possibleIngredients) {
        if (rec.ingredient.contains(p))
          ans++;
      }
    }

    return ans;
  }

  private String solveSecond(String[] input) {
    Recipe[] recs = new Recipe[input.length];
    Set<String> allergenUnion = new HashSet<>();
    Set<String> possibleIngredients = new HashSet<>();
    Set<String> ingredientUnion = new HashSet<>();

    for (int i = 0; i < input.length; i++) {
      String s = input[i];
      s = s.replace("(", "").replace(")", "");
      String[] split = s.split("contains");
      String[] ingredients = split[0].trim().split(" ");
      String[] allergens = split[1].trim().split(", ");
      Set<String> ing = new HashSet<>();
      Set<String> all = new HashSet<>();
      for (String k : ingredients) {
        possibleIngredients.add(k);
        ingredientUnion.add(k);
        ing.add(k);
      }
      for (String k : allergens) {
        allergenUnion.add(k);
        all.add(k);
      }
      recs[i] = new Recipe(ing, all);
    }
    Map<String, List<String>> allegrenCausedByCandidates = new HashMap<>();

    for (String allergen : allergenUnion) {
      Set<String> possibleIngredientSource = new HashSet<>();
      possibleIngredientSource.addAll(ingredientUnion);
      for (Recipe rec : recs) {
        if (rec.allergen.contains(allergen)) {
          List<String> itemsToRemove = new ArrayList<>();
          for (String cand : possibleIngredientSource) {
            if (!rec.ingredient.contains(cand)) {
              itemsToRemove.add(cand);
            }
          }
          possibleIngredientSource.removeAll(itemsToRemove);
        }
      }
      List<String> res = new ArrayList<>();
      for (String p : possibleIngredientSource) {
        res.add(p);
        possibleIngredients.remove(p);
      }
      allegrenCausedByCandidates.put(allergen, res);
    }

    List<Pair> pairs = new ArrayList<>();

    while (pairs.size() < allegrenCausedByCandidates.keySet().size()) {

      String ing = "", all = "";
      for (Entry<String, List<String>> entry : allegrenCausedByCandidates.entrySet()) {
        if (entry.getValue().size() == 1) {
          all = entry.getKey();
          ing = entry.getValue().get(0);
          break;
        }
      }
      pairs.add(new Pair(ing, all));

      for (Entry<String, List<String>> entry : allegrenCausedByCandidates.entrySet()) {
        entry.getValue().remove(ing);
      }
    }

    Collections.sort(pairs, new Comparator<Pair>(){

		@Override
		public int compare(advent21.Pair a, advent21.Pair b) {
			return a.allergen.compareTo(b.allergen);
		}
      
    });
    String ret = "";
    for( int i= 0; i < pairs.size(); i++ ){
      ret += pairs.get(i).ingredient;
      if ( i < pairs.size()-1) ret += ",";
    }

    return ret;

  }

  class Pair {
    String ingredient, allergen;

    public Pair(String ingredient, String allergen) {
      this.ingredient = ingredient;
      this.allergen = allergen;
    }

  }

	String[] readLines(boolean test) throws Exception {
		String filename = test ? "advent2020/test.txt" : "advent2020/input.txt";
		BufferedReader in = new BufferedReader(new FileReader(filename));
		List<String> values = new ArrayList<>();
		String s;
		while ((s = in.readLine()) != null) {
			values.add(s);
		}
		in.close();
		return values.stream().toArray(String[]::new);
	}
}
