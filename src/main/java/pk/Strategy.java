package pk;
import java.util.ArrayList;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Holds all the strategy methods for the random strategy, combo strategy
 * sea battle strategy and monkey business strategy.
 */

public class Strategy{

    //Strategy for random player
    static int randomStrategy(ArrayList<Faces> rolledDies){
        final Logger LOGGER = LogManager.getLogger(Game.class);
        if(ManageLog.toLog) LOGGER.trace("random strat");
        if(ManageLog.toLog) if(ManageLog.toLog) LOGGER.trace("INITIAL ROLLS: " + rolledDies);

        //gets the number of skulls (skullsAndScore[0]) + the rolled score (skullsAndScore[1])
        int[] skullsAndScore = Functions.status(rolledDies);
        if(ManageLog.toLog) if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skullsAndScore[0] + "             ROLLED SCORE: " + skullsAndScore[1]);

        //asks user to reroll aslong as skulls is not 3 or greater
        if(skullsAndScore[0] < 3){

            //reroll? picks between yes or no
            String input = Functions.yes_or_no();
            if(ManageLog.toLog) LOGGER.trace("DO YOU WANT TO REROLL?: " + input);

            if(!input.equalsIgnoreCase("y")){
                return skullsAndScore[1];
            }
        }
        else{
            return 0;          
        }

        //find the indexes where a skull is located
        ArrayList<Integer> skullidx = Functions.skullIndexs(rolledDies);

        //asks user which dice they want to reroll
        do{
            //how many dices to reroll (2 to (8 - #of skulls))
            Random random = new Random();
            int numRerolls = random.nextInt((9 - skullsAndScore[0]) - 2) + 2;
            if(ManageLog.toLog) LOGGER.trace("DICES TO REROLL: " + numRerolls);

            ArrayList<Integer> dices = new ArrayList<>();
            for(int i=0; i<numRerolls; i++){
                int input = random.nextInt(8);

                //refrains from letting cpu choose a dice thats a skull or previously chosen
                while(skullidx.contains(input) || dices.contains(input)){
                    input = random.nextInt(8);
                }
                dices.add(input);

                
                Dice myDice = new Dice();  
                rolledDies.set(input, myDice.roll());
            }

            skullsAndScore = Functions.status(rolledDies);
            if(ManageLog.toLog) LOGGER.trace("NEW SET OF ROLLS: " + rolledDies);
            if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skullsAndScore[0] + "             ROLLED SCORE: " + skullsAndScore[1]);
                if(skullsAndScore[0] >= 3){

                    return 0;
                }

