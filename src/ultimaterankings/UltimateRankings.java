
package ultimaterankings;

import java.io.File;
import java.util.Scanner;

public class UltimateRankings {

    public static void main(String[] args) throws Exception {
        
        Scanner scan = new Scanner(System.in);
        //
        // Get file with game data.
        //
//        System.out.print("Enter file containing game data: ");
//        String file = scan.nextLine();
//        File tweetFile = new File(file);
//        if (!tweetFile.exists()) {
//            System.out.println("File does not exist.");
//            return;
//        }
        RatingCalculator ratingCalculator = new RatingCalculator("C:/massey.txt");
        ratingCalculator.printResults();
    }
}
