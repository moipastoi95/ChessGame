package interfaces;

/**
 * The interface which start the Game
 */
public interface ChessGameInterface {

	/**
	 * The way to ask the user in which Piece he wants his Pawn to be promoted
	 * 
	 * @return The number of the Piece selected
	 */
	public int promoteDialog();
}
