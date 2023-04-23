package game;

public class Position {
	private int row;
	private int column;
	
	public Position(int row, int column) {
		
	}
	
	public void setRow(int row) {
		this.row = row;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getColumn() {
		return this.column;
	}
	
	public int getDistanceFrom(Position p) {
		return Math.abs(this.row-p.row) + Math.abs(this.column - p.column);
	}
}
