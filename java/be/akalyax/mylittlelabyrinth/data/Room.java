package be.akalyax.mylittlelabyrinth.data;

public class Room {
    private String schem;
    private int coef;
    public Room(String schem, int coef) {
        this.schem = schem;
        this.coef = coef;
    }
    public String getSchem() {
        return schem;
    }
    public void setSchem(String schem) {
        this.schem = schem;
    }
    public int getCoef() { return coef; }
    public void setCoef(int coef) {
        this.coef = coef;
    }
}
