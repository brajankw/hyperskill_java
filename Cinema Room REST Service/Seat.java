package cinema;


import com.fasterxml.jackson.annotation.JsonProperty;

public class Seat {
    @JsonProperty("row")
    private int row;
    @JsonProperty("column")
    private int column;
    @JsonProperty("price")
    private int price;

    public Seat() {
        super();
    }
    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        if (this.row > 4) this.price = 8;
        else this.price = 10;
    }

    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Seat seat = (Seat) o;
        return row == seat.row && column == seat.column;
    }
}
