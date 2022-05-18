import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;


public class Main extends Application {
	/**
     * joueur blanc commence en premier
     */
	String color = "White";		
	/**
     * 
     */
	List<String> colorList = new ArrayList<String>();  //Dua vao List de su dung duoc trong Lambda
	/**
     * 
     */
	boolean turn_board = false; //test turn 90 degree the chess board
	/**
     * 
     */
	List<Boolean> turn_boardList = new ArrayList<Boolean>();
	/**
     * 
     */
	List<Coord> histListWhite  = new ArrayList<Coord>();	//List to keep historical steps of White
	List<Coord> histListBlack  = new ArrayList<Coord>(); 	//List to keep historical steps of Black
	/**
     * 
     */
	List<LostPiece> lostPieceWhiteList = new ArrayList<LostPiece>(); //List to keep lost pieces
	List<LostPiece> lostPieceBlackList = new ArrayList<LostPiece>();
	/**
     * 
     */
	List<Integer> srcDestList = new ArrayList<Integer>();	//List to keep source and destination coordinates
	
	@Override
	public void start(Stage primaryStage) {
		try {
		turn_board = false;				//Create turn variable, false is meaning no turn. 
		turn_boardList.add(turn_board); //The save this value to first element of turn_board list
		
		LostPiece lostPieceWhite = new LostPiece(0, 0, 0, 0, 0, 0, true);	//Initial lost piece object and save to a list.
		lostPieceWhiteList.add(lostPieceWhite);
		LostPiece lostPieceBlack = new LostPiece(0, 0, 0, 0, 0, 0, false);
		lostPieceBlackList.add(lostPieceBlack);
		
		
			
			Game gameTest = new Game();
			
			//Put chessBoard into a List so that chessBoard variable can be change in the Lambda
			List<ChessBoard> chessBoardList = new ArrayList<ChessBoard>(); 
			chessBoardList.add(gameTest.cb);
			
			// Define grid of displayed chess board
			GridPane pane = new GridPane();
			
			//Call displayCell() function to draw the visible chess board and put appropriate piece onto the cells of the chess board. 
			displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));  //Tham so: turn_BoardList de kiem tra chess board co quay ko?
			
			//Numbering rows and columns of the visible chess board
			NumberingRowCol(pane);
			
