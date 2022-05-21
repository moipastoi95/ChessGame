package controlers;

import java.util.Observable;
import java.util.Observer;

import global.ChessBoard;
import global.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import pieces.Piece;

/**
 * Controller of the Field "LostPieces"
 *
 */
public class ControleLostPieces implements Observer {
	private FlowPane fp;
	private ChessBoard cb;
	private Player player;

	/**
	 * Default constructor
	 * 
	 * @param fp     The FlowPane where Pieces images will be put on
	 * @param cb     The current ChessBoard
	 * @param player The player that lose pieces
	 */
	public ControleLostPieces(FlowPane fp, ChessBoard cb, Player player) {
		this.fp = fp;
		this.cb = cb;
		this.player = player;
		
		for (Piece p : player.getCapturedPieces()) {
			// on prend l'image associé
			Image img = ControleTileIcon.getImage(p);
			
			ImageView iv = new ImageView(img);
			int size = 25;
			iv.setFitHeight(size);
			iv.setFitWidth(size);
			
			fp.getChildren().add(iv);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		Integer stat = (Integer) arg;
		if (stat == ChessBoard.LOST_PIECES) {
			fp.getChildren().clear();
			for (Piece p : player.getCapturedPieces()) {
				// on prend l'image associé
				Image img = ControleTileIcon.getImage(p);
				
				ImageView iv = new ImageView(img);
				int size = 25;
				iv.setFitHeight(size);
				iv.setFitWidth(size);
				
				fp.getChildren().add(iv);
			}
		}
	}

}