            //chooses to reroll or not...
            String reroll = Functions.yes_or_no();
            if(ManageLog.toLog) LOGGER.trace("REROLL MORE DICE?: " + reroll);
            if(reroll.equalsIgnoreCase("n")){
                break;
            }

        }while(true);

        return skullsAndScore[1];

    }
    

    //Strategy for combo player 
    static int comboStrategy(ArrayList<Faces> rolledDies){
        final Logger LOGGER = LogManager.getLogger(Game.class);
        if(ManageLog.toLog) LOGGER.trace("combo strat");
        Faces maxComboFace = Functions.maxCombo(rolledDies);
        int[] skullsAndScore = Functions.status(rolledDies);
        ArrayList<Integer> skullidx = Functions.skullIndexs(rolledDies);

        if(ManageLog.toLog) LOGGER.trace("INITIAL ROLLS: " + rolledDies);
        if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skullsAndScore[0] + "             ROLLED SCORE: " + skullsAndScore[1]);

        if(skullsAndScore[0] >= 3) return 0;
        

        //if 2 skulls are rolled dont risk rerolling and return the score
        if(skullsAndScore[0] == 2 && skullsAndScore[1] >= 500) return skullsAndScore[1]; 

        //if a 6 combo is already rolled -- end turn
        if(Functions.countFace(maxComboFace, rolledDies) >= 6) return skullsAndScore[1];

        
        do{
            //how many dices to reroll (2 to (8 - #of skulls))
            Random random = new Random();
            int num = skullsAndScore[0] + Functions.countFace(maxComboFace, rolledDies) +
                      Functions.countFace(Faces.DIAMOND, rolledDies) + Functions.countFace(Faces.GOLD, rolledDies);

            if(num >= 7){
                return skullsAndScore[1];
            }
            int numRerolls = random.nextInt((9-num)-2)+2;

            if(ManageLog.toLog) LOGGER.trace("TARGETED COMBO: " + maxComboFace);
            if(ManageLog.toLog) LOGGER.trace("DICES TO REROLL: " + numRerolls);

            ArrayList<Integer> dices = new ArrayList<>();
            for(int i=0; i<numRerolls; i++){
                int input = random.nextInt(8);

                //refrains from letting cpu choose a dice thats a skull, previously chosen, max combo or gold/diamond
                while(skullidx.contains(input) || dices.contains(input) || rolledDies.get(input) == maxComboFace
                ||    rolledDies.get(input) == Faces.DIAMOND || rolledDies.get(input) == Faces.GOLD){
                    input = random.nextInt(8);
                }
                dices.add(input);

                
                Dice myDice = new Dice();  
                rolledDies.set(input, myDice.roll());
            }

            skullsAndScore = Functions.status(rolledDies);
            if(ManageLog.toLog) LOGGER.trace("NEW SET OF ROLLS: " + rolledDies);
            if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skullsAndScore[0] + "             ROLLED SCORE: " + skullsAndScore[1]);
            if(skullsAndScore[0] >= 3){
                return 0;
            }

            //chooses to reroll or not...

            //if 2 skulls are rolled dont risk rerolling and return the score
            if(skullsAndScore[0] == 2) return skullsAndScore[1]; 

            //if a 5 combo is rolled -- end turn
            if(Functions.countFace(maxComboFace, rolledDies) >= 5) return skullsAndScore[1];

            if(skullsAndScore[0] != 0){
            String reroll = Functions.yes_or_no();
            if(ManageLog.toLog) LOGGER.trace("REROLL MORE DICE?: " + reroll);
            if(reroll.equalsIgnoreCase("n")) break;
            }   

        }while(true);

        return skullsAndScore[1];

    }


    //Strategy for sea battle card
    static int seaBattleStrat(int numOfSabers, ArrayList<Faces> rolledDies){
        final Logger LOGGER = LogManager.getLogger(Game.class);
        if(ManageLog.toLog) LOGGER.trace("sea battle strat");
        if(ManageLog.toLog) if(ManageLog.toLog) LOGGER.trace("YOU ARE ENGANGED IN A SEA BATTLE COLLECT SABERS: " + numOfSabers);
        int[] skullsAndScore = Functions.status(rolledDies);
        ArrayList<Integer> skullidx = Functions.skullIndexs(rolledDies);

        if(ManageLog.toLog) if(ManageLog.toLog) LOGGER.trace("INITIAL ROLLS: " + rolledDies);
        if(ManageLog.toLog) if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skullsAndScore[0] + "             ROLLED SCORE: " + skullsAndScore[1]);
        int sabersRolled = Functions.countFace(Faces.SABER, rolledDies);

        if(skullsAndScore[0] >= 3){
            switch(numOfSabers){
                case 2:
                    return -300;
                case 3:
                    return -500;
                case 4:
                    return -1000;
                default:
                    return 0;
            }
        }

        if(sabersRolled == numOfSabers){
            switch(numOfSabers){
                case 2:
                    return 300+skullsAndScore[1];
                case 3:
                    return 500+skullsAndScore[1];
                case 4:
                    return 1000+skullsAndScore[1];
            }
        }

        do{
            Random random = new Random();
            int numRerolls = random.nextInt((9-skullsAndScore[0]-sabersRolled)-2)+2;

            if(ManageLog.toLog) LOGGER.trace("DICES TO REROLL: " + numRerolls);

            ArrayList<Integer> dices = new ArrayList<>();
            for(int i=0; i<numRerolls; i++){
                int input = random.nextInt(8);

                //refrains from letting cpu choose a dice thats a skull, previously chosen or is a saber
                while(skullidx.contains(input) || dices.contains(input) || rolledDies.get(input) == Faces.SABER){
                    input = random.nextInt(8);
                }
                dices.add(input);

                
                Dice myDice = new Dice();  
                rolledDies.set(input, myDice.roll());
            }

            skullsAndScore = Functions.status(rolledDies);
            sabersRolled = Functions.countFace(Faces.SABER, rolledDies);
            if(ManageLog.toLog) LOGGER.trace("NEW SET OF ROLLS: " + rolledDies);
            if(ManageLog.toLog) LOGGER.trace("SABERS ROLLED " + sabersRolled);
            if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skullsAndScore[0] + "             ROLLED SCORE: " + skullsAndScore[1]);
            if(skullsAndScore[0] >= 3){
                switch(numOfSabers){
                    case 2:
                        return -300;
                    case 3:
                        return -500;
                    case 4:
                        return -1000;
                    default:
                        return 0;
                }
            }

        }while(Functions.countFace(Faces.SABER, rolledDies) < numOfSabers);

        switch(numOfSabers){
            case 2:
                return 300+skullsAndScore[1];
            case 3:
                return 500+skullsAndScore[1];
            case 4:
                return 1000+skullsAndScore[1];
            default:
                return 0;
        }
            
    }

    //Strategy for monkey business card
    static int monkeybusinessStrategy(ArrayList<Faces> rolledDies){
        final Logger LOGGER = LogManager.getLogger(Game.class);
        if(ManageLog.toLog) LOGGER.trace("monkey business strat");
        if(ManageLog.toLog) LOGGER.trace("you drew the monkey business strategy! collect monkeys and parrots");
        int skulls = Functions.checkSkulls(rolledDies);
        int score = Functions.checkCoinsDiamonds(rolledDies) + Functions.identicalSets_monkeybusiness(rolledDies);
        ArrayList<Integer> skullidx = Functions.skullIndexs(rolledDies);

        if(ManageLog.toLog) LOGGER.trace("INITIAL ROLLS: " + rolledDies);
        if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skulls + "             ROLLED SCORE: " + score);

        if(skulls >= 3) return 0;
        

        //if 2 skulls are rolled dont risk rerolling and return the score
        if((skulls == 2 && score >= 400) || score >= 700 ) return score; 

        //if a 6 combo is already rolled -- end turn
        if(Functions.countFace(Faces.MONKEY, rolledDies) + Functions.countFace(Faces.PARROT, rolledDies) >= 6){
            return score;
        } 

        do{
            //how many dices to reroll (2 to (8 - #of skulls))
            Random random = new Random();
            int num = skulls + Functions.countFace(Faces.MONKEY, rolledDies) +
                      Functions.countFace(Faces.PARROT, rolledDies);

            if(num >= 7){
                return score;
            }
            int numRerolls = random.nextInt((9-num)-2)+2;

            if(ManageLog.toLog) LOGGER.trace("DICES TO REROLL: " + numRerolls);

            ArrayList<Integer> dices = new ArrayList<>();
            for(int i=0; i<numRerolls; i++){
                int input = random.nextInt(8);

                //refrains from letting cpu choose a dice thats a skull, previously chosen, max combo or gold/diamond
                while(skullidx.contains(input) || dices.contains(input) || rolledDies.get(input) == Faces.PARROT
                ||    rolledDies.get(input) == Faces.MONKEY){
                    input = random.nextInt(8);
                }
                dices.add(input);

                Dice myDice = new Dice();  
                rolledDies.set(input, myDice.roll());
            }

            skulls = Functions.checkSkulls(rolledDies);
            score = Functions.checkCoinsDiamonds(rolledDies) + Functions.identicalSets_monkeybusiness(rolledDies);
            if(ManageLog.toLog) LOGGER.trace("NEW SET OF ROLLS: " + rolledDies);
            if(ManageLog.toLog) LOGGER.trace("#OFSKULLS: " + skulls + "             ROLLED SCORE: " + score);
            if(skulls >= 3){
                return 0;
            }

            //chooses to reroll or not...

            //if 2 skulls are rolled dont risk rerolling and return the score
            if(skulls == 2) return score; 

            //if a 5 combo is rolled -- end turn
            if(Functions.countFace(Faces.PARROT, rolledDies) + Functions.countFace(Faces.MONKEY, rolledDies) >= 7){
                return score;
            } 

            String reroll = Functions.yes_or_no();
            if(ManageLog.toLog) LOGGER.trace("REROLL MORE DICE?: " + reroll);
            if(reroll.equalsIgnoreCase("n")) break;

        }while(true);

        return score;


    }

}