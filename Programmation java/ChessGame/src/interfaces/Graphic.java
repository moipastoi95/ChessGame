package interfaces;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Paths;

import controlers.ControleKingStatus;
import controlers.ControleLostPieces;
import controlers.ControleTileIcon;
import controlers.ControleTileStack;
import controlers.ControleTime;
import controlers.ControleTurn;
import global.ChessBoard;
import global.Coord;
import global.Game;
import global.Player;
import global.SetTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import pieces.Knight;

/**
 * Graphic interface
 *
 */
public class Graphic extends Application implements ChessGameInterface {
	// attributes
	private Stage primaryStage;
	private GridPane grid = new GridPane();
	BorderPane echiquier=new BorderPane();
	private Game game;
	private Coord selectedCoord;
	private SetTimer timerW;
	private SetTimer timerB;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		newGame();
	}
	
	public SetTimer getWhiteTimer() {
		return this.timerW;
	}
	
	public SetTimer getBlackTimer() {
		return this.timerB;
	}

	/**
	 * Get the position of the selected Piece
	 * 
	 * @return The Coord of the selected Piece
	 */
	public Coord getSelectedCoord() {
		return selectedCoord;
	}

	/**
	 * Set the position of the selected Piece
	 * 
	 * @param c The Coord of the selected Piece
	 */
	public void setSelectedCoord(Coord c) {
		selectedCoord = c;
	}

	/**
	 * init and display a new Game
	 */
	public void newGame() {
		// Create logical ChessBoard
//		String[][] position={{"r","","","k","","b","","r"},
//				             {"",""," ",""," "," ","",""},
//				             {" "," "," ","",""," "," ",""},
//				             {" "," ",""," ","",""," ",""},
//				             {" "," "," "," "," "," "," ",""},
//				             {" "," "," "," "," "," "," ",""},
//				             {"P"," ","",""," ","","",""},
//				             {"R","","","","K","","","R"}};
//		game = new Game(position);
		game=new Game();
		ChessBoard.setConfigBoard(1);
		
		// set timer
		// clear 
		if (timerB != null) {
			timerB.newTimeLine();
		}
		if (timerW != null) {
			timerW.newTimeLine();
		}
		int GlobalTime = 5 * 60;
		timerW = new SetTimer(true, GlobalTime); 
		timerB = new SetTimer(true, GlobalTime);

		loadGame();
	}

	public void loadGame() {
		// generate the graphic board
		displayCell(game.getChessBoard(), grid);
		numberingRowCol(ChessBoard.getConfigBoard());

		// init pieces moveable
		if (game.getTurn()) {
			game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
			game.getChessBoard().updateCheckStatusKing(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
		} else {
			game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
			game.getChessBoard().updateCheckStatusKing(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
		}
		
		BorderPane screen = new BorderPane();
		echiquier.setCenter(grid);
		screen.setCenter(echiquier);
		

		// create button at the bottom
		VBox h = createSaveOpenExitButton(primaryStage, grid, game.getChessBoard());
		screen.setRight(h);

		// create player border
		VBox bPlayerBorder = playerBorder(game.getBlackPlayer(), timerB);
		VBox wPlayerBorder = playerBorder(game.getWhitePlayer(), timerW);
		screen.setTop(bPlayerBorder);
		screen.setBottom(wPlayerBorder);

		// show the screen
		Scene scene = new Scene(screen);
		primaryStage.setScene(scene);
		primaryStage.setTitle("ChessBoard Projet");
		primaryStage.show();
	}
	
	/**
	 * Generate the graphic board
	 * 
	 * @param chessBoard The abstract ChessBoard
	 * @param pane       The mother node in which the Board will be generated
	 */
	private void displayCell(ChessBoard chessBoard, GridPane pane) {
		pane.getChildren().clear();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				StackPane tile = new StackPane();
				ControleTileStack cs = new ControleTileStack(new Coord(i, j), this, game, tile);
				tile.setOnMouseClicked(cs);
				pane.add(tile, j, i);
			}
		}
		
		// 
	}

	/**
	 * Generate the number on the side of the graphic board
	 * 
	 * @param config The configuration of the board
	 * @param pane   The GridPane that store the graphic board
	 */
	private void numberingRowCol(int config) {
		GridPane vertical= new GridPane();
		GridPane horizontal=new GridPane();
		vertical.getChildren().clear();
		horizontal.getChildren().clear();
		echiquier.getChildren().clear();
		echiquier.setCenter(grid);
//		Label lab = new Label();
//		lab.setTextFill(Color.BLUE);
//		lab.setStyle("-fx-font-weight: bold");
//		// Numbering columns
		switch (config) {
		case 1:
			for(int i=65; i<73; i++) {
				Label l1=new Label("       "+Character.toString(i)+"      ");
				l1.setTextFill(Color.BLUE);
				l1.setStyle("-fx-font-weight: bold");
				horizontal.add(l1,i-65, 0);		
			}
			echiquier.setBottom(horizontal);
			for(int j=8; j>0;j--) {
				Integer k=j;
				Label l2=new Label("  "+k.toString());
				l2.setPrefSize(50, 50);
				l2.setTextFill(Color.BLUE);
				l2.setStyle("-fx-font-weight: bold");
				vertical.add(l2,0,8-j);
				vertical.setPrefSize(20, 300);
			}
			echiquier.setRight(vertical);
			break;
		case 2:
			horizontal.add(new Label("      "),0, 0);
			for(int i=1; i<9; i++) {
				Integer k=i;
				Label l1=new Label("       "+k.toString()+"      ");
				l1.setTextFill(Color.BLUE);
				l1.setStyle("-fx-font-weight: bold");
				horizontal.add(l1,i, 0);		
			}
			echiquier.setBottom(horizontal);
			for(int j=65; j<73;j++) {

				Label l2=new Label(Character.toString(j)+"  ");
				l2.setPrefSize(50, 50);
				l2.setTextFill(Color.BLUE);
				l2.setStyle("-fx-font-weight: bold");
				vertical.add(l2,0,j-65);
				vertical.setPrefSize(20, 300);
			}
			echiquier.setLeft(vertical);
			break;
		case 3:
			horizontal.add(new Label("      "),0, 0);	
			for(int i=72; i>64; i--) {
				Label l1=new Label("       "+Character.toString(i)+"      ");
				l1.setTextFill(Color.BLUE);
				l1.setStyle("-fx-font-weight: bold");
				horizontal.add(l1,73-i, 0);		
			}
			echiquier.setTop(horizontal);
			for(int j=1; j<9;j++) {
				Integer k=j;
				Label l2=new Label("  "+k.toString());
				l2.setPrefSize(50, 50);
				l2.setTextFill(Color.BLUE);
				l2.setStyle("-fx-font-weight: bold");
				vertical.add(l2,0,j-1);
				vertical.setPrefSize(20, 300);
			}
			echiquier.setLeft(vertical);
			break;
		default:
			for(int i=8; i>0; i--) {
				Integer k=i;
				Label l1=new Label("       "+k.toString()+"      ");
				l1.setTextFill(Color.BLUE);
				l1.setStyle("-fx-font-weight: bold");
				horizontal.add(l1,8-i, 0);		
			}
			echiquier.setBottom(horizontal);
			for(int j=72; j>64;j--) {
	
				Label l2=new Label("  "+Character.toString(j));
				l2.setPrefSize(50, 50);
				l2.setTextFill(Color.BLUE);
				l2.setStyle("-fx-font-weight: bold");
				vertical.add(l2,0,j-65);
				vertical.setPrefSize(20, 300);
			}
			echiquier.setRight(vertical);
			break;
		}
//		Label lb2 = new Label("");
//		pane.add(lb2, 0, 9);
//		// Numbering rows
//		for (int i = 0; i < 8; i++) {
//			Integer k = i + 1;
//			Label lb3 = new Label("  " + k.toString());
//			lb3.setTextFill(Color.BLUE);
//			lb3.setStyle("-fx-font-weight: bold");
//			pane.add(lb3, 9, 7 - i);
//		}
	}

	/**
	 * Generate a pane that display player's current informations
	 * 
	 * @param player The concerned player
	 * @return A VBox with player's informations
	 */
	private VBox playerBorder(Player player, SetTimer timer) {
		/// Top
		// Display the current player (Black or White).
		HBox playerBox = new HBox();
		Label lb_turn_1 = new Label("  Player:    ");
		lb_turn_1.setStyle("-fx-font-weight: bold");

		TextField result = new TextField();
		result.setPrefWidth(60);
		result.setEditable(false);
		String playerString = player.getColor() ? "White" : "Black";
		result.setText(playerString);
		result.setStyle("-fx-text-fill: red; -fx-font-weight: bold");

		playerBox.getChildren().add(lb_turn_1);
		playerBox.getChildren().add(result);

		// Create area for King status
		HBox kingBox = new HBox();
		Label blackKingStatus = new Label(playerString + "  King Status:    ");
		blackKingStatus.setStyle("-fx-font-weight: bold");

		TextField kingStatusArea = new TextField();
		// formatTextArea(kingStatusArea, 50, 50);
		kingStatusArea.setPrefWidth(60);
		kingStatusArea.setEditable(false);
		ControleKingStatus cks = new ControleKingStatus(kingStatusArea, player);
		player.addObserver(cks);

		kingBox.getChildren().add(blackKingStatus);
		kingBox.getChildren().add(kingStatusArea);

		// Display time
		HBox timeBox = new HBox();
		Label timeLabel = new Label("  " + playerString + " time    ");
		timeLabel.setStyle("-fx-font-weight: bold");
		TextField timeArea = new TextField();
		timeArea.setPrefWidth(60);
		timeArea.setEditable(false);
		ControleTime ct = new ControleTime(timeArea, timer, this, game, player.getColor());
		game.getChessBoard().addObserver(ct);
		
		timeBox.getChildren().add(timeLabel);
		timeBox.getChildren().add(timeArea);

		// gather children into VBox top
		HBox top = new HBox();
		top.getChildren().add(playerBox);
		top.getChildren().add(kingBox);
		top.getChildren().add(timeBox);

		/// Bottom
		// Create area for display lost pieces
		HBox lostBox = new HBox();
		Label lostPiece = new Label("Lost " + playerString + " pieces: ");
		lostPiece.setStyle("-fx-font-weight: bold");
		FlowPane lostArea = new FlowPane();
		lostArea.setMinHeight(50);
//		formatTextArea(lostArea, 200, 30);
//		lostArea.setText(player.getCapturedPieces().toString());
		ControleLostPieces clp = new ControleLostPieces(lostArea, game.getChessBoard(), player);
		game.getChessBoard().addObserver(clp);

		lostBox.getChildren().add(lostPiece);
		lostBox.getChildren().add(lostArea);

		// gather top and bottom into HBox borderPlayer
		VBox borderPlayer = new VBox();
		borderPlayer.getChildren().add(top);
		borderPlayer.getChildren().add(lostBox);

		return borderPlayer;

	}

	/**
	 * Generate the control button (save, open, exit, turn) + a counter of turn
	 * 
	 * @param primaryStage The primaryStage
	 * @param pane         The GridPane which represent the graphic board
	 * @param chessBoard   The abstract ChessBoard
	 * @return A VBox that contains buttons and the counter of turn
	 */
	private VBox createSaveOpenExitButton(Stage primaryStage, GridPane pane, ChessBoard chessBoard) {
		// Save Button
		Button saveBtn = new Button("Save");
		ImageView saveImv = new ImageView();
		Image saveImg = new Image(Graphic.class.getResourceAsStream("/images/save.jpg"));
		saveImv.setImage(saveImg);
		saveBtn.setGraphic(saveImv);
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ChessGame Save", "*.ser*"));
		saveBtn.setOnAction(e1 -> {			
			File file = fileChooser.showSaveDialog(primaryStage);
			if (file != null) {
				Integer[] timers = {timerW.getTimeSeconds(), timerB.getTimeSeconds()};
				String path = file.getAbsolutePath();
				game.saveFile(path, timers);
			}
				
		});

		// Open Button
		Button openBtn = new Button("Open");
		ImageView openImv = new ImageView();
		Image openImg = new Image(Graphic.class.getResourceAsStream("/images/open.png"));
		openImv.setImage(openImg);
		openBtn.setGraphic(openImv);
		// Creating a File chooser
		FileChooser fileChooserOpen = new FileChooser();
		fileChooser.setTitle("Open");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("ChessGame Save", "*.ser*"));
		openBtn.setOnAction(e -> {
//			try {
				String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); // To set default open directory
				fileChooserOpen.setInitialDirectory(new File(currentPath));
				File fileOpen = fileChooser.showOpenDialog(primaryStage);
				if (fileOpen != null) {
					String path = fileOpen.getAbsolutePath();
					Integer[] timers = game.loadFile(path);
					timerW = new SetTimer(true, timers[0]);
					timerW.newTimeLine();
					timerB = new SetTimer(false, timers[1]);
					timerB.newTimeLine();
					loadGame();
				}
//				FileInputStream filestream = new FileInputStream(fileOpen);
//				try {
//					ObjectInputStream os = new ObjectInputStream(filestream);
//					try {
//						Game gameTest_s = (Game) os.readObject();
//						Integer configBoard_s = (Integer) os.readObject();
//						game = gameTest_s;
////							chessBoard = gameBoard_s;
////							histListWhite = (List<Coord>) histListWhite_s;
////							histListBlack = (List<Coord>) histListBlack_s;
////							lostPieceWhiteList.set(0, lostPieceWhite_s);
////							lostPieceBlackList.set(0, lostPieceBlack_s);
////							whiteTimeList = whiteTimeList_s;
////							blackTimeList = blackTimeList_s;
////							chessBoardList.get(0).setConfigBoard(configBoard_s);
////							markPossibleMove(game, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
////						markPossibleMove(game);
//
////							pane.getChildren().remove(labelCol);
//						numberingRowCol(ChessBoard.getConfigBoard());
////							labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
//						displayCell(chessBoard, pane); // Call display chessboard's
//														// cell function
////							markCell(game, pane, "O");
//					} catch (ClassNotFoundException e2) {
//						e2.printStackTrace();
//					}
//				} catch (IOException e2) {
//					e2.printStackTrace();
//				}
//			} catch (FileNotFoundException e2) {
//				e2.printStackTrace();
//			}
		});

		// Button Exit
		Button exitBtn = new Button("Exit");
		ImageView exitImv = new ImageView();
		Image exitImg = new Image(Graphic.class.getResourceAsStream("/images/exit.gif"));
		exitImv.setImage(exitImg);
		exitBtn.setGraphic(exitImv);
		exitBtn.setOnAction(e -> {
			Platform.exit();
		});

		// Button turn
		Button turnBtn = new Button("Turn");
		ImageView turnImv = new ImageView();
		Image turnImg = new Image(Graphic.class.getResourceAsStream("/images/turn90.png"));
		turnImv.setImage(turnImg);
		turnBtn.setGraphic(turnImv);
		turnBtn.setOnAction(e -> {
			ChessBoard.setConfigBoard((game.getChessBoard().getConfigBoard() + 1) % 4);
			displayCell(game.getChessBoard(), pane); // Call display chessboard's cell function
			numberingRowCol(ChessBoard.getConfigBoard());
		});

		// New Button
		Button newBtn = new Button("New");
		newBtn.setOnAction(e -> {
//				Game gameNew = new Game();
//				gameNew.getChessBoard().setConfigBoard(game.getChessBoard().getConfigBoard());
//				game = gameNew;
//				chessBoardList.set(0, gameTest2.getChessBoard());
//				turnBoard = false;
//				histListWhite.clear();
//				histListBlack.clear();
//				lostPieceWhiteList.set(0, new LostPiece(0, 0, 0, 0, 0, 0, true));
//				lostPieceBlackList.set(0, new LostPiece(0, 0, 0, 0, 0, 0, false));
//				whiteTimeList.set(0, (double) 0);
//				blackTimeList.set(0, (double) 0);
//				HashSet<Coord> pMove = null; // HashSet contains possible move
//				pMoveList.add(pMove);
//				countList.set(0, 1); // Neu ko co khi 1 ben win, chon New button, sau do chon quan co di se bao
//										// Invalid input
//				InputDialog dialog = new InputDialog("2");
//				dialog.getStage().showAndWait();
//				int config;
//				if (dialog.getResult().substring(0, 1).contains("1")) {
//					config = 1;
//				} else {
//					config = 2;
//				}
//				game.getChessBoard().setConfigBoard(config);
//				markPossibleMove(game, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
//				markPossibleMove(game, pane, new ArrayList<Coord>(), new ArrayList<Coord>(), new ArrayList<LostPiece>(), new ArrayList<LostPiece>());
//				
//				pane.getChildren().remove(labelCol);
//				NumberingRowCol(pane);
//				labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
//				displayCell(game.getChessBoard(), pane);
//				markCell(game, pane, "O");
			newGame();
		});
