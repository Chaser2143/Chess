package chess;

import java.util.Objects;

public class CPosition implements ChessPosition{

    int row;
    int column;

    public CPosition(int row, int column){
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

    private void incrementRow(){
        row++;
    }

    private void incrementCol(){
        column++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPosition cPosition = (CPosition) o;
        return row == cPosition.row && column == cPosition.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString(){
        return "(" + getRow() + "," + getColumn() + ")";
    }
}
