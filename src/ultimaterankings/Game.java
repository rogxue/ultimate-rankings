package ultimaterankings;

public class Game {

    private final Team team1, team2;
    private final int score1, score2;
    private final double rating;

    /**
     * Games constructor
     *
     * @param team1
     * @param team2
     * @param score1
     * @param score2
     */
    public Game(Team team1, Team team2, int score1, int score2) {
        this.team1 = team1;
        this.team2 = team2;
        this.score1 = score1;
        this.score2 = score2;

        int lose = Math.min(score1, score2);    // losing score
        int win = Math.max(score1, score2);     // winning score

        //
        // SUPER AWESOME USA ULTIMATE ALGORITHM
        //
//        double r = (double) lose / (win - 1.0);
        double r = (double) lose / win * 15 / 14;

        if (lose == win) {
            rating = 0;
        } else {
            double temp = 125 + 475 * (Math.sin(Math.min(1.0, (1 - r) / 0.5) * 0.4 * Math.PI) / Math.sin(0.4 * Math.PI));
            rating = temp * (1 + 0.003 * (team1.getGameList().size() + team2.getGameList().size()));
//            System.out.println(rating);
        }
    }

    /**
     * Gets specific team
     *
     * @param t
     * @return Team
     */
    public Team getTeam(Team t) {
        return team1.equals(t) ? team1 : team2;
    }

    /**
     * Gets opposing team
     *
     * @param t
     * @return Opposing team
     */
    public Team getOtherTeam(Team t) {
        return team1.equals(t) ? team2 : team1;
    }

    /**
     * Gets team score
     *
     * @param t
     * @return Team score
     */
    public int getTeamScore(Team t) {
        return team1.equals(t) ? score1 : score2;
    }

    /**
     * Gets opposing team score
     *
     * @param t
     * @return Opposing team score
     */
    public int getOtherTeamScore(Team t) {
        return team1.equals(t) ? score2 : score1;
    }

    /**
     * Gets team rating from game
     *
     * @param t
     * @return Rating
     */
    public double getRating(Team t) {
        return isWinner(t) ? rating : -1 * rating;
    }

    /**
     * Gets game results in nice looking string
     *
     * @return Game results
     */
    public String printResult() {
        return team1.getName() + " " + score1 + " - " + score2 + " " + team2.getName();
    }

    /**
     * Returns game results with specific team first
     *
     * @param teamName
     * @return Game results
     */
    public String printHomeTeamFirst(String teamName) {
        if (team1.getName().equalsIgnoreCase(teamName)) {
            return team1.getName() + " " + score1 + " - " + score2 + " " + team2.getName();
        } else if (team2.getName().equalsIgnoreCase(teamName)) {
            return team2.getName() + " " + score2 + " - " + score1 + " " + team1.getName();
        } else {
            return printResult();
        }
    }

    /**
     * Returns game results with winning team first
     *
     * @return Game results
     */
    public String printWinnerFirst() {
        if (score1 > score2) {
            return team1.getName() + " " + score1 + " - " + score2 + " " + team2.getName();
        } else {
            return team2.getName() + " " + score2 + " - " + score1 + " " + team1.getName();
        }
    }

    /**
     * Checks if given team is winner
     *
     * @param t
     * @return Winner?
     */
    public boolean isWinner(Team t) {
        return getWinner().equals(t);
    }

    /**
     * Returns winning team
     *
     * @return Winner
     */
    public Team getWinner() {
        return (score1 > score2) ? team1 : team2;
    }

    /**
     * Returns losing team
     *
     * @return Loser
     */
    public Team getLoser() {
        return (score1 > score2) ? team2 : team1;
    }

    /**
     * Checks if a game is a blowout based on point differential and team
     * ratings
     *
     * @return Is game a blowout?
     */
    public boolean isBlowout() {
        int lose = Math.min(score1, score2);
        int win = Math.max(score1, score2);
        return win > 2 * lose && (getWinner().getOgRating() - getLoser().getOgRating()) > 500;
    }
}
