
package ultimaterankings;

public class UltimateRankings {

    public static void main(String[] args) throws Exception {
        
        String file = System.getProperty("user.dir") + "\\data\\" + "2016d1b.txt";
        RatingCalculator ratingCalculator = new RatingCalculator(file);
        
    }
}
