/**
 * Player class responsibility: Keep track of Player string
 */
public class Player {
    
    String player;
    int turn;

    public Player(int turn) {
        this.turn = turn;
        if (turn % 2 == 0) {
            player = "X";
        } else {
            player = "O";
        } 
    }

    public String getPlayer(){
        return player;
    }

    public void setPlayer(String player){
        this.player = player;
        turn = 0;
    }
}