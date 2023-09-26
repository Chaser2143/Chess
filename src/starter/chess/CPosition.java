package chess;

public class CPosition implements ChessPosition{

    int row;
    int column;

    public CPosition(int row, int column){
        //Constructor, i think this is right... right?
        setRow(row);
        setColumn(column);
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }
}
