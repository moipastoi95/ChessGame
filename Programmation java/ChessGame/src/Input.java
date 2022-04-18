import java.util.InputMismatchException;
import java.util.Scanner;
public class Input {
	public static Coord askCoord() {
		try {
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Saisissez une coordonnée du board sous la forme '(x,y)'");
			String coord=sc.next("[(][0-7][,][0-7][)]");
			int x=Integer.parseInt(coord.substring(1,2));
			int y=Integer.parseInt(coord.substring(3,4));
			Coord c=new Coord(x,y);
			return c;
			
		}catch(InputMismatchException e){
			System.out.println("Forme non valide");
			return null;
		}
		
	}
	
	public static Coord askValidCoord() {
		Coord c=null;
		while(c==null) {
			c=askCoord();
		}
		return c;
	}
	
	public static Object askYesNo() {
		try {
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Do you want play this piece? Answer 'Y'for yes  or 'N' for No");
			String answer=sc.next("[YN]");
			return answer.equals("Y");
			
		}catch(InputMismatchException e){
			System.out.println("Forme non valide");
			return null;
		}
	}
	
	public static boolean askValidYesNo() {
		Object b=null;
		while(b==null) {
			b=askYesNo();
		}
		return (boolean)b;
	}
	
	public static Object askIntPromotion() {
		try {
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Chose a Piece of promuting, '0'=knight, '1'=bishop, '2'=rock, all other number=Queen");
			int nb=sc.nextInt();
			return nb;
		}catch(InputMismatchException e) {
			System.out.println("this is not a number! Please retry!");
			return null;
		}
	}
	
	public static int askValidIntPromotion() {
		Object i=null;
		while(i==null) {
			i=askIntPromotion();
		}
		return (int)i;
	}
	
	public static void main(String[] args) {
		System.out.println(askValidIntPromotion());
		
	}
}

