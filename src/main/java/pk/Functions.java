package pk;
import java.util.ArrayList;
import java.util.Random;

/*
 * Holds all the functions for the pirate kapern game that may be needed through out the simulator
 */

public class Functions {

    //roll eight dices
    static ArrayList<Faces> rollEight(){
        ArrayList<Faces> rolledDices = new ArrayList<Faces>();

        for(int i = 0; i<8; i++){
            //System.out.print("I'm rolling a dice: ");
            Dice myDice = new Dice();
            Faces roll = myDice.roll();
            //System.out.println(roll);
            rolledDices.add(roll);
        }

        return rolledDices;
    }

    //count # of skulls
    static int checkSkulls(ArrayList<Faces> list){
        int count = 0; 
        for(int i=0; i<list.size(); i++){
            if(list.get(i) == Faces.SKULL){
                count++;
            }
        }
        return count;
    }

    //take a face as a parameter and count the # of those faces in a given set of rolls
    static int countFace(Faces face, ArrayList<Faces> list){
        int count = 0; 
        for(int i=0; i<list.size(); i++){
            if(list.get(i) == face){
                count++;
            }
        }
        return count;
    }

    //counts the score by checking # of coinds and diamonds
    static int checkCoinsDiamonds(ArrayList<Faces> list){
        int count = 0; 
        for(int i=0; i<list.size(); i++){
            if(list.get(i) == Faces.DIAMOND || list.get(i) == Faces.GOLD ){
                count++;
            }
        }
        return count*100;
    }
    
    //finds the indexs where a skull is located
    static ArrayList<Integer> skullIndexs(ArrayList<Faces> list){
        ArrayList<Integer> skullidx = new ArrayList<>();

        for(int i=0; i<list.size(); i++){
            if(list.get(i) == Faces.SKULL){
                skullidx.add(i);
            }
        }
        return skullidx;
    }

    //gets the num of skulls and the rolled score
    static int[] status(ArrayList<Faces> list){

        int skulls = checkSkulls(list); //checks how many skulls are rolled
        int score = checkCoinsDiamonds(list) + identicalSets(list); //calculates the rolled score

        int[] status = {skulls, score};

        return status; 
    }

    //random input for asking cpu "yes" or "no"
    static String yes_or_no(){
        String s = "yn";
        Random random = new Random();
        int index = random.nextInt(s.length());
        char inputc = s.charAt(index);
        String input = Character.toString(inputc);
        return input;
    }

    //counts every occurence of a face in a given set of rolls
    static int[] faceCounter(ArrayList<Faces> list){
        int monkeyCount = 0; 
        int parrotCount = 0; 
        int goldCount = 0; 
        int diamondCount = 0; 
        int saberCount = 0; 
        for(int i=0; i<list.size(); i++){
            switch(list.get(i)){
                case MONKEY:
                    monkeyCount++;
                    break;
                case PARROT:
                    parrotCount++;
                    break;
                case GOLD:
                    goldCount++;
                    break;
                case DIAMOND:
                    diamondCount++;
                    break;
                case SABER:
                    saberCount++;
                    break;
                default:
                    break;
            }
        }
        int[] faceList = {monkeyCount, parrotCount, goldCount, diamondCount, saberCount};
        return faceList;
    }

    //computes the score of identical sets of faces
    static int identicalSets(ArrayList<Faces> list){
        int totalScore = 0;
        
        int[] countedFaces = faceCounter(list);

        totalScore += identicalSetsExtend(countedFaces[0]);
        totalScore += identicalSetsExtend(countedFaces[1]);
        totalScore += identicalSetsExtend(countedFaces[2]);
        totalScore += identicalSetsExtend(countedFaces[3]);
        totalScore += identicalSetsExtend(countedFaces[4]);

        return totalScore;

    }

    //computes the score if identical sets of faces with parrot and monkey being the same symbol
    static int identicalSets_monkeybusiness(ArrayList<Faces> list){
        int totalScore = 0;
        
        int[] countedFaces = faceCounter(list);

        totalScore += identicalSetsExtend(countedFaces[0]+countedFaces[1]);
        totalScore += identicalSetsExtend(countedFaces[2]);
        totalScore += identicalSetsExtend(countedFaces[3]);
        totalScore += identicalSetsExtend(countedFaces[4]);

        return totalScore;
    }

    //extends identicalSets(): returns points for 'x'-of-a-kind
    static int identicalSetsExtend(int faceCounter){
        switch(faceCounter){
            case 8:
                return 4000;
            case 7:
                return 2000;   
            case 6:
                return 1000;    
            case 5:
                return 500;
            case 4:
                return 200;
            case 3:
                return 100;
            default:
                return 0;
        }   
    }

    //finds the face that appears the most in a set of rolls
    static Faces maxCombo(ArrayList<Faces> list){
        
        int[] countedFaces = faceCounter(list);
        int max = 0;
        Faces maxComboFace = Faces.SKULL;

        for(int i=0; i<countedFaces.length; i++){
            if(countedFaces[i] > max){
                switch(i){
                    case 0:
                        max = countedFaces[0];
                        maxComboFace = Faces.MONKEY;
                        break;
                    case 1:
                        max = countedFaces[1];
                        maxComboFace = Faces.PARROT;
                        break;
                    case 2:
                        max = countedFaces[2];
                        maxComboFace = Faces.GOLD;
                        break;
                    case 3:
                        max = countedFaces[3];
                        maxComboFace = Faces.DIAMOND;
                        break;
                    case 4:
                        max = countedFaces[4];
                        maxComboFace = Faces.SABER;
                        break;
                }
            }
        }

        return maxComboFace;

    }




    
    
}