//		pane.add(newBtn, 0, 10, 2, 1);
//		GridPane.setHalignment(newBtn, HPos.CENTER);
//		pane.add(saveBtn, 2, 10, 2, 1);
//		GridPane.setHalignment(saveBtn, HPos.CENTER);
//		pane.add(openBtn, 4, 10, 2, 1);
//		GridPane.setHalignment(openBtn, HPos.CENTER);
//		pane.add(exitBtn, 6, 10, 2, 1);
//		GridPane.setHalignment(exitBtn, HPos.CENTER);
//		pane.add(turnBtn, 10, 10, 2, 1);
//		GridPane.setHalignment(turnBtn, HPos.CENTER);

		// Number of turn
		HBox turnBox = new HBox();
		Label lb_turn = new Label("Tour : ");
		lb_turn.setStyle("-fx-font-weight: bold");

		TextField result = new TextField();
		result.setPrefWidth(60);
		result.setEditable(false);
		result.setStyle("-fx-text-fill: red; -fx-font-weight: bold");
		ControleTurn ct = new ControleTurn(result, game);
		game.addObserver(ct);

		turnBox.getChildren().add(lb_turn);
		turnBox.getChildren().add(result);

		VBox buttonsBox = new VBox();
		buttonsBox.getChildren().add(newBtn);
		buttonsBox.getChildren().add(saveBtn);
		buttonsBox.getChildren().add(openBtn);
		buttonsBox.getChildren().add(exitBtn);
		buttonsBox.getChildren().add(turnBtn);
		buttonsBox.getChildren().add(turnBox);
		return buttonsBox;
	}

	/**
	 * Show a Dialog box
	 * 
	 * @param str The text to display
	 */
	public static void showAlertInvalidInput(String str) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(str);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
	}

	// Identify pieces that are movable and the status of current King.
