package application;
	
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;
import java.text.DecimalFormat;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;	
import javafx.stage.FileChooser.ExtensionFilter;

public class Main extends Application {		
	List<Boolean> turn_boardList = new ArrayList<Boolean>();	
	List<Integer> srcDestList = new ArrayList<Integer>();	//List to keep source and destination coordinates
	List<Coord> histListWhite  = new ArrayList<Coord>();	//List to keep historical steps of White
	List<Coord> histListBlack  = new ArrayList<Coord>(); 	//List to keep historical steps of Black	
	List<LostPiece> lostPieceWhiteList = new ArrayList<LostPiece>(); //List to keep lost pieces
	List<LostPiece> lostPieceBlackList = new ArrayList<LostPiece>();	
	List<LostPiece> livingWhiteList = new ArrayList<LostPiece>();
	List<LostPiece> livingBlackList = new ArrayList<LostPiece>();	
	List<Game> gameList = new ArrayList<Game>();		//List to keep the game	
	List<ChessBoard> chessBoardList = new ArrayList<ChessBoard>();	
	List<HashSet<Coord>> pMoveList = new ArrayList<HashSet<Coord>>();	
	List<Integer> countList = new ArrayList<Integer>();	
	List<Double> whiteTimeList = new ArrayList<Double>();
	List<Double> blackTimeList = new ArrayList<Double>();	
	List<GridPane> paneList = new ArrayList<GridPane>();	
	Label lb1; //For numbering the column of the chess board
	boolean turn_board = false; //used to thu nghiem turn 90 degree the chess board
	
	@Override
	public void start(Stage primaryStage) {
		turn_board = false;				//Create turn variable, false is meaning no turn. 
		turn_boardList.add(turn_board); //The save this value to first element of turn_board list	
		LostPiece lostPieceWhite = new LostPiece(0, 0, 0, 0, 0, 0, true);	//Initial lost piece object and save to a list.
		lostPieceWhiteList.add(lostPieceWhite);
		LostPiece lostPieceBlack = new LostPiece(0, 0, 0, 0, 0, 0, false);
		lostPieceBlackList.add(lostPieceBlack);		
		LostPiece livingPieceWhite = new LostPiece(0, 0, 0, 0, 0, 0, true);		
		livingWhiteList.add(livingPieceWhite);
		LostPiece livingPieceBlack = new LostPiece(0, 0, 0, 0, 0, 0, false);		
		livingBlackList.add(livingPieceBlack);		
		Double whiteTime = (double) 0;
		Double blackTime = (double) 0;
		whiteTimeList.add(whiteTime);
		blackTimeList.add(blackTime);		
		try {		
			//Create logical chess board
			Game gameTest = new Game();
			gameList.add(gameTest);
			chessBoardList.add(gameTest.cb);
			//Dialog to input configBoard is 1 or 2. Default 2.
			InputDialog dialog = new InputDialog("2");
			dialog.getStage().showAndWait();
			int config;
			if(dialog.getResult().substring(0,1).contains("1")) {
				config = 1;
			} else {
				config= 2 ;
			}
			chessBoardList.get(0).setConfigBoard(config);			
			// Define grid of displayed chess board
			GridPane pane = new GridPane();	
			paneList.add(pane);
			displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));  //Tham so: turn_BoardList de kiem tra chess board co quay ko?
			NumberingRowCol(pane);
			calculateLivingPieces();			
			labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
			createSaveOpenExitButton(primaryStage, pane,chessBoardList);
			
			Integer count_select = 1;		//Variable to identify the order of mouse click (Bien de xac dinh lan bam chuot 1 hay 2 trong moi buoc di) 
			countList.add(count_select);
			for (int i=0; i<4; i++) {	//Initialize 4 elements of srcDestList. 02 for x,y coordinates of source and 02 for x,y coordinates of destination
				srcDestList.add(i, 0);
			}		
			HashSet<Coord> pMove = null;	//HashSet contains possible move
			pMoveList.add(pMove);			//Must add the first element, otherwise an error will be happened
			//De tim va ghi cac quan co the di va ghi vao list coorPieceMovable (thuoc ChessBoard); Dong thoi kiem tra xem co bi quan dich chieu ko
  			//Likes lines from 135 - 141 in class Game (courseOfTheGame()).			
			markPossibleMove(gameList, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
			calculateLivingPieces();
			labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList); //Phai them goi ham label de update duoc king status len GUI		
			markCell(gameList, pane, "O");
			long start_time = System.nanoTime();
			System.out.println("Start time 0: " + start_time/1000000000);
			List<Long> start_time_list = new ArrayList<Long>();
			//Start time of a first step of White player
			start_time_list.add(start_time);
			