			//Create labels to display the current player
			labelCurentPlayer(pane, colorList, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
			
			//Create buttons for the game board.
			createSaveOpenExitButton(pane,chessBoardList, colorList);
			
			//Xu ly khi bam chuot
			Integer count_select = 1;		//Variable to identify 
			List<Integer> countList = new ArrayList<Integer>();
			countList.add(count_select);
			
			//List<Integer> srcDestList = new ArrayList<Integer>();	//List to keep source and destination coordinates
			for (int i=0; i<4; i++) {	
				srcDestList.add(i, 0);
			}
			
			//HashSet<Coord> pMove = null;	//HashSet contains possible move
			HashSet<Coord> pMove = new HashSet<Coord>() ;
			List<HashSet<Coord>> pMoveList = new ArrayList<HashSet<Coord>>();
			pMoveList.add(pMove);			//Must add the first element, otherwise an error will be happened
			
			/*
			//Possible piece to move
  		  	if(gameList.get(0).getTurn()) {
				  gameList.get(0).cb.coorPieceMovable(gameList.get(0).whitePlayer.coordOfMyPieces, gameList.get(0).getTurn());;
				  gameList.get(0).cb.updateCheckStatusking(gameList.get(0).blackPlayer.coordOfMyPieces, gameList.get(0).getTurn());;				    					  
			  } else {
				  gameList.get(0).cb.coorPieceMovable(gameList.get(0).blackPlayer.coordOfMyPieces, gameList.get(0).getTurn());;
				  gameList.get(0).cb.updateCheckStatusking(gameList.get(0).whitePlayer.coordOfMyPieces, gameList.get(0).getTurn());;				    					  
			  }
  		  	System.out.println(gameList.get(0).toString());
			*/
			//System.out.println(chessBoardList);
			pane.setOnMousePressed(new EventHandler<MouseEvent>() {
			      @Override public void handle(MouseEvent event) {
			    	  	int rx, cy, rz, cw;
			    	  	int c;
			    		int r;
				    	c = (int)Math.round(event.getX())/50;  //Column
				    	r = (int)Math.round(event.getY())/50;  //Row				    	  				    	  
				    	if (c <=7 && r <=7) {
				    		  //System.out.println("x: " + event.getX() +"; y: " + event.getY());
				    		  System.out.println("c: " + c);
				    		  System.out.println("r: " + r);
				    		  System.out.println("x: " + countList);
				    		  int number = countList.get(0);	//Variable de kiem soat lan bam chuot thu may
				    		  switch (number) {
				    		  case 1:
				    			  //Tam thoi de the nay, can chuyen colorList ve thanh mang boolean - de tam de test voi quan trang
				    			  if (chessBoardList.get(0).board[r][c] == null || !chessBoardList.get(0).board[r][c].getColor() == (colorList.get(0)=="White")) {
				    				  //showAlerInvalidInput("Invalid Input");  //Neu lan dau chon vao o trong hoac khong phai quan cua nguoi choi hien thoi
				    			  }
				    			  else {
				    				  System.out.println(chessBoardList.get(0).board[r][c]);				    				
				    				  System.out.println("r: " + r + "; c: " + c);
				    				  rx = r;
				    				  cy = c;
				    				  srcDestList.set(0,(Integer)rx);
				    				  srcDestList.set(1,(Integer)cy);
				    				  
				    				  //Tao pMove chua cac vi tri ma quan co duoc cho co the di
				    				  HashSet<Coord> pMove=chessBoardList.get(0).board[r][c].possibleMove(new Coord(r,c));
				    				  System.out.println("pMove: " + pMove);
				    				  pMoveList.set(0, pMove);
				    				  
				    				  //Danh dau cac o ma quan co dang chon co the di duoc
				    				  for (Coord coord : pMove) {
				    					  Label labelGo = new Label("X");
			    							 labelGo.setTextFill(Color.BLUE);
			    							 labelGo.setStyle("-fx-font-weight: bold");
			    							 pane.add(labelGo, coord.getC(), coord.getR());
				    				  }				    			  
				    			  Integer click_1 = 2;		//The first mouse click is successful. Change the click_1 to 2 so that the next click will be consider second click
				    			  countList.set(0, click_1);
				    			  }
				    			  break;
				    		  case 2:
				    			  //if (chessBoardList.get(0).board[r][c] != null && chessBoardList.get(0).board[r][c].getColor() == (colorList.get(0)=="White")) {
				    			  if (chessBoardList.get(0).board[r][c] != null && 
				    			  		chessBoardList.get(0).board[r][c].getColor() == 
				    			  		chessBoardList.get(0).board[srcDestList.get(0)][srcDestList.get(1)].getColor()) {
				    			  		System.out.println("Invalid dest");  	//Neu lan bam thu 2 bam vao o khong trong, va co quan co dong mau voi nguoi choi hien thoi
				    			  } else {									//Do quy dinh o ChessBoard la quan co trang ung voi getColor = true, nen lenh tren dong nghia voi true la White
				    				  System.out.println("r: " + r + "; c: " + c);
				    				  rz = r;
				    				  cw = c;
				    				  srcDestList.set(2,(Integer)rz);	//set the rz, cw to third element and fourth of srcDestList to keep it
				    				  srcDestList.set(3,(Integer)cw);				    				 				    				 
				    				  
				    				  Coord destMove = new Coord(r, c);	//Creat a new Coord object (keeps the r and c) that will be used in checking with possible move list.
				    				  int meet = 0;	//Variable to check whether destMove is in pMove. Do not know why cannot use contains and equals method of HashSet
				    				  for (Coord coord : pMoveList.get(0)) {
				    					  if (destMove.row == coord.row && destMove.column == coord.column) {
				    					  System.out.println("meet!");
				    					  meet++;
				    					  //This test only - will re do more...
				    					  //Check neu an thi an quan co gi va cong vao so luong quan bi an
				    					  if (chessBoardList.get(0).board[destMove.row][destMove.column] != null) {
				    						  System.out.println(chessBoardList.get(0).board[destMove.row][destMove.column].toString());				    						  				    						 
				    						  //Update vao first element of list lost piece sau khi an. Luu y ko update truc tiep vap lostPieceWhite/Black
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("r")) { 				    							  				    							  
				    							  lostPieceBlackList.get(0).rookNum +=1; //For test;
				    						  }  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("R")) {				    							  				    							  
				    							  lostPieceWhiteList.get(0).rookNum +=1;
				    						  }
				    						  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("c")) {				    							  
				    							  lostPieceBlackList.get(0).knightNum +=1;
				    						  }			  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("C")) {				    							  
				    							  lostPieceWhiteList.get(0).knightNum +=1;
				    						  }
				    						  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("b")) {				    							  
				    							  lostPieceBlackList.get(0).bishopNum +=1;
				    						  }			  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("B")) {				    							  
				    							  lostPieceWhiteList.get(0).bishopNum +=1;
				    						  }
				    						  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("k")) {				    							  
				    							  lostPieceBlackList.get(0).kingNum +=1;
				    						  }			  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("K")) {				    							 
				    							  lostPieceWhiteList.get(0).kingNum +=1;
				    						  }
				    						  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("q")) {				    							  
				    							  lostPieceBlackList.get(0).queenNum +=1;
				    						  }			  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("Q")) {				    							  
				    							  lostPieceWhiteList.get(0).queenNum +=1;
				    						  }
				    						  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("p")) {				    							  
				    							  lostPieceBlackList.get(0).pawnNum +=1;
				    						  }			  
				    						  if (chessBoardList.get(0).board[destMove.row][destMove.column].toString().contains("P")) {				    							  
				    							  lostPieceWhiteList.get(0).pawnNum +=1;
				    						  }
				    					  }
				    					  				    					  
				    					  chessBoardList.get(0).board[destMove.row][destMove.column] = chessBoardList.get(0).board[srcDestList.get(0)][srcDestList.get(1)];
				    					  chessBoardList.get(0).board[srcDestList.get(0)][srcDestList.get(1)] = null;
				    				  } 
				    			  }	  
				    				  
				    			  if (meet > 0 ) {
				    				  if (chessBoardList.get(0).board[r][c].getColor() == true) { //Luu vao historical list of white				    					  
				    					  histListWhite.add(new Coord(srcDestList.get(0), srcDestList.get(1)));
				    					  histListWhite.add(new Coord(srcDestList.get(2), srcDestList.get(3)));				    					 			    					  
				    				  } else {				    					 
				    					  histListBlack.add(new Coord(srcDestList.get(0), srcDestList.get(1)));
				    					  histListBlack.add(new Coord(srcDestList.get(2), srcDestList.get(3)));				    					 
				    				  }
				    			  } else {
				    				  System.out.println("Wrong select!");
				    				  showAlerInvalidInput("Wrong select!");
				    				  colorList.set(0, colorToggle(colorList.get(0)));
				    			  }
				    			  
				    			  //String move = coordinatesToMoveString_2(srcDestList.get(0), srcDestList.get(1), srcDestList.get(2), srcDestList.get(3));
				    			  //System.out.println("move: " + move);
				    			  /*
				    			  try {
					    	            	gameBoardList.get(0).performMove(move, colorList.get(0), true);
					    	          } catch (IOException even) {
					    	                // Ask for user input again
					    	                System.out.println("Invalid input!");
					    	                //continue;
					    	                showAlerInvalidInput("Invalid Input");
					    	                colorList.set(0, colorToggle(colorList.get(0)));
					    	           }
				    			  */
				    			  displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));
				    			  colorList.set(0, colorToggle(colorList.get(0)));	//Change Player (change color of player)				    			  
	    						  
				    			  labelCurentPlayer(pane, colorList, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
						          Integer click_2 = 1;			//Set the count of mouse to 1.
						          countList.set(0, click_2);	
						          
						          //***** Kiem tra thang thua ******
						          //Kiem tra xem co het nuoc di ko?
						          /*
						          if(!gameBoardList.get(0).canAnyPieceMakeAnyMove(colorToggle(colorList.get(0)))){
						        	  if(gameBoardList.get(0).isInCheck(colorToggle(colorList.get(0)))){
						        		  System.out.println("Checkmate. " + colorList.get(0) + " wins");
						        		  System.out.println("Game over!");
						        		  showAlerInvalidInput("Game Over!" + " Checkmate. " + colorList.get(0) + " wins");
						        	  }else{
						        		  System.out.println("Stalemate!");	//Het nuoc di (stalemate) tu la hoa (draw)
						        	  }
						        	  return;
						          }
						          */
						          //gameBoard.board = oldBoard;
						          //Kiem tra xem co bi chieu tuong ko?
						          /*
						          if(gameBoardList.get(0).isInCheck(colorToggle(colorList.get(0)))){
						        	  System.out.println(colorToggle(colorList.get(0)) + " is in check.");
						        	  showAlerInvalidInput(colorList.get(0) + " is in check.");
						          }
						          */
					    	      //*****
				    			  }
				    			  break;
				    		  default:
				    			  break;
				    		  }
				    	  }
			    	} 
			 });
			
			//**************************************
			
			//Create a Scene and put the pane on the Scene  
			Scene scene = new Scene(pane);
			//Assign the Scene to the Stage
			primaryStage.setScene(scene);
			primaryStage.setTitle("ChessBoard Projet");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
		//Call displayCell() function to draw the visible chess board and put appropriate piece onto the cells of the chess board.
		private void displayCell(ChessBoard chessBoard, GridPane pane, Boolean turn_board) {	
			int count = 0;
			final double s = 50; //Size of the rectangle in a cell of the displayed chess board
			//Draw the chess board with 8 x 8 cell 
			for (int i = 0; i < chessBoard.NUM_OF_ROW; i++) {
				count++;
				for (int j =0; j < chessBoard.NUM_OF_COLUMN; j++) {
					Rectangle r = new Rectangle(s,s,s,s);
					if(count % 2 == 0) {
						r.setFill(Color.BROWN);
					} else {
						r.setFill(Color.LIGHTYELLOW);
					}
					pane.add(r, j, i);
					count++;
				}
			}
			
			//Define image of pieces
			try {
			Image image_bb = new Image(Main.class.getResourceAsStream("/images/bb.gif"));
			Image image_wb = new Image(Main.class.getResourceAsStream("/images/wb.gif"));
	        Image image_wk = new Image(Main.class.getResourceAsStream("/images/wk.gif"));
	        Image image_bk = new Image(Main.class.getResourceAsStream("/images/bk.gif"));
	        Image image_wn = new Image(Main.class.getResourceAsStream("/images/wn.gif"));
	        Image image_bn = new Image(Main.class.getResourceAsStream("/images/bn.gif"));
	        Image image_wp = new Image(Main.class.getResourceAsStream("/images/wp.gif"));
	        Image image_bp = new Image(Main.class.getResourceAsStream("/images/bp.gif"));
	        Image image_wq = new Image(Main.class.getResourceAsStream("/images/wq.gif"));
	        Image image_bq = new Image(Main.class.getResourceAsStream("/images/bq.gif"));
	        Image image_wr = new Image(Main.class.getResourceAsStream("/images/wr.gif"));
	        Image image_br = new Image(Main.class.getResourceAsStream("/images/br.gif"));
			
	        //boolean turn_board = false; //used to thu nghiem turn 90 degree the chess board
	        int m, n;
	        //Scan the logical chessBoard and put the appropriate image according to the type of pieces.
			for (int i = 0; i < chessBoard.NUM_OF_ROW; i++) {
				for (int j = 0; j < chessBoard.NUM_OF_COLUMN; j++) {
					if (turn_boardList.get(0)) { //To turn chess board 90 
						m = i;
						n = j;
					} else {
						m = j;
						n = i;
					}
					if(chessBoard.board[i][j] != null) {
						if(chessBoard.board[i][j].getClass().isInstance(new Pawn(true, chessBoard))) {
							if(chessBoard.board[i][j].getColor()) {
								pane.add((new ImageView(image_wp)), m, n);
							} else {
								pane.add((new ImageView(image_bp)), m, n);
							}
						}
						if(chessBoard.board[i][j].getClass().isInstance(new King(true, chessBoard))) {
							if(chessBoard.board[i][j].getColor()) {
								pane.add((new ImageView(image_wk)), m, n);
							} else {
								pane.add((new ImageView(image_bk)), m, n);
							}
						}
						if(chessBoard.board[i][j].getClass().isInstance(new Queen(true, chessBoard))) {
							if(chessBoard.board[i][j].getColor()) {
								pane.add((new ImageView(image_wq)), m, n);
							} else {
								pane.add((new ImageView(image_bq)), m, n);
							}
						}
						if(chessBoard.board[i][j].getClass().isInstance(new Bishop(true, chessBoard))) {
							if(chessBoard.board[i][j].getColor()) {
								pane.add((new ImageView(image_wb)), m, n);
							} else {
								pane.add((new ImageView(image_bb)), m, n);
							}
						}
						if(chessBoard.board[i][j].getClass().isInstance(new Knight(true, chessBoard))) {
							if(chessBoard.board[i][j].getColor()) {
								pane.add((new ImageView(image_wn)), m, n);
							} else {
								pane.add((new ImageView(image_bn)), m, n);
							}
						}
						if(chessBoard.board[i][j].getClass().isInstance(new Rook(true, chessBoard))) {
							if(chessBoard.board[i][j].getColor()) {
								pane.add((new ImageView(image_wr)), m, n);
							} else {
								pane.add((new ImageView(image_br)), m, n);
							}
						}
					}
				}
			}
			
			} catch(Exception e) {
				e.printStackTrace();
			}
	}
		
		private void NumberingRowCol(GridPane pane) {
			//Numbering columns
			Label lb1 = new Label("      a              b             c             d             e             f             g             h");
			lb1.setTextFill(Color.BLUE);
			lb1.setStyle("-fx-font-weight: bold");
			pane.add(lb1, 0, 8, 8, 1);
			Label lb2 = new Label("");
			pane.add(lb2, 0, 9);
			
			//Numbering rows
			for (int i=0; i <8 ; i++) {
				Integer k = 8 - i;
				Label lb3 = new Label("  " + k.toString()); //"  " to make gap between the chessboard and the index number 
				lb3.setTextFill(Color.BLUE);
				lb3.setStyle("-fx-font-weight: bold");			
				pane.add(lb3, 9, 7-i);  //"7-i" to numbering from down to top
			}	
		}
		
		//Create labels to display the current player and Steps
		private void labelCurentPlayer(GridPane pane, List<String> colorList, List<Coord> histListWhite, List<Coord> histListBlack, List<LostPiece> lostPieceWhiteList, List<LostPiece> lostPieceBlackList) {
			Label lb_turn_1 = new Label("  Player:    ");
	        lb_turn_1.setStyle("-fx-font-weight: bold");
	        pane.add(lb_turn_1, 10, 0);
	        
	        //Do colorList duoc dinh nghia la bien thuc the do vay ko add khi khoi tao dc nen add o day.
	        //Lan dau thi no la phan tu 0, nhung lan 2 no se chuyen thanh phan tu 1..., nen no ko anh huong lenh get(0) cua lan thu 2
			colorList.add(color);	
	        TextField result = new TextField();
	        result.setPrefWidth(60);
			result.setEditable(false);
			result.setText(colorList.get(0));
			result.setStyle("-fx-text-fill: red; -fx-font-weight: bold");  //-fx-font-size: 16px; neu muon doi font size 
			pane.add(result, 11, 0);
	        
			//Label for historical steps
			Label lb_step_1 = new Label("          Steps " + "\n" + "     White    Black ");
	        lb_step_1.setStyle("-fx-font-weight: bold");
	        pane.add(lb_step_1, 10, 1, 2, 1);
	        
	        TextArea stepsWhite = new TextArea(); //historical steps of White will be displayed on this TextArea
	        stepsWhite.setPrefWidth(50);
	        stepsWhite.setPrefHeight(300);
	        stepsWhite.setWrapText(true);
	        stepsWhite.setEditable(false);  //srcDestList
	        stepsWhite.setText(histListWhite.toString());
	        stepsWhite.setStyle("-fx-font-size: 9px;");
	        pane.add(stepsWhite, 10, 2, 1, 6);
	        
	        TextArea stepsBlack = new TextArea(); //historical steps of Black will be displayed on this TextArea
	        stepsBlack.setPrefWidth(50);
	        stepsBlack.setPrefHeight(300);
	        stepsBlack.setWrapText(true);
	        stepsBlack.setEditable(false);  //srcDestList
	        stepsBlack.setText(histListBlack.toString()); //LUU Y CHI LAY TAM 
	        stepsBlack.setStyle("-fx-font-size: 9px;");
	        pane.add(stepsBlack, 11, 2, 1, 6); 
	        
	        //Create area for display lost pieces
	        Label lostBlackPiece = new Label("        Lost" + "\n" + "  black pieces:  ");
	        lostBlackPiece.setStyle("-fx-font-weight: bold");
	        pane.add(lostBlackPiece, 12, 0);
	        
	        TextArea lostBlackArea = new TextArea();
	        lostBlackArea.setPrefWidth(50);
	        lostBlackArea.setPrefHeight(150);
	        lostBlackArea.setWrapText(true);
	        lostBlackArea.setEditable(false);  //srcDestList
	        //lostBlackArea.setText(histListBlack.toString()); //LUU Y CHI LAY TAM 
	        lostBlackArea.setText(lostPieceBlackList.get(0).toString());
	        lostBlackArea.setStyle("-fx-font-size: 10px;");
	        pane.add(lostBlackArea, 12, 1, 1, 3);
	        
	        Label lostBlackWhite = new Label("        Lost" + "\n" + "  white pieces:  ");
	        lostBlackWhite.setStyle("-fx-font-weight: bold");
	        pane.add(lostBlackWhite, 12, 4);
	        
	        TextArea lostWhiteArea = new TextArea();
	        lostWhiteArea.setPrefWidth(50);
	        lostWhiteArea.setPrefHeight(150);
	        lostWhiteArea.setWrapText(true);
	        lostWhiteArea.setEditable(false);  //srcDestList
	        //losWhiteArea.setText(histListBlack.toString()); //LUU Y CHI LAY TAM  
	        lostWhiteArea.setText(lostPieceWhiteList.get(0).toString());
	        lostWhiteArea.setStyle("-fx-font-size: 10px;");
	        pane.add(lostWhiteArea, 12, 5, 1, 3);
	        
		}
		private void createSaveOpenExitButton(GridPane pane, List<ChessBoard> chessBoardList, List<String> colorList) {
			//Save Button
	  		Button saveBtn = new Button("Save");
	  		ImageView saveImv = new ImageView();
	  		Image saveImg = new Image(Main.class.getResourceAsStream("/images/save.jpg"));
	  		saveImv.setImage(saveImg);
	  		saveBtn.setGraphic(saveImv);
	  		//TODO more....
	  		
	  		saveBtn.setOnAction(e1 -> {
	  			try {
	  				FileOutputStream filestream = new FileOutputStream("savefile/game.dat");
	  				try {
	  					ObjectOutputStream os = new ObjectOutputStream(filestream);
	  					os.writeObject(chessBoardList.get(0));	//save cac o co logic
	  					os.writeObject(colorList.get(0));		//Luu giu mau quan co (nguoi choi) hien tai 
	  					os.writeObject(histListWhite);
	  					os.writeObject(histListBlack);
	  					os.writeObject(lostPieceWhiteList.get(0));
	  					os.writeObject(lostPieceBlackList.get(0));
	  					  					  					
	  					System.out.println("Written");			//histListWhite, histListBlack, lostPieceWhiteList, histListBlack.
	  					os.close();
	  				} catch (IOException e2) {
	  					// TODO Auto-generated catch block
	  					System.out.println("Exception in saving file");
	  					e2.printStackTrace();
	  				}
	  				
	  			} catch (FileNotFoundException e2) {
	  				// TODO Auto-generated catch block
	  				System.out.println("File not found!");
	  				e2.printStackTrace();
	  			}
	  		});
	  			
	  		//Open Button
			Button openBtn = new Button("Open");
			ImageView openImv = new ImageView();
			Image openImg = new Image(Main.class.getResourceAsStream("/images/open.png"));
			openImv.setImage(openImg);
			openBtn.setGraphic(openImv);
			//TODO more actions for the click into Open button.
			openBtn.setOnAction(e -> {
	            InputDialog dialog = new InputDialog("Please enter the opened file:");
	            dialog.getStage().showAndWait(); // This will not execute until the stage is closed, so it will give the correct result:
	            //System.out.println(dialog.getResult());
				try {
					FileInputStream filestream = new FileInputStream(dialog.getResult());
					try {
						ObjectInputStream os = new ObjectInputStream(filestream);
						try {
							ChessBoard gameBoard_s = (ChessBoard) os.readObject();	//Load logical chessBoard from the saved file
							String color_s = (String) os.readObject();		//Load saved color (player's color) from the saved file
							List<Coord> histListWhite_s = (List<Coord>) os.readObject();
							//histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList.
							List<Coord> histListBlack_s = (List<Coord>) os.readObject();
							LostPiece lostPieceWhite_s = (LostPiece) os.readObject();
							LostPiece lostPieceBlack_s = (LostPiece) os.readObject();
							
							chessBoardList.set(0, gameBoard_s);				
							displayCell (chessBoardList.get(0), pane, turn_boardList.get(0));		//Call display chessboard's cell function 
							
							colorList.set(0, color_s);	
													
							//List<Coord> histListWhite = (List<Coord>) histListWhite_s; //Neu de the nay thi se coi tao bien moi va ko update vao histListWhite da khai bao o dau class!!!!
							histListWhite = (List<Coord>) histListWhite_s;
							histListBlack = (List<Coord>) histListBlack_s;
							
							lostPieceWhiteList.set(0, lostPieceWhite_s);
							lostPieceBlackList.set(0, lostPieceBlack_s);												
							
							labelCurentPlayer(pane, colorList, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);				//Call display Player function
						} catch (ClassNotFoundException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
				} catch (FileNotFoundException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
	        });
			
			//Button Exit	
			Button exitBtn = new Button("Exit");
			ImageView exitImv = new ImageView();
			Image exitImg = new Image(Main.class.getResourceAsStream("/images/exit.gif"));
			exitImv.setImage(exitImg);
			exitBtn.setGraphic(exitImv);
			exitBtn.setOnAction(e -> {
			        Platform.exit();
			});
			
			//Button turn	
			turn_boardList.add(turn_board);
			Button turnBtn = new Button("Turn");
			ImageView turnImv = new ImageView();
			Image turnImg = new Image(Main.class.getResourceAsStream("/images/turn90.png"));
			turnImv.setImage(turnImg);
			turnBtn.setGraphic(turnImv);
			turnBtn.setOnAction(e -> {
				if(turn_boardList.get(0)) {
					turn_boardList.set(0, false);
				} else {
					turn_boardList.set(0, true);
				}
				displayCell (chessBoardList.get(0), pane, turn_boardList.get(0));		//Call display chessboard's cell function 
			});
			
			//New Button
			Button newBtn = new Button("New");
			newBtn.setOnAction(e -> {
				Game gameTest = new Game();
				chessBoardList.set(0, gameTest.cb);
				colorList.set(0,"White");
				turn_boardList.set(0,false);
				histListWhite.clear();
				histListBlack.clear();
				lostPieceWhiteList.set(0, new LostPiece(0, 0, 0, 0, 0, 0, true));
				lostPieceBlackList.set(0, new LostPiece(0, 0, 0, 0, 0, 0, false));
				
				displayCell (chessBoardList.get(0), pane, turn_boardList.get(0));		//Call display chessboard's cell function 
				labelCurentPlayer(pane, colorList, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
			});
			
			//Dua cac button vao cac o tren GridPane
			pane.add(newBtn, 0, 10, 2, 1);
	    	GridPane.setHalignment(newBtn, HPos.CENTER);
			pane.add(saveBtn, 2, 10, 2, 1);
	    	GridPane.setHalignment(saveBtn, HPos.CENTER);
	    	pane.add(openBtn, 4, 10, 2, 1);
	    	GridPane.setHalignment(openBtn, HPos.CENTER);
	    	pane.add(exitBtn, 6, 10, 2, 1);
	    	GridPane.setHalignment(exitBtn, HPos.CENTER);
	    	pane.add(turnBtn, 10, 10, 2, 1);
	    	GridPane.setHalignment(turnBtn, HPos.CENTER);
			
		}
		//Tao 1 cua so moi de dua thong tin vao
		private static class InputDialog {
	        private final Stage stage ;
	        private final TextField input ;
	        
	        InputDialog(String string) {
	            input = new TextField(string);
	            Button close = new Button("Close");
	            Label label = new Label("Enter the open file:");
	            VBox root = new VBox(5);
	            root.setAlignment(Pos.CENTER);
	            root.getChildren().addAll(label, input, close);
	            Scene scene = new Scene(root, 200, 100);
	            stage = new Stage();
	            stage.initModality(Modality.APPLICATION_MODAL);
	            close.setOnAction(e -> stage.hide());
	            stage.setScene(scene);
	        }
	        Stage getStage() {
	            return stage ;
	        }
	        String getResult() {
	            return input.getText();
	        }
	    }
		
		private void showAlerInvalidInput(String str) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(str);

			// Header Text: null
			alert.setHeaderText(null);
			alert.setContentText(str);

			alert.showAndWait();
		}
		
		public static String colorToggle(String color){
	        if(color.equals("White")){
	            return "Black";
	        }

	        return "White";
	    }
	
	public static void main(String[] args) {
		launch(args);
	}
}