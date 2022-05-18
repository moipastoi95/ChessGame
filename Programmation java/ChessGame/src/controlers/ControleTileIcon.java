package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Coord;
import global.Game;
import interfaces.Graphic;
import interfaces.Main;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

public class ControleTileIcon implements Observer {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private ImageView imageView;

	public ControleTileIcon(Coord tilePosition, Game game, ImageView imageView) {
			this.tilePosition = tilePosition;
			this.game = game;
			this.imageView = imageView;
			
			syncImage();
		}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;		
		if (stat == ChessBoard.PLAY) {
			syncImage();
		}		
	}
	
	public void syncImage() {

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
			Coord c = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());
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
				imageView.setImage(img);
			} else {
				imageView.setImage(null);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