			pane.setOnMousePressed(new EventHandler<MouseEvent>() { 
			      @Override public void handle(MouseEvent event) {	
			    	  int c, r;
			    	  if(!turn_board) {
			    	  		c = (int)Math.round(event.getX())/50;  //Column
			    	  		r = (int)Math.round(event.getY())/50;  //Row			    					    				    	
			    	  } else {
			    		  	r = (int)Math.round(event.getX())/50;  //Column
					    	c = (int)Math.round(event.getY())/50;  //Row
			    	  }				    	
			    	  if (chessBoardList.get(0).getConfigBoard()== 1) {
			    		  r = 7 -r;
			    		  c = 7 -c;
			    	  }			    	  
				      if (c <=7 && r <=7) {
				    		  Coord coord_src_dest = new Coord(r, c);				    		  
				    		  int number = countList.get(0);	//Variable de kiem soat lan bam chuot thu may
				    		  switch (number) {
				    		  case 1:			    			  
				    			  if (!gameList.get(0).cb.coorPieceMovable.contains(coord_src_dest)) {	//Neu quan co dc chon ko thuoc cac quan co di dc thi ko lam gi ca 			    				  
				    			  }  //Like function select() in class Game
				    			  else {				    				  				    				
				    				  srcDestList.set(0,(Integer)r);
				    				  srcDestList.set(1,(Integer)c);				    				  
				    				  //Tao pMove chua cac vi tri ma quan co duoc cho co the di				    				  
				    				  HashSet<Coord> pMove = chessBoardList.get(0).board[r][c].getAllowedMove(); //(Like line 94 in class Game) Sua lai dung ham getAllowedMove() chu ko dung possible move.
				    				  pMoveList.set(0, pMove);   //Add to pMoveList so that can be used in the code of second mouse click			    				     				  
				    				  displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));
				    				  //Mark pieces that are movable
				    				  for (Coord coord : pMoveList.get(0)) {
				    					  Label labelGo = new Label("X");
			    							 labelGo.setTextFill(Color.BLUE);
			    							 labelGo.setStyle("-fx-font-weight: bold");
			    							 if (chessBoardList.get(0).getConfigBoard()== 2) {
			    								 pane.add(labelGo, coord.getC(), coord.getR());
			    							 }
			    							 if (chessBoardList.get(0).getConfigBoard()== 1) {
			    								 pane.add(labelGo, 7-coord.getC(), 7-coord.getR());
			    							 }
				    				  }				    			  
				    			  Integer click_1 = 2;		//The first mouse click is successful. Change the click_1 to 2 so that the next click will be consider second click
				    			  countList.set(0, click_1);
				    			  }
				    			  break;
				    		  case 2:				    			  
				    			  if (!pMoveList.get(0).contains(coord_src_dest)) { //Neu lan bam thu 2 ko co trong danh sach co the di dc
				    				  if (r == srcDestList.get(0) && c == srcDestList.get(1)) { //If src = dest --> cancel, choose again.
				    					  showAlerInvalidInput("Choose a movable piece again.");
								          displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));
								          markCell(gameList, pane, "O");
				    					  Integer click_1 = 1;
				    					  countList.set(0, click_1);
				    				  } else {  	
				    			  		showAlerInvalidInput("Invalid dest!");
				    				  }
				    			  } else {				    				 
				    				  //System.out.println("r: " + r + "; c: " + c);				    					    				 				    				 				    				  
				    				  srcDestList.set(2,(Integer)r);	
				    				  srcDestList.set(3,(Integer)c);				    				  
				    				  Coord destMove = new Coord(r, c);	//Creat a new Coord object (keeps the r and c) that will be used in checking with possible move list.				    					 			    					  			    				  
				    				  //Lenh duoi muc dich de di chuyen quan tu src den dest va cap nhat lai toa do cac quan den hoac trang sau khi di chuyen
				    				  chessBoardList.get(0).update(new Coord(srcDestList.get(0), srcDestList.get(1)), destMove); //Like line 176 of class Game.
		    						  calculateLostPiece();		   // Calculate lost (captured) pieces.	 						  		    						  
				    				  if (chessBoardList.get(0).board[r][c].getColor() == true) { 	//save to historical steps list		    					  
				    					  histListWhite.add(new Coord(srcDestList.get(0), srcDestList.get(1)));
				    					  histListWhite.add(new Coord(srcDestList.get(2), srcDestList.get(3)));				    					 			    					  
				    				  } else {				    					 
				    					  histListBlack.add(new Coord(srcDestList.get(0), srcDestList.get(1)));
				    					  histListBlack.add(new Coord(srcDestList.get(2), srcDestList.get(3)));				    					 
				    				  }				    				  
				    				  long end_time = System.nanoTime(); //End time of a turn and calculate cumulated playing time
				    				  if (gameList.get(0).getTurn()) {
				    					  whiteTimeList.set(0, (double)Math.round(whiteTimeList.get(0) + (double) (end_time - start_time_list.get(0))/1000000000));
				    				  } else {
				    					  blackTimeList.set(0, (double)Math.round(blackTimeList.get(0) + (double) (end_time - start_time_list.get(0))/1000000000));
				    				  }				    				  
							          gameList.get(0).setnbCoup(); 	//Increase 1 for nbCoup; Likes line 181 of class Game
							          gameList.get(0).setTurn();	//Change the turn; Likes line 182 of class Game							          
							          long start_time = System.nanoTime(); //Start time of a turn
							          start_time_list.set(0, start_time);							          
							          markPossibleMove(gameList, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);							          
							          displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));
							          markCell(gameList, pane, "O");
							          System.out.println("Tessssst: " + gameList.get(0).cb.getCoorPieceMovable());
							          calculateLivingPieces();
							          labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);							          
							          //Check won/lost/draw conditions - likes getEnd() in class Game
					    			  if (gameList.get(0).cb.getCoorPieceMovable().isEmpty()) { //Neu ko co quan co nao di chuyen dc
							  		  		int kq = gameList.get(0).getEnd();
							  		  			switch(kq) {
							  		  				case 0:	// this.getTurn() && this.whitePlayer.getMyKingStatus() == true
							  		  					showAlerInvalidInput("Black Won!");
							  		  					break;
							  		  				case 1: //!this.getTurn()) && this.blackPlayer.getMyKingStatus() == true
							  		  					showAlerInvalidInput("White Won!");
							  		  					break;
							  		  				case 2: // ko thuoc 2 truong hop tren
							  		  					showAlerInvalidInput("Draw!");
							  		  					break;
							  		  			}
							  		  	}    			  					    		
					    			  Integer click_2 = 1;			//Set the count of mouse to 1.
							          countList.set(0, click_2);							          				    						    			  				    			  
				    			 }				    			  
				    			  break;
				    		  default:
				    			  break;
				    		  }
				    	  }
			    	} 
			 });			
			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			primaryStage.setTitle("ChessBoard Projet");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
		//draw the visible chess board and put appropriate piece onto the cells of the chess board.
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
			
	        int m, n;
	        //Scan the logical chessBoard and put the appropriate image according to the type of pieces.
	        if (chessBoardList.get(0).getConfigBoard()== 2) {
			for (int i = 0; i < chessBoard.NUM_OF_ROW; i++) {
				for (int j = 0; j < chessBoard.NUM_OF_COLUMN; j++) {
					if (turn_boardList.get(0)) {
						m = i;
						n = 7-j;
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
	        }
	        
	        if (chessBoardList.get(0).getConfigBoard()== 1) {
				for (int i = 0; i < chessBoard.NUM_OF_ROW; i++) {
					for (int j = 0; j < chessBoard.NUM_OF_COLUMN; j++) {
						if (turn_boardList.get(0)) {
							m = i;
							n = 7-j;
						} else {
							m = j;
							n = i;
						}
						if(chessBoard.board[i][j] != null) {
							if(chessBoard.board[i][j].getClass().isInstance(new Pawn(true, chessBoard))) {
								if(chessBoard.board[i][j].getColor()) {
									pane.add((new ImageView(image_wp)), 7-m, 7-n);
								} else {
									pane.add((new ImageView(image_bp)), 7-m, 7-n);
								}
							}
							if(chessBoard.board[i][j].getClass().isInstance(new King(true, chessBoard))) {
								if(chessBoard.board[i][j].getColor()) {
									pane.add((new ImageView(image_wk)),  7-m, 7-n);
								} else {
									pane.add((new ImageView(image_bk)),  7-m, 7-n);
								}
							}
							if(chessBoard.board[i][j].getClass().isInstance(new Queen(true, chessBoard))) {
								if(chessBoard.board[i][j].getColor()) {
									pane.add((new ImageView(image_wq)), 7-m, 7-n);
								} else {
									pane.add((new ImageView(image_bq)),  7-m, 7-n);
								}
							}
							if(chessBoard.board[i][j].getClass().isInstance(new Bishop(true, chessBoard))) {
								if(chessBoard.board[i][j].getColor()) {
									pane.add((new ImageView(image_wb)),  7-m, 7-n);
								} else {
									pane.add((new ImageView(image_bb)),  7-m, 7-n);
								}
							}
							if(chessBoard.board[i][j].getClass().isInstance(new Knight(true, chessBoard))) {
								if(chessBoard.board[i][j].getColor()) {
									pane.add((new ImageView(image_wn)),  7-m, 7-n);
								} else {
									pane.add((new ImageView(image_bn)),  7-m, 7-n);
								}
							}
							if(chessBoard.board[i][j].getClass().isInstance(new Rook(true, chessBoard))) {
								if(chessBoard.board[i][j].getColor()) {
									pane.add((new ImageView(image_wr)),  7-m, 7-n);
								} else {
									pane.add((new ImageView(image_br)),  7-m, 7-n);
								}
							}
						   }
						}
					}
		        }
	        
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		//Numbering rows and columns of the visible chess board
		private void NumberingRowCol(GridPane pane) {
			//Numbering columns
			if (chessBoardList.get(0).getConfigBoard() == 2) {
				lb1 = new Label("      a              b             c             d             e             f             g             h");
			} else {
				lb1 = new Label("      h              g             f             e             d             c             b             a");			
			}
			lb1.setTextFill(Color.BLUE);
			lb1.setStyle("-fx-font-weight: bold");			
			pane.add(lb1, 0, 8, 8, 1);
			Label lb2 = new Label("");
			pane.add(lb2, 0, 9);		
			//Numbering rows
			for (int i=0; i <8 ; i++) {
				Integer k = i + 1; 		//Integer k = i; //Danh so tu 0
				Label lb3 = new Label("  " + k.toString()); //"  " to make gap between the chessboard and the index number 
				lb3.setTextFill(Color.BLUE);
				lb3.setStyle("-fx-font-weight: bold");
				if (chessBoardList.get(0).getConfigBoard() == 2) {
					pane.add(lb3, 9, 7-i);  //"7-i" to numbering from down to top 	//pane.add(lb3, 9, i);	//Danh so tu 0 va tu tren xuong
				}else {
					pane.add(lb3, 9, i);
				}
			}	
		}
		
		//Display all labels  
		private void labelCurentPlayer(GridPane pane, List<Coord> histListWhite, List<Coord> histListBlack, List<LostPiece> lostPieceWhiteList, List<LostPiece> lostPieceBlackList) {
			//Display the current player (Black or White).
			Label lb_turn_1 = new Label("  Player:    ");
	        lb_turn_1.setStyle("-fx-font-weight: bold");
	        pane.add(lb_turn_1, 10, 0);
	        
	        TextField result = new TextField();
	        result.setPrefWidth(60);
			result.setEditable(false);
			String playerString;
			if (gameList.get(0).getTurn()) {
				playerString = "White";
			} else {
				playerString = "Black";
			}
			result.setText("" + playerString);
			result.setStyle("-fx-text-fill: red; -fx-font-weight: bold");  //-fx-font-size: 16px; neu muon doi font size 
			pane.add(result, 11, 0);			
			//Label for historical steps
			Label lb_step_1 = new Label("          Steps " + "\n" + "     White    Black ");
	        lb_step_1.setStyle("-fx-font-weight: bold");
	        pane.add(lb_step_1, 10, 1, 2, 1);
	        //White
	        TextArea stepsWhite = new TextArea(); //historical steps of White will be displayed on this TextArea
	        formatTextArea(stepsWhite, 50, 300);
	        String histListWhiteString = convertHistList(histListWhite.toString());	
	        stepsWhite.setText(histListWhiteString);
	        pane.add(stepsWhite, 10, 2, 1, 6);
	        //Black
	        TextArea stepsBlack = new TextArea(); //historical steps of Black will be displayed on this TextArea
	        formatTextArea(stepsBlack, 50, 300);
	        String histListBlackString = convertHistList(histListBlack.toString());	        
	        stepsBlack.setText(histListBlackString);       
	        pane.add(stepsBlack, 11, 2, 1, 6);         
	        //Create area for display lost pieces
	        //Black
	        Label lostBlackPiece = new Label("        Lost" + "\n" + "  black pieces:  ");
	        lostBlackPiece.setStyle("-fx-font-weight: bold");
	        pane.add(lostBlackPiece, 12, 0);
	        TextArea lostBlackArea = new TextArea();
	        formatTextArea(lostBlackArea, 50, 100);
	        //lostBlackArea.setStyle("-fx-font-size: 11px;");
	        lostBlackArea.setText(lostPieceBlackList.get(0).toString());        
	        pane.add(lostBlackArea, 12, 1, 1, 2);
	        //White
	        Label lostWhitePiece = new Label("        Lost" + "\n" + "  white pieces:  ");
	        lostWhitePiece.setStyle("-fx-font-weight: bold");
	        pane.add(lostWhitePiece, 12, 4);
	        TextArea lostWhiteArea = new TextArea();
	        formatTextArea(lostWhiteArea, 50, 100);
	        lostWhiteArea.setText(lostPieceWhiteList.get(0).toString());        
	        pane.add(lostWhiteArea, 12, 5, 1, 2);	        
	        //Create are for King status
	        //Black
	        Label blackKingStatus = new Label("Black King Status: ");
	        blackKingStatus.setStyle("-fx-font-weight: bold");
	        pane.add(blackKingStatus, 12, 3, 1, 1);	        
	        TextArea blackKingStatusArea = new TextArea();
	        formatTextArea(blackKingStatusArea, 50, 50);
	        blackKingStatusArea.setText("\n" + "       " + gameList.get(0).blackPlayer.getMyKingStatus());
	        if (gameList.get(0).blackPlayer.getMyKingStatus()== true) {
	        	blackKingStatusArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
	        } else {
	        	blackKingStatusArea.setStyle("-fx-font-weight: bold;");
	        }
	        pane.add(blackKingStatusArea, 13, 3, 1, 1);	        
	        //White
	        Label whiteKingStatus = new Label("White King Status: ");
	        whiteKingStatus.setStyle("-fx-font-weight: bold");
	        pane.add(whiteKingStatus, 12, 7, 1, 1);	        
	        TextArea whiteKingStatusArea = new TextArea();
	        formatTextArea(whiteKingStatusArea, 50, 50);
	        whiteKingStatusArea.setText("\n" + "       " + gameList.get(0).whitePlayer.getMyKingStatus());
	        if (gameList.get(0).whitePlayer.getMyKingStatus()== true) {
	        	whiteKingStatusArea.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
	        } else {
	        	whiteKingStatusArea.setStyle("-fx-font-weight: bold");
	        }
	        pane.add(whiteKingStatusArea, 13, 7, 1, 1);	        
	        //Living pieces
	        //Black
	        Label livingBlackPiece = new Label("        Living" + "\n" + "  black pieces:  ");
	        livingBlackPiece.setStyle("-fx-font-weight: bold");
	        pane.add(livingBlackPiece, 13, 0);
	        TextArea livingBlackArea = new TextArea();
	        formatTextArea(livingBlackArea, 50, 100);
	        livingBlackArea.setText(livingBlackList.get(0).toString());	        
	        pane.add(livingBlackArea, 13, 1, 1, 2);
	        //White
	        Label livingWhitePiece = new Label("        Living" + "\n" + "  white pieces:  ");
	        livingWhitePiece.setStyle("-fx-font-weight: bold");
	        pane.add(livingWhitePiece, 13, 4);
	        TextArea livingWhiteArea = new TextArea();
	        formatTextArea(livingWhiteArea, 50, 100);
	        livingWhiteArea.setText(livingWhiteList.get(0).toString());        
	        pane.add(livingWhiteArea, 13, 5, 1, 2);	   	        
	        //Display time
	        //Balck
	        Label timeBlackLabel =  new Label("Black time");
	        timeBlackLabel.setStyle("-fx-font-weight: bold");
	        pane.add(timeBlackLabel, 12, 9);
	        TextArea timeBlackArea = new TextArea();
	        formatTextArea(timeBlackArea, 50, 50);
	        timeBlackArea.setText(blackTimeList.get(0).toString());	        
	        pane.add(timeBlackArea, 12, 10);
	        //White
	        Label timeWhiteLabel =  new Label("White time");
	        timeWhiteLabel.setStyle("-fx-font-weight: bold");
	        pane.add(timeWhiteLabel, 13, 9);
	        TextArea timeWhiteArea = new TextArea();
	        formatTextArea(timeWhiteArea, 50, 50);
	        timeWhiteArea.setText(whiteTimeList.get(0).toString());	        
	        pane.add(timeWhiteArea, 13, 10);
		}
		
		private void formatTextArea(TextArea textArea, int w, int h) {
			textArea.setPrefWidth(w);
			textArea.setPrefHeight(h);
			textArea.setWrapText(true);
			textArea.setEditable(false);
			textArea.setStyle("-fx-font-size: 10px;");
		}
		
		
		
		//Convert history steps list into string and plus "\n" after each steps.
		public String convertHistList(String strList) {
	        char[] ch = null;
	        String str = "";
	        String string = strList.substring(1, strList.length()-1);
	        if (string.length() > 2) {	        	
	        	ch = string.toCharArray();
	        	for (int i =0 ; i <ch.length; i++) {
	        		if (i==6 || (i>0 && (i-6)%8==0)) {
	        			str = str + ch[i] +"\n";
	        		} else {
	        			str = str + ch[i];
	        		}	        		
	        	}	        	
	        }
	        return str;
		}
		//Calculate captured (lost) pieces based on the capturedPieces list.
		public void calculateLostPiece() {
			LostPiece tempWhite = new LostPiece(0, 0, 0, 0, 0, 0, true);			
			lostPieceWhiteList.set(0, tempWhite);
			for (Piece piece :  gameList.get(0).whitePlayer.getCapturedPieces()) {
				if (piece instanceof Pawn) {
					lostPieceWhiteList.get(0).pawnNum +=1;
				}
				if (piece instanceof Rook) {
					lostPieceWhiteList.get(0).rookNum +=1;
				}
				if (piece instanceof Knight) {
					lostPieceWhiteList.get(0).knightNum +=1;
				}
				if (piece instanceof Bishop) {
					lostPieceWhiteList.get(0).bishopNum +=1;
				}
				if (piece instanceof Queen) {
					lostPieceWhiteList.get(0).queenNum +=1;
				}
				if (piece instanceof King) {
					lostPieceWhiteList.get(0).kingNum +=1;
				}
			}			
			LostPiece tempBlack = new LostPiece(0, 0, 0, 0, 0, 0, turn_board);			
			lostPieceBlackList.set(0, tempBlack);
			for (Piece piece :  gameList.get(0).blackPlayer.getCapturedPieces()) {
				if (piece instanceof Pawn) {
					lostPieceBlackList.get(0).pawnNum +=1;
				}
				if (piece instanceof Rook) {
					lostPieceBlackList.get(0).rookNum +=1;
				}
				if (piece instanceof Knight) {
					lostPieceBlackList.get(0).knightNum +=1;
				}
				if (piece instanceof Bishop) {
					lostPieceBlackList.get(0).bishopNum +=1;
				}
				if (piece instanceof Queen) {
					lostPieceBlackList.get(0).queenNum +=1;
				}
				if (piece instanceof King) {
					lostPieceBlackList.get(0).kingNum +=1;
				}
			}		  
		}
		//Calculate living pieces by scan the current board.
		public void calculateLivingPieces() {
			LostPiece tempWhitePieces = new LostPiece(0, 0, 0, 0, 0, 0, true);
			LostPiece tempBlackPieces = new LostPiece(0, 0, 0, 0, 0, 0, false);
			livingWhiteList.set(0, tempWhitePieces);
			livingBlackList.set(0, tempBlackPieces);			
	        for (int i = 0; i < 8; i++) {
	        	for (int j = 0; j < 8; j++) {	        		
	        		if (gameList.get(0).cb.board[i][j] != null) {
	        			//White	        			
	        			if (gameList.get(0).cb.board[i][j].toString().contains("R")) { 				    							  				    							  	        				
	        				livingWhiteList.get(0).rookNum +=1; 		        			
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("C")) { 				    							  				    							  
		        			livingWhiteList.get(0).knightNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("B")) { 				    							  				    							  
		        			livingWhiteList.get(0).bishopNum +=1;
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("Q")) { 				    							  				    							  
		        			livingWhiteList.get(0).queenNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("K")) { 				    							  				    							  
		        			livingWhiteList.get(0).kingNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("P")) { 				    							  				    							  
		        			livingWhiteList.get(0).pawnNum +=1; 
						  }		        		
		        		//Black
		        		if (gameList.get(0).cb.board[i][j].toString().contains("r")) { 				    							  				    							  
		        			livingBlackList.get(0).rookNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("c")) { 				    							  				    							  
		        			livingBlackList.get(0).knightNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("b")) { 				    							  				    							  
		        			livingBlackList.get(0).bishopNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("q")) { 				    							  				    							  
		        			livingBlackList.get(0).queenNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("k")) { 				    							  				    							  
		        			livingBlackList.get(0).kingNum +=1; 
						  }
		        		if (gameList.get(0).cb.board[i][j].toString().contains("p")) { 				    							  				    							  
		        			livingBlackList.get(0).pawnNum +=1; 
						  }						  
	        		}        		
	        	}
	        }
		}		
		//Create buttons modules
		private void createSaveOpenExitButton(Stage primaryStage, GridPane pane, List<ChessBoard> chessBoardList) {		
			//Save Button
	  		Button saveBtn = new Button("Save");
	  		ImageView saveImv = new ImageView();
	  		Image saveImg = new Image(Main.class.getResourceAsStream("/images/save.jpg"));
	  		saveImv.setImage(saveImg);
	  		saveBtn.setGraphic(saveImv);  		
	        FileChooser fileChooser = new FileChooser();
	        fileChooser.setTitle("Save");
	        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));		
	  		saveBtn.setOnAction(e1 -> {
	  			try {
	  				File file = fileChooser.showSaveDialog(primaryStage);
	  				FileOutputStream filestream = new FileOutputStream(file);	  				
	  				try {
	  					ObjectOutputStream os = new ObjectOutputStream(filestream);
	  					os.writeObject(gameList.get(0));		//save current gameTest object.
	  					os.writeObject(chessBoardList.get(0));	//save cac o co logic
	  					os.writeObject(histListWhite);
	  					os.writeObject(histListBlack);
	  					os.writeObject(lostPieceWhiteList.get(0));
	  					os.writeObject(lostPieceBlackList.get(0));
	  					os.writeObject(whiteTimeList);
	  					os.writeObject(blackTimeList);
	  					os.writeObject(chessBoardList.get(0).getConfigBoard());
	  					showAlerInvalidInput("Written!");	
	  					os.close();
	  				} catch (IOException e2) {
	  					System.out.println("Exception in saving file");
	  					e2.printStackTrace();
	  				}	  				
	  			} catch (FileNotFoundException e2) {
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
			//Creating a File chooser	  		
	        FileChooser fileChooserOpen = new FileChooser();
	        fileChooser.setTitle("Open");
	        fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));	        			
	        openBtn.setOnAction(e -> {
				try {	
					String currentPath = Paths.get(".").toAbsolutePath().normalize().toString();  //To set default open directory
			        fileChooserOpen.setInitialDirectory(new File(currentPath));
					File fileOpen = fileChooser.showOpenDialog(primaryStage);
					FileInputStream filestream = new FileInputStream(fileOpen);
					try {						
						ObjectInputStream os = new ObjectInputStream(filestream);
						try {
							Game gameTest_s = (Game) os.readObject();
							ChessBoard gameBoard_s = (ChessBoard) os.readObject();	//Load logical chessBoard from the saved file
							List<Coord> histListWhite_s = (List<Coord>) os.readObject();
							List<Coord> histListBlack_s = (List<Coord>) os.readObject();
							LostPiece lostPieceWhite_s = (LostPiece) os.readObject();
							LostPiece lostPieceBlack_s = (LostPiece) os.readObject();
							List<Double> whiteTimeList_s = (List<Double>) os.readObject();
							List<Double> blackTimeList_s = (List<Double>) os.readObject();
							Integer configBoard_s = (Integer) os.readObject();
							gameList.set(0, gameTest_s);
							chessBoardList.set(0, gameBoard_s);				
							histListWhite = (List<Coord>) histListWhite_s;
							histListBlack = (List<Coord>) histListBlack_s;							
							lostPieceWhiteList.set(0, lostPieceWhite_s);
							lostPieceBlackList.set(0, lostPieceBlack_s);
							whiteTimeList = whiteTimeList_s;
							blackTimeList = blackTimeList_s;
							chessBoardList.get(0).setConfigBoard(configBoard_s);
							markPossibleMove(gameList, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
					        paneList.get(0).getChildren().remove(lb1);
					        NumberingRowCol(paneList.get(0));
					        labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);					          
					        displayCell(chessBoardList.get(0), pane, turn_boardList.get(0)); //Call display chessboard's cell function 					          
					        markCell(gameList, pane, "O");					          
						} catch (ClassNotFoundException e2) {
							e2.printStackTrace();
						}
					} catch (IOException e2) {
						e2.printStackTrace();
					}
				} catch (FileNotFoundException e2) {					
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
				Game gameTest2 = new Game();
				gameList.set(0, gameTest2);   //For test
				chessBoardList.set(0, gameTest2.cb);
				turn_boardList.set(0,false);
				histListWhite.clear();
				histListBlack.clear();
				lostPieceWhiteList.set(0, new LostPiece(0, 0, 0, 0, 0, 0, true));
				lostPieceBlackList.set(0, new LostPiece(0, 0, 0, 0, 0, 0, false));
				whiteTimeList.set(0,(double) 0);
				blackTimeList.set(0,(double) 0);
				HashSet<Coord> pMove = null;	//HashSet contains possible move
				pMoveList.add(pMove);	
				countList.set(0,1);		//Neu ko co khi 1 ben win, chon New button, sau do chon quan co di se bao Invalid input				
				InputDialog dialog = new InputDialog("2");
				dialog.getStage().showAndWait();
				int config;
				if(dialog.getResult().substring(0,1).contains("1")) { config = 1;
				} else { config= 2 ;
				}
				chessBoardList.get(0).setConfigBoard(config);
				markPossibleMove(gameList, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);				
				paneList.get(0).getChildren().remove(lb1);				
				NumberingRowCol(paneList.get(0));
				labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);		          
		        displayCell(chessBoardList.get(0), pane, turn_boardList.get(0));
		        markCell(gameList, pane, "O");		          							
			});			
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
		//Identify pieces that are movable and the status of current King.
		public void markPossibleMove(List<Game> gameList, GridPane pane, List<Coord> histListWhite, List<Coord> histListBlack, 
									 List<LostPiece> lostPieceWhiteList, List<LostPiece> lostPieceBlackList) {
			if(gameList.get(0).getTurn()) {  //Like lines 135-141 of courseOfTheGame() function (Game class)
				  gameList.get(0).cb.coorPieceMovable(gameList.get(0).whitePlayer.coordOfMyPieces, gameList.get(0).getTurn());;  
				  gameList.get(0).cb.updateCheckStatusking(gameList.get(0).blackPlayer.coordOfMyPieces, gameList.get(0).getTurn());;				    					  
			  } else {
				  gameList.get(0).cb.coorPieceMovable(gameList.get(0).blackPlayer.coordOfMyPieces, gameList.get(0).getTurn());;
				  gameList.get(0).cb.updateCheckStatusking(gameList.get(0).whitePlayer.coordOfMyPieces, gameList.get(0).getTurn());;				    					  
			  }		  	
		}
		//Mark pieces that are movable ("O").
		public void markCell(List<Game> gameList, GridPane pane, String string ) {
			for (Coord coord : gameList.get(0).cb.getCoorPieceMovable()) {
				  Label labelGo = new Label(string);
					 labelGo.setTextFill(Color.BLUE);
					 labelGo.setStyle("-fx-font-weight: bold");
					 if(chessBoardList.get(0).getConfigBoard()== 2) {
					 pane.add(labelGo, coord.getC(), coord.getR());
					 }
					 if(chessBoardList.get(0).getConfigBoard()== 1) {
						 pane.add(labelGo, 7-coord.getC(), 7-coord.getR());
					 }
			 }	
		}
		//Show alert messages
		private void showAlerInvalidInput(String str) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle(str);
			alert.setHeaderText(null);
			alert.setContentText(str);
			alert.showAndWait();
		}		
		//Create a new window that allow user to type input data such as the configBoard.
		private static class InputDialog {
		       private final Stage stage ;
		       private final TextField input ;			        
		       InputDialog(String string) {
		           input = new TextField(string);
		           Button close = new Button("Submit");
		           Label label = new Label("Enter configBoard (1 or 2):");
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
		        	if (input.getText().length() > 0) {
		        		return input.getText();
		        	} else {
		        		return "2";
		        	}
			     }
		}	
}
