public class TennisGame1 implements TennisGame {
    private static final String PLAYER_1 = "player1";
    private static final String PLAYER_2 = "player2";
    private Player player1;
    private Player player2;
    private Score score1;
    private Score score2;

    public TennisGame1(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.score1 = new Score(0);
        this.score2 = new Score(0);
    }
    public void wonPoint(Player player) {
        if (player.equals(player1))
            score1.incrementScore();
        else
            score2.incrementScore();
    }

    public String getScore() {
        String score = "";
        if (score1.equals(score2)) {
            score = handleEqualScores();
        } else if (score1.isAdvantage(score2)) {
            score = handleAdvantage();
        } else {
            score = handleNonEqualScores();
        }
        return score;
    }

    private String handleEqualScores() {
        switch (score1.getScoreValue()) {
            case 0:
                return "Love-All";
            case 1:
                return "Fifteen-All";
            case 2:
                return "Thirty-All";
            default:
                return "Deuce";
        }

    }

    private String handleAdvantage() {
        int minusResult = score1.getScoreValue() - score2.getScoreValue();
        if (minusResult == 1)
            return "Advantage " + player1.getName();
        else if (minusResult == -1)
            return "Advantage " + player2.getName();
        else if (minusResult >= 2)
            return "Win for " + player1.getName();
        else
            return "Win for " + player2.getName();
    }

    private String handleNonEqualScores() {
        String tempScoreDescription = "";
        for (int i = 1; i < 3; i++) {
            int tempScore = (i == 1) ? score1.getScoreValue() : score2.getScoreValue();
            tempScoreDescription += (i == 1) ? "" : "-";
            tempScoreDescription += getScoreDescription(tempScore);
        }
        return tempScoreDescription;
    }

    private String getScoreDescription(int tempScore) {
        switch (tempScore) {
            case 0:
                return "Love";
            case 1:
                return "Fifteen";
            case 2:
                return "Thirty";
            case 3:
                return "Forty";
            default:
                return "";
        }
    }
}

public class Player {
    private String name;
    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

public class Score {
    private int scoreValue;
    public Score(int scoreValue) {
        this.scoreValue = scoreValue;
    }

    public int getScoreValue() {
        return scoreValue;
    }

    public void incrementScore() {
        scoreValue++;
    }

    public boolean isAdvantage(Score opponentScore) {
        return Math.abs(scoreValue - opponentScore.getScoreValue()) == 1;
    }

} /*  */