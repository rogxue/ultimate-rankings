package ultimaterankings;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RatingCalculator {

    private List<Team> teamList = new ArrayList<>();
    private DecimalFormat fmt = new DecimalFormat("0.0000");

    /**
     * Rating calculator, where the magic happens
     *
     * @param file
     * @throws InterruptedException
     */
    public RatingCalculator(String file) throws InterruptedException {

        try {
            Team team1, team2;
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("###")) {
                    continue;
                }
                //
                // Parses through score data, data is formatted in this order
                // Team 1 score
                // Team 1 name
                // Team 2 score
                // Team 2 name
                //
                int score1 = Integer.parseInt(line);
                String teamName1 = br.readLine();
                int score2 = Integer.parseInt(br.readLine());
                String teamName2 = br.readLine();
                //
                // Adds teams to team list if they weren't there already
                //
                if (checkIfTeamExists(teamName1)) {
                    team1 = getTeam(teamName1);
                } else {
                    team1 = new Team(teamName1);
                    teamList.add(team1);
                }
                if (checkIfTeamExists(teamName2)) {
                    team2 = getTeam(teamName2);
                } else {
                    team2 = new Team(teamName2);
                    teamList.add(team2);
                }
                //
                // Adds games to teams' resume
                //
                Game g = new Game(team1, team2, score1, score2);
                team1.getGameList().add(g);
                team2.getGameList().add(g);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //
        // Iterates through 40 generations of ratings (I forgot what number USAU
        // uses, but any greater and nothing really changes, though the data I've
        // been working with only contains like 12 teams max)
        //
        for (int j = 0; j < 80; j++) {
//            System.out.println("\tGENERATION " + (j + 1));
            for (Team t : teamList) {
                //
                // OgRating holds the "old" rating as new ratings are calculated
                // Every team starts off with a default rating of 1000
                //
                t.setOgRating(t.getRating());
                t.setRating(0);
            }
            for (Team t : teamList) {
                int gameCount = t.getGameList().size();
//                System.out.println("\t" + t.getName() + " Rating: " + t.getOgRating());
                //
                // I'll explain logic later
                // For now, all you need to know is that blowouts don't count
                // for or against teams
                //
                for (Game g : t.getGameList()) {
                    if (!g.isBlowout()) {
                        t.addRating(g.getOtherTeam(t).getOgRating() + g.getRating(t));
//                        System.out.println(g.printHomeTeamFirst(t.getName()) + " " + g.getRating(t) + " " + t.getRating());
                    } else {
//                        System.out.println(g.printHomeTeamFirst(t.getName()) + " " + g.getRating(t) + " " + t.getRating() + " (BLOWOUT)");
                        gameCount--;
                    }
                }
                //
                // Calculates new ratings, ratings don't change if 0 eligible games
                //
                if (gameCount != 0) {
                    t.setRating(t.getRating() / gameCount);
                } else {
                    t.setRating(t.getOgRating());
                }
//                System.out.println("\t" + t.getName() + " New Rating: " + t.getRating());
            }
        }
    }

    /**
     * Check if team already exists in team list based on string
     *
     * @param teamName
     * @return Does team exist in team list?
     */
    private boolean checkIfTeamExists(String teamName) {
        for (Team t : teamList) {
            if (t.getName().equalsIgnoreCase(teamName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns team from team list
     *
     * @param teamName
     * @return team
     */
    private Team getTeam(String teamName) {
        for (Team t : teamList) {
            if (t.getName().equalsIgnoreCase(teamName)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Prints rankings.
     */
    public void printResults() {
        Collections.sort(teamList, (Team c1, Team c2) -> Double.compare(c2.getRating(), c1.getRating()));
        int i = 1;
        System.out.println("\n\tFINAL RESULTS");
        for (Team t : teamList) {
            System.out.println(i++ + ". " + t.getName() + " " + t.printRecord() + " " + t.printPointsDiff() + " " + fmt.format(t.getRating()));
        }
    }
}
