package be.akalyax.mylittlelabyrinth.data;

public class Mobs {
    private String name;
    private int lvlmini;
    private int lvlmax;
    private int coef;
    private int quantity;
    public Mobs(String name, int lvlmini, int lvlmax, int coef, int quantity) {
        this.name = name;
        this.lvlmini = lvlmini;
        this.lvlmax = lvlmax;
        this.coef = coef;
        this.quantity = quantity;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLvlmini() {
        return lvlmini;
    }
    public void setLvlmini(int lvlmini) {
        this.lvlmini = lvlmini;
    }
    public int getLvlmax() {
        return lvlmax;
    }
    public void setLvlmax(int lvlmax) {
        this.lvlmax = lvlmax;
    }
    public int getCoef() {
        return coef;
    }
    public void setCoef(int coef) {
        this.coef = coef;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
