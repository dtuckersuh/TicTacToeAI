package game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Board {
 
	private String[][] board;

	// Constructs an empty 3x3 Grid
	public Board() {
		board = new String[3][3];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				board[i][j] = " ";
			}
		}
	}

	// Copy constructor
	public Board(Board src) {
		board = new String[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				board[j][i] = src.board[j][i];
			}
		}
	}

	// Used for existing rows
	public Board(String[][] rows) throws IllegalArgumentException {
		board = rows;
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j] == null) {
					board[i][j] = " ";
				} else if (board[i][j] != "O" && board[i][j] != "X" && board[i][j] != " ") {
					throw new IllegalArgumentException("Wrong input, must either be 'X', 'O', or empty!");
				}
			}
		}
	}

	// Prints a beautiful Tic-Tac-Toe board
	public void render() {
		System.out.println("   0 1 2");
		System.out.println("  -------");
		for (int i = 0; i < 3; i++) {
			System.out.print(i + "| ");
			for (int j = 0; j < 3; j++) {
				System.out.print(this.board[i][j] + " ");
			}
			System.out.println("|");
		}
		System.out.println("  -------");
	}

	public String[][] getBoard() {
		return this.board;
	}

	// Updates board with player move. Returns the new Board.
	// REFACTOR
	public Board makeMove(Board oldBoard, int x, int y, String player) throws IOException {
		Board nextBoard = new Board(oldBoard.getBoard());
		if (!nextBoard.board[y][x].isBlank()) { // Checks if Board coordinates is already occupied
			throw new IOException();
		}
		nextBoard.board[y][x] = player;
		return nextBoard;
	}

	// Works out if there is a winner. Returns null if there is no winner.
	// REFACTOR - 3 separate methods: row winner, column winner, diagonal winner
	public String getWinner() {

		// Row testing.
		for (int j = 0; j < 3; j++) {
			String[] three = new String[3];
			for (int i = 0; i < 3; i++) {
				three[i] = this.getBoard()[j][i];
				if (threeInRow(three)) {
					return three[0];
				}
			}
		}

		// Column testing.
		for (int i = 0; i < 3; i++) {
			String[] three = new String[3];
			for (int j = 0; j < 3; j++) {
				three[j] = this.getBoard()[j][i];
				if (threeInRow(three)) {
					return three[0];
				}
			}
		}

		// Diagonal testing.
		String[] diag1 = new String[3];
		for (int i = 0; i < 3; i++) {
			diag1[i] = this.getBoard()[i][i];
			if (threeInRow(diag1)) {
				return diag1[0];
			}
		}

		String[] diag2 = new String[3];
		for (int i = 0; i < 3; i++) {
			diag2[i] = this.getBoard()[i][2 - i];
			if (threeInRow(diag2)) {
				return diag2[0];
			}
		}

		// No winner
		return "";
	}

	// Checks for three in a row.
	public boolean threeInRow(String[] three) {
		if (three[0] == " ") {
			return false;
		}
		for (int i = 0; i < three.length; i++) {
			if (!three[0].equals(three[i])) {
				return false;
			}
		}
		return true;
	}

	public ArrayList<int[]> getLegalMoves() {
		ArrayList<int[]> possibleCoords = new ArrayList<>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (this.board[j][i].isBlank()) {
					int[] coords = { i, j };
					possibleCoords.add(coords);
				}
			}
		}
		return possibleCoords;
	}

	public String getOpponent(String currentPlayer) {
		if (currentPlayer.equals("X")) {
			return "O";
		} else {
			return "X";
		}
	}

	// Gets user's next move
	// REFACTOR
	public int[] getMove(String currentPlayer, String alg) throws IOException {
		int[] coords = new int[2];
		if (alg.equals("humanPlayer")) {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("Enter X/Y co-ordinates (x y): ");
			String lines = br.readLine();
			String[] strs = lines.trim().split("\\s+");
			for (int i = 0; i < strs.length; i++) {
				coords[i] = Integer.parseInt(strs[i]);
			}
			return coords;
		} else if (alg.equals("randomAI")) {
			coords = this.randomAI();
		} else if (alg.equals("winningMoveAI")) {
			coords = this.winningMoveAI(currentPlayer);
		} else if (alg.equals("winningAndLosingMovesAI")) {
			coords = this.winningAndLosingMovesAI(currentPlayer);
		} else if (alg.equals("minimaxAI")) {
			coords = this.minimaxAI(currentPlayer);
		} else {
			throw new IOException();
		}
		return coords;
	}

	// Gets coordinates for a random, legal move
	// REFACTOR
	public int[] randomAI() {
		ArrayList<int[]> possibleCoords = this.getLegalMoves();
		Random randomizer = new Random();
		int[] randomMove = possibleCoords.get(randomizer.nextInt(possibleCoords.size()));
		return randomMove;
	}

	// Returns winning move if it exists, else returns a random, legal move
	// REFACTOR
	public int[] winningMoveAI(String currentPlayer) throws IOException {
		ArrayList<int[]> possibleCoords = this.getLegalMoves();
		Random randomizer = new Random();
		for (int i = 0; i < possibleCoords.size(); i++) {
			int[] coords = possibleCoords.get(i);
			Board newBoard = new Board(this);
			newBoard.makeMove(newBoard, coords[0], coords[1], currentPlayer);
			if (!newBoard.getWinner().isEmpty()) { // Tests all moves, if one is a winning move then returns that move
				return coords;
			}
		}
		return possibleCoords.get(randomizer.nextInt(possibleCoords.size())); // If no move is winning then random,
																				// legal move returned
	}

	// Returns, in order of preference: a move that wins, a move that blocks a
	// losing move, or a random move
	// REFACTOR
	public int[] winningAndLosingMovesAI(String currentPlayer) throws IOException {
		ArrayList<int[]> possibleCoords = this.getLegalMoves();
		Random randomizer = new Random();
		// Returns winning move if it exists
		for (int i = 0; i < possibleCoords.size(); i++) {
			int[] coords = possibleCoords.get(i);
			Board newBoard = new Board(this);
			newBoard.makeMove(newBoard, coords[0], coords[1], currentPlayer);
			if (!newBoard.getWinner().isEmpty()) {
				return coords;
			}
		}

		// Blocks losing move if exists
		String oppPlayer = this.getOpponent(currentPlayer);
		for (int i = 0; i < possibleCoords.size(); i++) {
			int[] coords = possibleCoords.get(i);
			Board newBoard = new Board(this);
			newBoard.makeMove(newBoard, coords[0], coords[1], oppPlayer);
			if (!newBoard.getWinner().isEmpty()) {
				return coords;
			}
		}

		// Random move
		return possibleCoords.get(randomizer.nextInt(possibleCoords.size()));
	}

	/**
	 * 	REFACTOR
	 */

	// Considers all of the current positions legal moves, and assigns the
	// position resulting from each move a numerical score. Then calculates these
	// scores by working out what the result of the game would be if it were played
	// out between two equally perfect players. Finally, it will choose the move
	// that gives it the maximum score
	public int minimaxScore(String currentPlayer) throws IOException {
		// Terminal states
		String winner = this.getWinner();
		if (!winner.isEmpty()) {
			if (winner.equals("X")) {
				return 10;
			} else {
				return -10;
			}
		} else if (this.isBoardFull()) {
			return 0;
		}

		// Gets all legal moves of non-terminal states
		ArrayList<int[]> legalMoves = this.getLegalMoves();
		ArrayList<Integer> scores = new ArrayList<>();

		for (int[] move : legalMoves) {
			Board copyBoard = new Board(this);
			copyBoard.makeMove(copyBoard, move[0], move[1], currentPlayer);
			String opponent = copyBoard.getOpponent(currentPlayer);
			int nonTerminalScore = copyBoard.minimaxScore(opponent);
			scores.add(nonTerminalScore);
		}

		// Non-terminal state. If current player is AI ("X") then return max of scores,
		// else return min.
		if (currentPlayer.equals("X")) {
			return Collections.max(scores);
		} else {
			return Collections.min(scores);
		}
	}

	// Implements recursive minimaxScore method to select best possible move
	public int[] minimaxAI(String currentPlayer) throws IOException {
		ArrayList<int[]> legalMoves = this.getLegalMoves();
		Integer bestScore = null;
		int[] bestMove = new int[2];
		for (int[] move : legalMoves) {
			Board copyBoard = new Board(this);
			copyBoard.makeMove(copyBoard, move[0], move[1], currentPlayer);
			String opponent = copyBoard.getOpponent(currentPlayer);
			int score = copyBoard.minimaxScore(opponent);
			if (bestScore == null || score > bestScore) {
				bestMove = move;
				bestScore = score;
			}
		}
		return bestMove;
	}

	// Enables game run-through
	// REFACTOR
	public int play(String alg1, String alg2) {
		String[] players = { "X", "O" };
		String[] algs = { alg1, alg2 };
		Random randomizer = new Random();
		int startingTurn = randomizer.nextInt(2);
		String currentPlayer = players[startingTurn];
		String currentAlg = algs[startingTurn];
		int turnNumber = startingTurn;
		Board newBoard = new Board(this.board);
		do {
			int[] coords = new int[2];
			System.out.println("PLAYER " + currentPlayer + " (" + currentAlg + ")" + " TURN");
			newBoard.render();
			System.out.println();
			try {
				coords = newBoard.getMove(currentPlayer, currentAlg);
				TimeUnit.SECONDS.sleep(1);
				newBoard.makeMove(newBoard, coords[0], coords[1], currentPlayer);
				if (!newBoard.getWinner().isEmpty()) { // Tests if there is a winner
					newBoard.render();
					System.out.println(newBoard.getWinner() + " is the winner!");
					if (newBoard.getWinner().equals("X")) {
						return 1;
					} else if (newBoard.getWinner().equals("O")) {
						newBoard.render();
						return 2;
					}
				} else if (newBoard.isBoardFull()) { // Tests if there is a draw
					newBoard.render();
					System.out.println("It's a draw!");
					return 0;
				}
			} catch (NumberFormatException | IOException | ArrayIndexOutOfBoundsException | InterruptedException e) {
				System.out.println("INVALID INPUT. TRY AGAIN");
				System.out.println();
				continue;
			}
			turnNumber++;
			currentPlayer = newBoard.getOpponent(currentPlayer);
			currentAlg = algs[turnNumber % 2];
		} while (newBoard.getWinner().isEmpty() && !newBoard.isBoardFull());
		return 0;
	}

	// Repeats play function 100 times and tallys results
	// REFACTOR
	public void repeatedBattle(String algOne, String algTwo) {
		int draw = 0;
		int playerX = 1;
		int xWins = 0;
		int oWins = 0;
		for (int i = 0; i < 50; i++) {
			System.out.println("Game #" + i);
			Board newBoard = new Board();
			int result = newBoard.play(algOne, algTwo);
			System.out.println(result);
			if (result == draw) {
				draw++;
			} else if (result == playerX) {
				xWins++;
			} else if (result == 2) {
				oWins++;
			}
		}
		System.out.println(algOne + " wins: " + xWins);
		System.out.println(algTwo + " wins: " + oWins);
		System.out.println("Draws: " + draw);
	}

	// Checks if game is a draw
	public boolean isBoardFull() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (this.board[i][j] != "X" && this.board[i][j] != "O") {
					return false;
				}
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Board newBoard = new Board();
		newBoard.play("minimaxAI", "humanPlayer");
	}
}