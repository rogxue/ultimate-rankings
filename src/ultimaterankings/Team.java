package ultimaterankings;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private String name;
    private final List<Game> gameList = new ArrayList<>();
    private double rating = 1000, ogRating;

    /**
     * Establishes team
     *
     * @param name
     */
    public Team(String name) {
        this.name = name;
    }

    /**
     * Returns name
     *
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Returns list of games
     *
     * @return list of games
     */
    public List<Game> getGameList() {
        return gameList;
    }

    /**
     * Returns team rating
     *
     * @return rating
     */
    public double getRating() {
        return rating;
    }

    /**
     * Sets team rating
     *
     * @param rating
     */
    public void setRating(double rating) {
        this.rating = rating;
    }

    /**
     * Adds amount to rating
     *
     * @param points
     */
    public void addRating(double points) {
        rating += points;
    }

    /**
     * Returns original rating, this is important when generating new ratings
     * after every game
     *
     * @return Original rating
     */
    public double getOgRating() {
        return ogRating;
    }

    /**
     * Sets original rating
     *
     * @param ogRating
     */
    public void setOgRating(double ogRating) {
        this.ogRating = ogRating;
    }

    /**
     * Prints team record in a nice string
     *
     * @return Team record
     */
    public String printRecord() {
        int win = 0;
        int total = gameList.size();
        for (Game g : gameList) {
            if (g.isWinner(this)) {
                win++;
            }
        }
        return "(" + win + "-" + (total - win) + ")";
    }

    public int getWins() {
        int win = 0;
        int total = gameList.size();
        for (Game g : gameList) {
            if (g.isWinner(this)) {
                win++;
            }
        }
        return win;
    }

    public int getLosses() {
        int win = 0;
        int total = gameList.size();
        for (Game g : gameList) {
            if (g.isWinner(this)) {
                win++;
            }
        }
        return total - win;
    }

    /**
     * Returns points the team has scored
     *
     * @return Points for
     */
    public int getPointsFor() {
        int pointsFor = 0;
        for (Game g : gameList) {
            pointsFor += g.getTeamScore(this);
        }
        return pointsFor;
    }

    /**
     * Returns points the team has been scored on
     *
     * @return Points against
     */
    public int getPointsAgainst() {
        int pointsAgainst = 0;
        for (Game g : gameList) {
            pointsAgainst += g.getOtherTeamScore(this);
        }
        return pointsAgainst;
    }

    /**
     * Returns point differential
     *
     * @return Point differential
     */
    public int getPointsDiff() {
        return getPointsFor() - getPointsAgainst();
    }

    public double getAvgPointsFor() {
        return (double) getPointsFor() / gameList.size();
    }

    public double getAvgPointsAgainst() {
        return (double) getPointsAgainst() / gameList.size();
    }

    /**
     * Returns point differential in nice string, probably unnecessary
     *
     * @return Point differential string
     */
    public String printPointsDiff() {
        return "(" + getPointsDiff() + ")";
    }
}
