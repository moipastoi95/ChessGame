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
import controlers.ControleTileStack;
import controlers.ControleTurn;
import global.ChessBoard;
import global.Coord;
import global.Game;
import global.Player;
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

/**
 * Graphic interface
 *
 */
public class Graphic extends Application implements ChessGameInterface {
	// attributes
	private Stage primaryStage;
	private GridPane grid = new GridPane();
	private Game game;
	private Coord selectedCoord;

	@Override
	public void start(Stage primaryStage) {
		this.primaryStage = primaryStage;
		newGame();
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

		// generate the graphic board
		displayCell(game.getChessBoard(), grid);
		numberingRowCol(ChessBoard.getConfigBoard(), grid);

		// init pieces moveable
		game.getChessBoard().coorPieceMoveable(game.getWhitePlayer().getCoordOfMyPieces(), game.getTurn());
		game.getChessBoard().updateCheckStatusking(game.getBlackPlayer().getCoordOfMyPieces(), game.getTurn());

		BorderPane screen = new BorderPane();
		screen.setCenter(grid);

		// create button at the bottom
		VBox h = createSaveOpenExitButton(primaryStage, grid, game.getChessBoard());
		screen.setRight(h);

		// create player border
		VBox bPlayerBorder = playerBorder(game.getBlackPlayer());
		VBox wPlayerBorder = playerBorder(game.getWhitePlayer());
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
	}

	/**
	 * Generate the number on the side of the graphic board
	 * 
	 * @param config The configuration of the board
	 * @param pane   The GridPane that store the graphic board
	 */
	private void numberingRowCol(int config, GridPane pane) {
		Label lab = new Label();
		lab.setTextFill(Color.BLUE);
		lab.setStyle("-fx-font-weight: bold");
		// Numbering columns
		switch (config) {
		case 1:
			lab.setText(
					"      a              b             c             d             e             f             g             h");
			pane.add(lab, 0, 8, 8, 1);
			break;
		case 2:
			lab.setText(
					"      1              2             3             4             5             6             7             8");
			pane.add(lab, 0, 8, 8, 1);
			break;
		case 3:
			lab.setText(
					"      h              g             f             e             d             c             b             a");
			pane.add(lab, 0, 8, 8, 1);
			break;
		case 4:
			lab.setText(
					"      8              7             6             5             4             3             2             1");
			pane.add(lab, 0, 8, 8, 1);
			break;
		}
		Label lb2 = new Label("");
		pane.add(lb2, 0, 9);
		// Numbering rows
		for (int i = 0; i < 8; i++) {
			Integer k = i + 1;
			Label lb3 = new Label("  " + k.toString());
			lb3.setTextFill(Color.BLUE);
			lb3.setStyle("-fx-font-weight: bold");
			pane.add(lb3, 9, 7 - i);
		}
	}

	/**
	 * Generate a pane that display player's current informations
	 * 
	 * @param player The concerned player
	 * @return A VBox with player's informations
	 */
	private VBox playerBorder(Player player) {
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
		timeArea.setText("TODO");

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
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		saveBtn.setOnAction(e1 -> {
			try {
				File file = fileChooser.showSaveDialog(primaryStage);
				FileOutputStream filestream = new FileOutputStream(file);
				try {
					ObjectOutputStream os = new ObjectOutputStream(filestream);
//					os.writeObject(game); // save current gameTest object.
//						os.writeObject(chessBoard); // save cac o co logic
//						os.writeObject(histListWhite);
//						os.writeObject(histListBlack);
//						os.writeObject(lostPieceWhiteList.get(0));
//						os.writeObject(lostPieceBlackList.get(0));
//						os.writeObject(whiteTimeList);
//						os.writeObject(blackTimeList);
//						os.writeObject(chessBoardList.get(0).getConfigBoard());
					showAlertInvalidInput("Written!");
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

		// Open Button
		Button openBtn = new Button("Open");
		ImageView openImv = new ImageView();
		Image openImg = new Image(Graphic.class.getResourceAsStream("/images/open.png"));
		openImv.setImage(openImg);
		openBtn.setGraphic(openImv);
		// Creating a File chooser
		FileChooser fileChooserOpen = new FileChooser();
		fileChooser.setTitle("Open");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		openBtn.setOnAction(e -> {
			try {
				String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); // To set default open
																								// directory
				fileChooserOpen.setInitialDirectory(new File(currentPath));
				File fileOpen = fileChooser.showOpenDialog(primaryStage);
				FileInputStream filestream = new FileInputStream(fileOpen);
				try {
					ObjectInputStream os = new ObjectInputStream(filestream);
					try {
						Game gameTest_s = (Game) os.readObject();
						Integer configBoard_s = (Integer) os.readObject();
						game = gameTest_s;
//							chessBoard = gameBoard_s;
//							histListWhite = (List<Coord>) histListWhite_s;
//							histListBlack = (List<Coord>) histListBlack_s;
//							lostPieceWhiteList.set(0, lostPieceWhite_s);
//							lostPieceBlackList.set(0, lostPieceBlack_s);
//							whiteTimeList = whiteTimeList_s;
//							blackTimeList = blackTimeList_s;
//							chessBoardList.get(0).setConfigBoard(configBoard_s);
//							markPossibleMove(game, pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
//						markPossibleMove(game);

//							pane.getChildren().remove(labelCol);
						numberingRowCol(game.getChessBoard().getConfigBoard(), pane);
//							labelCurentPlayer(pane, histListWhite, histListBlack, lostPieceWhiteList, lostPieceBlackList);
						displayCell(chessBoard, pane); // Call display chessboard's
														// cell function
//							markCell(game, pane, "O");
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
			game.getChessBoard().setConfigBoard((game.getChessBoard().getConfigBoard() + 1) % 4);
			displayCell(game.getChessBoard(), pane); // Call display chessboard's cell function
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
			input = new TextField(string);
			Button close = new Button("Submit");
			Label label = new Label("Chose a Piece of promoting\n0=knight, 1=bishop, 2=rook,\nany other number=Queen");
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