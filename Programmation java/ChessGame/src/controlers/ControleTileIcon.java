package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Coord;
import global.Game;
import interfaces.Graphic;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Piece;
import pieces.Queen;
import pieces.Rook;

/**
 * Controller of Image of each tile
 *
 */
public class ControleTileIcon implements Observer {
	// attributes
	private Coord tilePosition; // get ref to selected Piece
	private Game game;
	private ImageView imageView;

	/**
	 * Default constructor
	 * 
	 * @param tilePosition The "absolute" position of the tile
	 * @param game         The current game
	 * @param imageView    The node that store the image
	 */
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

	/**
	 * Refresh the image
	 */
	public void syncImage() {
		int m, n;
		// Scan the logical chessBoard and put the appropriate image according to the
		// type of pieces.
		Coord c = Graphic.convertGraphToChess(tilePosition, game.getChessBoard().getConfigBoard());
		m = c.getC();
		n = c.getR();
		imageView.setImage(getImage(game.getChessBoard().getBoard()[n][m]));
	}

	/**
	 * Get the image according to a Piece
	 * 
	 * @param p The Piece
	 * @return An Image that represent the Piece
	 */
	public static Image getImage(Piece p) {
		// Define image of pieces
		try {
			Image image_bb = new Image(Graphic.class.getResourceAsStream("/images/bb.gif"));
			Image image_wb = new Image(Graphic.class.getResourceAsStream("/images/wb.gif"));
			Image image_wk = new Image(Graphic.class.getResourceAsStream("/images/wk.gif"));
			Image image_bk = new Image(Graphic.class.getResourceAsStream("/images/bk.gif"));
			Image image_wn = new Image(Graphic.class.getResourceAsStream("/images/wn.gif"));
			Image image_bn = new Image(Graphic.class.getResourceAsStream("/images/bn.gif"));
			Image image_wp = new Image(Graphic.class.getResourceAsStream("/images/wp.gif"));
			Image image_bp = new Image(Graphic.class.getResourceAsStream("/images/bp.gif"));
			Image image_wq = new Image(Graphic.class.getResourceAsStream("/images/wq.gif"));
			Image image_bq = new Image(Graphic.class.getResourceAsStream("/images/bq.gif"));
			Image image_wr = new Image(Graphic.class.getResourceAsStream("/images/wr.gif"));
			Image image_br = new Image(Graphic.class.getResourceAsStream("/images/br.gif"));

			if (p != null) {
				Image img = image_bb;
				if (p instanceof Pawn) {
					if (p.getColor()) {
						img = image_wp;
					} else {
						img = image_bp;
					}
				}
				if (p instanceof King) {
					if (p.getColor()) {
						img = image_wk;
					} else {
						img = image_bk;
					}
				}
				if (p instanceof Queen) {
					if (p.getColor()) {
						img = image_wq;
					} else {
						img = image_bq;
					}
				}
				if (p instanceof Bishop) {
					if (p.getColor()) {
						img = image_wb;
					} else {
						img = image_bb;
					}
				}
				if (p instanceof Knight) {
					if (p.getColor()) {
						img = image_wn;
					} else {
						img = image_bn;
					}
				}
				if (p instanceof Rook) {
					if (p.getColor()) {
						img = image_wr;
					} else {
						img = image_br;
					}
				}
				return img;
			} else {
				return null;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
