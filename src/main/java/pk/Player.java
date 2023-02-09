package pk;
import java.util.ArrayList;

/*
 * Player class to create the 2 players who take part in the game
 */

public class Player {
    public int score;
    public static ArrayList<Faces> rolledDies;
    public boolean strategy;

    public Player(String strat){
        this.score = 0;

        if(strat.equalsIgnoreCase("combo")){
            this.strategy = true;
        }
        else{
            this.strategy = false;
        }

        rolledDies = Functions.rollEight(); 
    }

    public void updateScore(int roundScore){
        score += roundScore;
    }

    public void resetScore(){
        score = 0;
    }

    public void newDice(){
        rolledDies = Functions.rollEight();
    }


    public int getScore(){
        return score;
    }

    public ArrayList<Faces> getDice(){
        return rolledDies;
    }

    public int turn(Cards selectedCard){

        if(strategy){
            if(selectedCard == Cards.NOP) return Strategy.comboStrategy(rolledDies);

            else if (selectedCard == Cards.MONKEYBUSINESS) return Strategy.monkeybusinessStrategy(rolledDies);

            else{
                switch(selectedCard){
                    case SEABATTLE2:
                        return Strategy.seaBattleStrat(2, rolledDies);
                    case SEABATTLE3:
                        return Strategy.seaBattleStrat(3, rolledDies);
                    case SEABATTLE4:
                        return Strategy.seaBattleStrat(4, rolledDies);
                    default:
                        return 0;
                }
            }
        }

        else return Strategy.randomStrategy(rolledDies);
        
    }
    
}
