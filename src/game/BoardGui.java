import java.awt.event.ActionEvent;

import javax.swing.JPanel;

public class BoardGui extends JPanel {
    
    JButton buttons[] = new JButton[9];
    Player[] players;
    int turn;

    public BoardGui(){
        setLayout(new GridLayout(3, 3));
        this.players = new Player[2];
        this.turn = 0;
        players[0] = new Player(turn);
        players[1] = new Player(turn++);
    }

    public void initializeButtons(){
        int initial = 8;
        for (int i = 0; i < initial; i++){
            buttons[i] = new JButton();
            buttons[i].setText("");
            buttons[i].addActionListener(new buttonListener());

            add(buttons[i]);
        }
    }

    public void resetButtons(){
        for (int i = 0; i < 8; i++){
            buttons[i].setText("");
        }
    }

    private class buttonListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            JButton buttonClicked = (JButton) e.getSource();
            Player current = players[turn];
            buttonClicked.setText(current.getPlayer());

            if (checkForWin()){
                JOptionPaneshowConfirmDialog(null, "Game Over.");
                resetButtons();
            }

            turn++; 
        }
        /**
         * Button Arrangement
         * 0 | 1 | 2
         * 3 | 4 | 5
         * 6 | 7 | 8
         */
        public boolean checkForWin(){
            return (horizontalWin() || verticalWin() || diagonalWin());
        }

        public boolean horizontalWin(){
            int rows, col = 3;
            int count = 0;
            for (int i = 0; i < rows; i++){
                for (int j = 0; j < col; j++){
                    u
                }
            }
        }

        public boolean verticalWin(){

        }

        public boolean diagonalWin(){

        }

        public boolean checkThree(int squareA, int squareB, int squareC) {
            return (buttons[squareA].getText().equals(
                    buttons[squareB].getText()).equals(
                    buttons[squareC].getText()));
        }
    }
}