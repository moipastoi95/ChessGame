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
	
	
	public static void main(String[] args) {
		System.out.println(askValidCoord());
		System.out.println(askValidYesNo());
	}
}

