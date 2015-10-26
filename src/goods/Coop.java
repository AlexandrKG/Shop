package goods;


public class Coop extends Goods {

    private int high;
    private int width;

    public Coop() {
        category = "Coops";
    }

    public int getHigh() {
        return high;
    }

    public int getWidth() {
        return width;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