//	public void markPossibleMove(Game game) {
//		if (game.getTurn()) {
//			game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
//
//			game.getChessBoard().updateCheckStatusking(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
//			;
//		} else {
//			game.getChessBoard().coorPieceMoveable(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());
//			;
//			game.getChessBoard().updateCheckStatusking(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
//			;
//		}
//	}

	/**
	 * Set dimension to a TextArea
	 * 
	 * @param textArea The concerned TextArea
	 * @param w        The width wanted
	 * @param h        The height wanted
	 */
	private void formatTextArea(TextArea textArea, int w, int h) {
		textArea.setPrefWidth(w);
		textArea.setPrefHeight(h);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		textArea.setStyle("-fx-font-size: 10px;");
	}

	/**
	 * Convert Coord from ChessBoard to Coord accorded to the Graphic board (maybe
	 * turned)
	 * 
	 * @param c      The Coord according to the abstract ChessBoard
	 * @param config The configuration of the Board
	 * @return The Coord according to the graphic board
	 */
	public static Coord convertChessToGraph(Coord c, int config) {
		int i = c.getR();
		int j = c.getC();
		int n, m;
		// normal config : black on the top, white on the bottom
		if (config == 1) {
			m = j;
			n = i;
		} // 90� config : white on the left, black on the right
		else if (config == 2) {
			m = 7 - i;
			n = j;
		} // 180� config : white on the top, black on the bottom
		else if (config == 3) {
			m = 7 - j;
			n = 7 - i;
		} // -90� config : black on the left, white on the right
		else {
			m = i;
			n = 7 - j;
		}
		return new Coord(n, m);
	}

	/**
	 * Convert Coord from graphic board to Coord accorded to the abstract ChessBoard
	 * (maybe // turned)
	 * 
	 * @param c      The Coord according to the graphic board
	 * @param config The configuration of the Board
	 * @return The Coord according to the abstract ChessBoard
	 */
	public static Coord convertGraphToChess(Coord c, int config) {
		int i = c.getR();
		int j = c.getC();
		int n, m;
		// normal config : black on the top, white on the bottom
		if (config == 1) {
			m = j;
			n = i;
		} // 90� config : white on the left, black on the right
		else if (config == 2) {
			m = i;
			n = 7 - j;
		} // 180� config : white on the top, black on the bottom
		else if (config == 3) {
			m = 7 - j;
			n = 7 - i;
		} // -90� config : black on the left, white on the right
		else {
			m = 7 - i;
			n = j;
		}
		return new Coord(n, m);
	}

	@Override
	public int promoteDialog() {
		// Dialog to input configBoard is 1 or 2. Default 2.
		InputDialog dialog = new InputDialog("3");
		dialog.getStage().showAndWait();
		return dialog.getResult();
	}

	/**
	 * Generate a dialog box which let a user write a number
	 */
	private static class InputDialog {
		private final Stage stage;
		private final TextField input;

		/**
		 * Default constructor
		 * 
		 * @param string The default value
		 */
		public InputDialog(String string) {
//			input = new TextField(string);
//			Button close = new Button("Submit");
//			Label label = new Label("Chose a Piece of promoting\n0=knight, 1=bishop, 2=rook,\nany other number=Queen");
//			VBox root = new VBox();
//			root.setAlignment(Pos.CENTER);
//			root.getChildren().addAll(label, input, close);
//			Scene scene = new Scene(root, 200, 100);
//			stage = new Stage();
//			stage.initModality(Modality.APPLICATION_MODAL);
//			close.setOnAction(e -> stage.hide());
//			stage.setScene(scene);
			
			input = new TextField(string);
			Button close = new Button("Submit");
			Label label = new Label("Chose a Piece of promoting");
			
			HBox choiceBtn = new HBox();
			Button knightBtn = new Button();
			knightBtn.setGraphic(new ImageView(ControleTileIcon.getImage(new Knight(true, null))));
			knightBtn.setOnMouseClicked(e -> {
				
			});
			
			VBox root = new VBox();
			root.setAlignment(Pos.CENTER);
			root.getChildren().addAll(label, input, close);
			Scene scene = new Scene(root, 200, 100);
			stage = new Stage();
			stage.initModality(Modality.APPLICATION_MODAL);
			close.setOnAction(e -> stage.hide());
			stage.setScene(scene);
		}

		/**
		 * Get the stage
		 * 
		 * @return The stage
		 */
		public Stage getStage() {
			return stage;
		}

		/**
		 * Get the value of the User input
		 * 
		 * @return
		 */
		public int getResult() {
			if (input.getText().length() > 0) {
				return Integer.parseInt(input.getText());
			} else {
				return 2;
			}
		}
	}

	/**
	 * Start the graphic interface
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

}