package shogi.piece;

import shogi.board.GameBoard;
import shogi.board.Position;

import java.util.ArrayList;

/**
 * @author sina
 * @version 1.0.0
 */
public abstract class ChessMen {

	private boolean isNormal;
	protected GameBoard gameBoard;
	protected Position position;

	private roles playerRole;

	public ChessMen(){
		isNormal = true;
	}
	public abstract ArrayList<Position> calculatingMoves();

	public Position getPosition() {
		return position;
	}

	public roles getPlayerRole() {
		return playerRole;
	}

	public void setPlayerRole(roles playerRole) {
		this.playerRole = playerRole;
	}
	public boolean getNormal() {
		return isNormal;
	}

	public void setNormal(boolean normal) {
		isNormal = normal;
	}

	public enum  roles {
		PLAYER_BLACK_ROLE,PLAYER_WHITE_ROLE
	}

	public abstract String toString();
}
