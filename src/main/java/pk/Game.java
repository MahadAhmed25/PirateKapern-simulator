package pk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Foundation program for running the game, with number of games and each turn
 */

public class Game {

    public Game(){
    }

    public void play(String playerOne, String playerTwo, int numGames, String playerOneStrat, String playerTwoStrat){

        final Logger LOGGER = LogManager.getLogger(Game.class);

        if(ManageLog.toLog) LOGGER.trace("Welcome to Piraten Karpen Simulator!");
        pk.Player player1 = new pk.Player(playerOneStrat);
        pk.Player player2 = new pk.Player(playerTwoStrat);
        CardDeck cardDeck = new CardDeck();
        cardDeck.shuffleDeck();

        
        int player1Wins = 0;
        int player2Wins = 0;
        int draws = 0;
        for(int i=0; i<numGames; i++) {
            if(ManageLog.toLog) LOGGER.trace("\n===================GAME " + (i+1) + "=========================\n");
            while (player1.getScore() <= 6000 && player2.getScore() <= 6000) {
                
                if(ManageLog.toLog) LOGGER.trace("=====================Player 1 Turn==================");

                int score = player1.turn(cardDeck.drawCard());
                if(ManageLog.toLog) LOGGER.trace("Round Score: " + score);
                player1.updateScore(score);
                if(ManageLog.toLog) LOGGER.trace("||||||||| PLAYER1 TOTAL SCORE: " + player1.getScore() + " ||||||||");
                player1.newDice();

                if(ManageLog.toLog) LOGGER.trace("=====================Player 2 Turn==================");

                score = player2.turn(cardDeck.drawCard());
                if(ManageLog.toLog) LOGGER.trace("Round Score: " + score);
                player2.updateScore(score);
                if(ManageLog.toLog) LOGGER.trace("||||||||| PLAYER2 TOTAL SCORE: " + player2.getScore() + " ||||||||");
                player2.newDice();

            }

            if(player1.getScore() > player2.getScore()){
                player1Wins++;
                if(ManageLog.toLog){
                LOGGER.trace("GAME " + (i+1) + ": " + playerOne + " SCORE: " + player1.getScore() + " " + playerTwo
                    + " SCORE: " + player2.getScore() + " ----- PLAYER 1 WINS \n\n");
                }
                
            }

            else if(player1.getScore() < player2.getScore()){
                player2Wins++;
                if(ManageLog.toLog){
                LOGGER.trace("GAME " + (i+1) + ": " + playerOne + " SCORE: " + player1.getScore() + " " + playerTwo
                + " SCORE: " + player2.getScore() + " ----- PLAYER 2 WINS\n\n");
                }
                
            }

            else{
                draws++;
                if(ManageLog.toLog){
                LOGGER.trace("GAME " + (i+1) + ": " + playerOne + " SCORE: " + player1.getScore() + " " + playerTwo
                + " SCORE: " + player2.getScore() + " ----- DRAW \n\n");
                }
                
            }
            player1.resetScore();
            player2.resetScore();
            
        }

        double percentageOne = ((float) player1Wins/(float) (numGames-draws)) * 100;
        double percentageTwo = ((float) player2Wins/(float) (numGames-draws)) * 100;
        LOGGER.trace("PLAYER 1 WIN PERCENTAGE: " + Math.round(percentageOne) + "%");
        LOGGER.trace("PLAYER 2 WIN PERCENTAGE: " + Math.round(percentageTwo) + "%");
        if(ManageLog.toLog) LOGGER.trace("That's all folks!");



    }

}
