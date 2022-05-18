
import java.io.Serializable;

public class LostPiece implements Serializable {
	int pawnNum,  kingNum, queenNum, bishopNum, knightNum, rookNum;
	boolean color;

	public LostPiece(int pawnNum, int kingNum, int queenNum, int bishopNum, int knightNum, int rookNum, boolean color) {
		super();
		this.pawnNum = pawnNum;
		this.kingNum = kingNum;
		this.queenNum = queenNum;
		this.bishopNum = bishopNum;
		this.knightNum = knightNum;
		this.rookNum = rookNum;
		this.color = color;
	}

	public int getPawnNum() {
		return pawnNum;
	}

	public void setPawnNum(int pawnNum) {
		this.pawnNum = pawnNum;
	}

	public int getKingNum() {
		return kingNum;
	}

	public void setKingNum(int kingNum) {
		this.kingNum = kingNum;
	}

	public int getQueenNum() {
		return queenNum;
	}

	public void setQueenNum(int queenNum) {
		queenNum = queenNum;
	}

	public int getBishopNum() {
		return bishopNum;
	}

	public void setBishopNum(int bishopNum) {
		this.bishopNum = bishopNum;
	}

	public int getKnightNum() {
		return knightNum;
	}

	public void setKnightNum(int knightNum) {
		this.knightNum = knightNum;
	}

	public int getRookNum() {
		return rookNum;
	}

	public void setRookNum(int rookNum) {
		this.rookNum = rookNum;
	}

	public boolean isColor() {
		return color;
	}

	public void setColor(boolean color) {
		this.color = color;
	}

	@Override
	public String toString() {
		return "pawnNum=" + pawnNum + "\n kingNum=" + kingNum + "\n QueenNum=" + queenNum + "\n bishopNum="
				+ bishopNum + "\n knightNum=" + knightNum + "\n rookNum=" + rookNum;
	}
	
	
}
