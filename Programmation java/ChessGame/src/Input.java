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

		public static Coord askTrueCoord() {
			try {
				@SuppressWarnings("resource")
				Scanner sc=new Scanner(System.in);
				System.out.println("Saisissez une coordonnée du board sous la forme 'e4'");
				String coord=sc.next("[a-h][1-8]");
				int x=Integer.parseInt(coord.substring(1,2));
				int y=0;
				String y1=(coord.substring(0,1));
				switch(y1) {
				case "a":
					y=0;
					break;
				case "b":
					y=1;
					break;
				case "c":
					y=2;
					break;
				case "d":
					y=3;
					break;
				case "e":
					y=4;
					break;
				case "f":
					y=5;
					break;
				case "g":
					y=6;
					break;
				case "h":
					y=7;
					break;			
				}
				
				Coord c=new Coord(8-x,y);
				return c;
				
			}catch(InputMismatchException e){
				System.out.println("Forme non valide");
				return null;
			}
			
		} 
	
	public static Coord askValidCoord() {
		if(ChessBoard.getConfigBoard()==0) {
			Coord c=null;
			while(c==null) {
				c=askCoord();
			}
			return c;
		}else {
			Coord c=null;
			while(c==null) {
				c=askTrueCoord();
			}
			return c;
		}
	}
	
	public static Object askYesNo() {
		try {
			@SuppressWarnings("resource")
			Scanner sc=new Scanner(System.in);
			System.out.println("Do you want play this piece? Answer 'y' for yes  or 'n' for No");
			String answer=sc.next("[yn]");
			return answer.equals("y");
			
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

