import pk.Game;
import pk.ManageLog;

public class PiratenKarpen {

    public static void main(String[] args) {

        if(args.length == 4) ManageLog.setManageLog(args[3]);
        
        Game game = new Game();
        game.play("p1", "p2", Integer.valueOf(args[2]),args[0], args[1]);
    }
    
}
