package interfaces;

import java.util.InputMismatchException;
import java.util.Scanner;

import global.ChessBoard;
import global.Coord;

/**
 * Manage command line inputs
 *
 */
public class Input {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Ask a Coord under the form of "(x,y)"
	 * 
	 * @return The entered Coord
	 */
	public static Coord askCoord() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Saisissez une coordonnée du board sous la forme '(x,y)'");
			String coord = sc.next("[(][0-7][,][0-7][)]");
			int x = Integer.parseInt(coord.substring(1, 2));
			int y = Integer.parseInt(coord.substring(3, 4));
			Coord c = new Coord(x, y);
			return c;

		} catch (InputMismatchException e) {
			System.out.println("Forme non valide");
			return null;
		}

	}

	/**
	 * Ask a Coord under the form of "a1"
	 * 
	 * @return The entered Coord
	 */
	public static Coord askTrueCoord() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Saisissez une coordonnée du board sous la forme 'e4'");
			String coord = sc.next("[a-h][1-8]");
			int x = Integer.parseInt(coord.substring(1, 2));
			int y = 0;
			String y1 = (coord.substring(0, 1));
			switch (y1) {
			case "a":
				y = 0;
				break;
			case "b":
				y = 1;
				break;
			case "c":
				y = 2;
				break;
			case "d":
				y = 3;
				break;
			case "e":
				y = 4;
				break;
			case "f":
				y = 5;
				break;
			case "g":
				y = 6;
				break;
			case "h":
				y = 7;
				break;
			}

			Coord c = new Coord(8 - x, y);
			return c;

		} catch (InputMismatchException e) {
			System.out.println("Forme non valide");
			return null;
		}

	}

	/**
	 * Ask a Coord under a certain form, according to the board configuration
	 * 
	 * @return The entered Coord
	 */
	public static Coord askValidCoord() {
		if (ChessBoard.getConfigBoard() == 0) {
			Coord c = null;
			while (c == null) {
				c = askCoord();
			}
			return c;
		} else {
			Coord c = null;
			while (c == null) {
				c = askTrueCoord();
			}
			return c;
		}
	}

	/**
	 * Ask yes or no
	 * 
	 * @return The answer is "y"
	 */
	public static Object askYesNo() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Do you want play this piece? Answer 'y' for yes  or 'n' for No");
			String answer = sc.next("[yn]");
			return answer.equals("y");

		} catch (InputMismatchException e) {
			System.out.println("Forme non valide");
			return null;
		}
	}

	/**
	 * Ask yes or no, till a correct answer
	 * 
	 * @return The answer is "y"
	 */
	public static boolean askValidYesNo() {
		Object b = null;
		while (b == null) {
			b = askYesNo();
		}
		return (boolean) b;
	}

	/**
	 * Ask a Piece for the promotion
	 * 
	 * @return The number of the selected Piece
	 */
	public static Object askIntPromotion() {
		try {
			@SuppressWarnings("resource")
			Scanner sc = new Scanner(System.in);
			System.out.println("Chose a Piece of promoting, '0'=knight, '1'=bishop, '2'=rock, all other number=Queen");
			int nb = sc.nextInt();
			return nb;
		} catch (InputMismatchException e) {
			System.out.println("this is not a number! Please retry!");
			return null;
		}
	}

	/**
	 * Ask a Piece for the promotion, till a correct answer
	 * 
	 * @return The number of the selected Piece
	 */
	public static int askValidIntPromotion() {
		Object i = null;
		while (i == null) {
			i = askIntPromotion();
		}
		return (int) i;
	}

	/**
	 * Initialize the Input, and call askValidPromotion
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		System.out.println(askValidIntPromotion());

	}

	public static Object askSave() {
		try {
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Do you want to save? Answer 'y' for yes  or 'n' for No");
			String answer=sc.next("[yn]");
			return answer.equals("y");
			
		}catch(InputMismatchException e){
			System.out.println("Forme non valide");
			return null;
		}
	}
	
	public static Object askLoad() {
		try {
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Do you want to load? Answer 'y' for yes  or 'n' for No");
			String answer=sc.next("[yn]");
			return answer.equals("y");
			
		}catch(InputMismatchException e){
			System.out.println("Forme non valide");
			return null;
		}
	}
	
	public static boolean askSaveFile() {
		Object s=null;
		while(s==null) {
			s=askSave();
		}
		return (boolean)s;
	}
	
	public static boolean askLoadFile() {
		Object s=null;
		while(s==null) {
			s=askLoad();
		}
		return (boolean)s;
	}
	
}
