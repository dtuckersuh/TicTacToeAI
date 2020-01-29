package test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;

import org.junit.Test;

import game.Board;

public class BoardTester {

	@Test
	public void testRowWin() {
		String[][] testRows = { { "X", "X", "X", }, { " ", " ", " " }, { " ", " ", " " }

		};
		Board newBoard = new Board(testRows);
		assertNotNull(newBoard.getWinner(), "X should be winner");

		String[][] testRows2 = { { " ", " ", " ", }, { "O", "O", "O" }, { " ", " ", " " } };
		Board newBoard2 = new Board(testRows2);
		assertNotNull(newBoard2.getWinner(), "O should be winner");

		String[][] testRows3 = { { " ", " ", " ", }, { " ", " ", " " }, { "O", "O", "O" } };
		Board newBoard3 = new Board(testRows3);
		assertNotNull(newBoard3.getWinner(), "O should be winner");
		System.out.println("Rows Test Passed");
	}

	@Test
	public void testColWin() {
		String[][] testRows = { { "O", " ", " ", }, { "O", " ", " " }, { "O", " ", " " } };
		Board newBoard = new Board(testRows);
		assertNotNull(newBoard.getWinner(), "O should be winner");

		String[][] testRows2 = { { " ", "X", " ", }, { " ", "X", " " }, { " ", "X", " " } };
		Board newBoard2 = new Board(testRows2);
		assertNotNull(newBoard2.getWinner(), "X should be winner");

		String[][] testRows3 = { { " ", " ", "O", }, { " ", " ", "O" }, { " ", " ", "O" } };
		Board newBoard3 = new Board(testRows3);
		assertNotNull(newBoard3.getWinner(), "O should be winner");
		System.out.println("Column Test Passed");
	}

	@Test
	public void testDiagWin() {
		String[][] testRows = { { "O", " ", " ", }, { " ", "O", " " }, { " ", " ", "O" } };
		Board newBoard = new Board(testRows);
		assertNotNull(newBoard.getWinner(), "O should be winner");

		String[][] testRows2 = { { " ", " ", "X", }, { " ", "X", " " }, { "X", " ", " " } };
		Board newBoard2 = new Board(testRows2);
		assertNotNull(newBoard2.getWinner(), "X should be winner");
		System.out.println("Diagonal Test Passed");
	}

	@Test
	public void testDraw() {
		String[][] testRows = { { "O", "O", "X" }, { "X", "O", "X" }, { "O", "X", "O" } };
		Board newBoard = new Board(testRows);
		assert newBoard.isBoardFull();
		System.out.println("Draw Test Passed");
	}

	@Test
	public void testRandomAI() {
		String[][] testRows = { { "X", "X", "O" }, { " ", " ", " " }, { " ", "X", "O" } };
		Board newBoard = new Board(testRows);
		int[] randomCoord = newBoard.randomAI();
		assert testRows[randomCoord[1]][randomCoord[0]].isBlank();
		System.out.println("Random AI Test Passed");
	}

	@Test
	public void testMinimax() throws IOException {
		// If AI goes first and marks center, then opponent marks edge
		String[][] centerEdgeTestRows = { { "O", " ", " " }, { "O", "X", " " }, { " ", " ", "X" } };
		Board newBoard = new Board(centerEdgeTestRows);
		int[] minimaxCoords = newBoard.minimaxAI("X");
		int[] correctCoords = { 0, 2 };
		assert Arrays.equals(minimaxCoords, correctCoords);

		// If AI goes first and marks center, then opponent marks corner
		String[][] centerCornerTestRows = { { " ", " ", "X" }, { "O", "X", " " }, // AI WIN
				{ "O", " ", " " } };

		String[][] centerCornerTestRows2 = { { " ", "O", "X" }, { " ", "X", " " }, // AI WIN
				{ "O", " ", " " } };

		String[][] centerCornerTestRows3 = { { "O", " ", "X" }, { " ", "X", " " }, // TIE
				{ "O", " ", " " } };

		Board newBoard1 = new Board(centerCornerTestRows);
		Board newBoard2 = new Board(centerCornerTestRows2);
		Board newBoard3 = new Board(centerCornerTestRows3);

		int[] newBoard1Actual = newBoard1.minimaxAI("X");
		int[] newBoard2Actual = newBoard2.minimaxAI("X");
		int[] newBoard3Actual = newBoard3.minimaxAI("X");

		int[] newBoard1Expected = { 0, 0 };
		int[] newBoard2Expected = { 2, 1 };
		int[] newBoard3Expected = { 0, 1 };

		assert Arrays.equals(newBoard1Expected, newBoard1Actual);
		assert Arrays.equals(newBoard2Expected, newBoard2Actual);
		assert Arrays.equals(newBoard3Expected, newBoard3Actual);

		// If AI goes corner first, then opponent marks edge
		String[][] cornerEdgeTestRows = { { " ", " ", " " }, { "O", " ", " " }, { "X", "O", "X" } };
		String[][] cornerEdgeTestRows2 = { { "O", " ", " " }, { " ", " ", " " }, { "X", "O", "X" } };
		String[][] cornerEdgeTestRows3 = { { " ", " ", " " }, { " ", " ", "O" }, { "X", "O", "X" } };
		String[][] cornerEdgeTestRows4 = { { " ", " ", "O" }, { " ", " ", " " }, { "X", "O", "X" } };

		Board cornerEdgeBoard = new Board(cornerEdgeTestRows);
		Board cornerEdgeBoard2 = new Board(cornerEdgeTestRows2);
		Board cornerEdgeBoard3 = new Board(cornerEdgeTestRows3);
		Board cornerEdgeBoard4 = new Board(cornerEdgeTestRows4);

		int[] cornerEdgeBoardActual = cornerEdgeBoard.minimaxAI("X");
		int[] cornerEdgeBoardActual2 = cornerEdgeBoard2.minimaxAI("X");
		int[] cornerEdgeBoardActual3 = cornerEdgeBoard3.minimaxAI("X");
		int[] cornerEdgeBoardActual4 = cornerEdgeBoard4.minimaxAI("X");

		cornerEdgeBoard2.makeMove(cornerEdgeBoard2, cornerEdgeBoard2.minimaxAI("X")[0],
				cornerEdgeBoard2.minimaxAI("X")[1], "X");
		cornerEdgeBoard3.makeMove(cornerEdgeBoard3, cornerEdgeBoard3.minimaxAI("X")[0],
				cornerEdgeBoard3.minimaxAI("X")[1], "X");
		cornerEdgeBoard4.makeMove(cornerEdgeBoard4, cornerEdgeBoard4.minimaxAI("X")[0],
				cornerEdgeBoard4.minimaxAI("X")[1], "X");

		int[] cornerEdgeBoardExpected = { 1, 1 };
		int[] cornerEdgeBoardExpected2 = { 2, 0 };
		int[] cornerEdgeBoardExpected3 = { 0, 0 };
		int[] cornerEdgeBoardExpected4 = { 0, 0 };

		assert Arrays.equals(cornerEdgeBoardActual, cornerEdgeBoardExpected);
		assert Arrays.equals(cornerEdgeBoardActual2, cornerEdgeBoardExpected2);
		assert Arrays.equals(cornerEdgeBoardActual3, cornerEdgeBoardExpected3);
		assert Arrays.equals(cornerEdgeBoardActual4, cornerEdgeBoardExpected4);
		System.out.println("Minimax AI Test Passed");
	}
}