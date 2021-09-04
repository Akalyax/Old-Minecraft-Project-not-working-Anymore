package be.akalyax.mylittlelabyrinth.data;

import java.util.HashMap;

public class Dungeon {
    private String name;
    private String salleDepart;
    private String salleArrivee;
    private String northernSouthernRoad;
    private String easternWesternRoad;
    private int distBetRoom;
    private int distanceForRoad;
    private int minimalRoomNumber;
    private int maximalRoomNumber;
    private HashMap<String,Room> stringRoomHashMap;
    private HashMap<String,Mobs> mobsHashMap;
    private HashMap<String,Boss> bossHashMap;
    public Dungeon(String name, String salleDepart, String salleArrivee, String northernSouthernRoad, String easternWesternRoad, int distBetRoom, int distanceForRoad, int minimalRoomNumber, int maximalRoomNumber) {
        this.name = name;
        this.salleDepart = salleDepart;
        this.salleArrivee = salleArrivee;
        this.northernSouthernRoad = northernSouthernRoad;
        this.easternWesternRoad = easternWesternRoad;
        this.distBetRoom = distBetRoom;
        this.distanceForRoad = distanceForRoad;
        this.minimalRoomNumber = minimalRoomNumber;
        this.maximalRoomNumber = maximalRoomNumber;
        this.stringRoomHashMap = new HashMap<>();
        this.mobsHashMap = new HashMap<>();
    }
    public Dungeon() {
        this.stringRoomHashMap = new HashMap<>();
        this.mobsHashMap = new HashMap<>();
        this.bossHashMap = new HashMap<>();
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSalleDepart() {
        return salleDepart;
    }
    public void setSalleDepart(String salleDepart) {
        this.salleDepart = salleDepart;
    }
    public String getSalleArrivee() {
        return salleArrivee;
    }
    public void setSalleArrivee(String salleArrivee) {
        this.salleArrivee = salleArrivee;
    }
    public String getNorthernSouthernRoad() {
        return northernSouthernRoad;
    }
    public void setNorthernSouthernRoad(String northernSouthernRoad) { this.northernSouthernRoad = northernSouthernRoad; }
    public String getEasternWesternRoad() {
        return easternWesternRoad;
    }
    public void setEasternWesternRoad(String easternWesternRoad) {
        this.easternWesternRoad = easternWesternRoad;
    }
    public int getDistBetRoom() {
        return distBetRoom;
    }
    public void setDistBetRoom(int distBetRoom) {
        this.distBetRoom = distBetRoom;
    }
    public int getDistanceForRoad() {
        return distanceForRoad;
    }
    public void setDistanceForRoad(int distanceForRoad) {
        this.distanceForRoad = distanceForRoad;
    }
    public int getMinimalRoomNumber() {
        return minimalRoomNumber;
    }
    public void setMinimalRoomNumber(int minimalRoomNumber) {
        this.minimalRoomNumber = minimalRoomNumber;
    }
    public int getMaximalRoomNumber() {
        return maximalRoomNumber;
    }
    public void setMaximalRoomNumber(int maximalRoomNumber) {
        this.maximalRoomNumber = maximalRoomNumber;
    }
    public HashMap<String, Room> getStringRoomHashMap() {
        return stringRoomHashMap;
    }
    public void setStringRoomHashMap(HashMap<String, Room> stringRoomHashMap) { this.stringRoomHashMap = stringRoomHashMap; }
    public HashMap<String, Mobs> getMobsHashMap() {
        return mobsHashMap;
    }
    public void setMobsHashMap(HashMap<String, Mobs> mobsHashMap) {
        this.mobsHashMap = mobsHashMap;
    }
    public HashMap<String, Boss> getBossHashMap() { return bossHashMap; }
    public void setBossHashMap(HashMap<String, Boss> bossHashMap) { this.bossHashMap = bossHashMap; }
}
