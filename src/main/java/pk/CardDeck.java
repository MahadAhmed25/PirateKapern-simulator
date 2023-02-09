package pk;
import java.util.ArrayList;
import java.util.Collections;

/*
 * Creates the 35 card, card deck
 */

public class CardDeck {
    public ArrayList<Cards> deck;

    public CardDeck(){
        deck  = new ArrayList<Cards>();

        for(int i=0; i<2; i++) deck.add(Cards.SEABATTLE2);
        for(int i=0; i<2; i++) deck.add(Cards.SEABATTLE3);
        for(int i=0; i<2; i++) deck.add(Cards.SEABATTLE4);

        for(int i=0; i<4; i++) deck.add(Cards.MONKEYBUSINESS);
        
        for(int i=0; i<25; i++) deck.add(Cards.NOP);
        
    }

    public void shuffleDeck() {
        Collections.shuffle(deck);
    }

    public Cards drawCard(){
        Cards draw = deck.get(0);
        deck.remove(draw);
        deck.add(draw);
        return draw;
    }

}
