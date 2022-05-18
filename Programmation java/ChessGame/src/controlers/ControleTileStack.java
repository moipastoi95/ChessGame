package controlers;

import java.util.Observable;
import java.util.Observer;

import controlers.ControleTileIcon;
import controlers.ControleTileRectangle;
import global.Coord;
import global.Game;
import global.NotInHashSetException;
import interfaces.Graphic;
import interfaces.Main;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ControleTileStack implements EventHandler<MouseEvent> {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private Graphic graphic;
	private StackPane tile;

	public ControleTileStack(Coord tilePosition, Graphic graphic, Game game, StackPane tile) {
		this.tilePosition = tilePosition;
		this.game = game;
		this.graphic = graphic;
		this.tile = tile;

		generateColor();
		generateImage();
	}

	@Override
	public void handle(MouseEvent arg0) {
		System.out.print("coucou");
		// select a piece
		if (graphic.getSelectedCoord() == null) {
			try {
				game.select(tilePosition);
				graphic.setSelectedCoord(tilePosition);
			} catch (NotInHashSetException e) {
				// Graphic.showAlertInvalidInput("Please select a valid tile");
				System.out.print("valid tile plz");
			}
		} else {
			int mi = tilePosition.getR();
			int mj = tilePosition.getC();
			int ai = graphic.getSelectedCoord().getR();
			int aj = graphic.getSelectedCoord().getC();
			// select another piece
			
			// A MODIFIER
			if (game.getChessBoard().getBoard()[mi][mj] == null) {
				Graphic.showAlertInvalidInput("please, another one");
			}
			else if (game.getChessBoard().getBoard()[mi][mj].getColor() == game.getChessBoard().getBoard()[ai][aj]
					.getColor()) {
				try {
					game.select(tilePosition);
					graphic.setSelectedCoord(tilePosition);
				} catch (NotInHashSetException e) {
					Graphic.showAlertInvalidInput(e.getMessage());
				}
			} else { // otherwise, play
				try {
					game.play(graphic.getSelectedCoord(), tilePosition);
					game.getChessBoard().update(graphic.getSelectedCoord(), tilePosition);

					if (game.getTurn()) {
						game.getChessBoard().coorPieceMovable(game.getWhitePlayer().getCoordOfMyPieces(),
								game.getTurn());
						game.getChessBoard().updateCheckStatusking(game.getBlackPlayer().getCoordOfMyPieces(),
								game.getTurn());
					} else {
						game.getChessBoard().coorPieceMovable(game.getBlackPlayer().getCoordOfMyPieces(),
								game.getTurn());
						game.getChessBoard().updateCheckStatusking(game.getWhitePlayer().getCoordOfMyPieces(),
								game.getTurn());
					}

					game.setnbCoup();
					game.setTurn();
				} catch (NotInHashSetException e) {
					Graphic.showAlertInvalidInput(e.getMessage());
				}
			}
		}
	}

	public void generateImage() {
		// Define image of pieces
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
			// Scan the logical chessBoard and put the appropriate image according to the
			// type of pieces.
			int i = tilePosition.getR();
			int j = tilePosition.getC()
;			Coord c = Graphic.convertChessToGraph(new Coord(i, j), game.getChessBoard().getConfigBoard());
			m = c.getC();
			n = c.getR();
			if (game.getChessBoard().getBoard()[n][m] != null) {
				Image img = image_bb;
				if (game.getChessBoard().getBoard()[n][m] instanceof Pawn) {
					if (game.getChessBoard().getBoard()[n][m].getColor()) {
						img = image_wp;
					} else {
						img = image_bp;
					}
				}
				if (game.getChessBoard().getBoard()[n][m] instanceof King) {
					if (game.getChessBoard().getBoard()[n][m].getColor()) {
						img = image_wk;
					} else {
						img = image_bk;
					}
				}
				if (game.getChessBoard().getBoard()[n][m] instanceof Queen) {
					if (game.getChessBoard().getBoard()[n][m].getColor()) {
						img = image_wq;
					} else {
						img = image_bq;
					}
				}
				if (game.getChessBoard().getBoard()[n][m] instanceof Bishop) {
					if (game.getChessBoard().getBoard()[n][m].getColor()) {
						img = image_wb;
					} else {
						img = image_bb;
					}
				}
				if (game.getChessBoard().getBoard()[n][m] instanceof Knight) {
					if (game.getChessBoard().getBoard()[n][m].getColor()) {
						img = image_wn;
					} else {
						img = image_bn;
					}
				}
				if (game.getChessBoard().getBoard()[n][m] instanceof Rook) {
					if (game.getChessBoard().getBoard()[n][m].getColor()) {
						img = image_wr;
					} else {
						img = image_br;
					}
				}
				ImageView iv = new ImageView(img);
				ControleTileIcon ci = new ControleTileIcon(tilePosition, graphic, game, iv);
				game.getChessBoard().addObserver(ci);
				tile.getChildren().add(iv);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void generateColor() {
		// Size of the rectangle in a cell of the displayed chess board
		final double s = 50;
		Rectangle r = new Rectangle(s, s, s, s);
		if ((tilePosition.getR() + tilePosition.getC())%2 == 0) {
			r.setFill(Color.LIGHTYELLOW);
		} else {
			r.setFill(Color.BROWN);
		}
		
		ControleTileRectangle ct = new ControleTileRectangle(tilePosition, graphic, game, r);
		game.getChessBoard().addObserver(ct);
		tile.getChildren().add(r);
	}
}
