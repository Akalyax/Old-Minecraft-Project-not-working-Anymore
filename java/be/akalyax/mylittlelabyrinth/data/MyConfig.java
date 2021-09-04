package be.akalyax.mylittlelabyrinth.data;

import be.akalyax.mylittlelabyrinth.Mylittlelabyrinth;
import org.bukkit.configuration.file.FileConfiguration;
import java.util.HashMap;

public class MyConfig {
    private final FileConfiguration config;
    public MyConfig(Mylittlelabyrinth mylittlelabyrinth){
        this.config = mylittlelabyrinth.getConfig();
    }
    public HashMap<String,Dungeon> getDungeon(){
        HashMap<String, Dungeon> dungeons = new HashMap<>();
        HashMap<String, Room> dungeonsRooms;
        HashMap<String, Mobs> mobsSpawnHashMap;
        HashMap<String, Boss> bossSpawnHashMap;
        Dungeon dungeon;
        /**
         * Initialisation de donjon s'il en existe
         */
        if (this.config.getConfigurationSection("myDungeon") != null) {
            for (String dungeonName : this.config.getConfigurationSection("myDungeon").getKeys(true)){
                dungeon = new Dungeon();
                dungeon.setName(dungeonName);
                dungeon.setSalleDepart(this.config.getString("myDungeon." + dungeonName + ".salle-de-depart"));
                dungeon.setSalleArrivee(this.config.getString("myDungeon." + dungeonName + ".salle-d-arrivee"));
                dungeon.setNorthernSouthernRoad(this.config.getString("myDungeon." + dungeonName + ".northern-southern-road"));
                dungeon.setEasternWesternRoad(this.config.getString("myDungeon." + dungeonName + ".eastern-western-road"));
                dungeon.setDistBetRoom(this.config.getInt("myDungeon." + dungeonName + ".distance-between-room"));
                dungeon.setDistanceForRoad(this.config.getInt("myDungeon." + dungeonName + ".distance-for-road"));
                dungeon.setMinimalRoomNumber(this.config.getInt("myDungeon." + dungeonName + ".minimal-room-number"));
                dungeon.setMaximalRoomNumber(this.config.getInt("myDungeon." + dungeonName + ".maximal-room-number"));
                /**
                 * Initialisation des différentes Room qui constituent le donjon en cours d'initialisation
                 */
                if (this.config.getConfigurationSection("myDungeon." + dungeonName + ".dungeon-room") != null) {
                    dungeonsRooms = new HashMap<>();
                    for (String roomName :
                            this.config.getConfigurationSection("myDungeon." + dungeonName + ".dungeon-room").getKeys(true)){
                        String schem = this.config.getString("myDungeon." + dungeonName + ".dungeon-room." + roomName + ".schem");
                        int coef = this.config.getInt("myDungeon." + dungeonName + ".dungeon-room." + roomName + ".coef", 1);
                        dungeonsRooms.put(roomName, new Room(schem, coef));
                    }
                    dungeon.setStringRoomHashMap(dungeonsRooms);
                }
                /**
                 * Initialisation des différents Monstres qui peuplent le donjon en cours d'initialisation
                 */
                if (this.config.getConfigurationSection("myDungeon."+ dungeonName +".monster") != null){
                    mobsSpawnHashMap = new HashMap<>();
                    for (String mobName :
                            this.config.getConfigurationSection("myDungeon."+dungeonName+".monster").getKeys(true)){
                        String name = mobName;
                        int lvlmini = this.config.getInt("myDungeon." + dungeonName + ".monster." + mobName + ".lvlmini",1);
                        int lvlmax = this.config.getInt("myDungeon." + dungeonName + ".monster." + mobName + ".lvlmax",1000);
                        int coef = this.config.getInt("myDungeon." + dungeonName + ".monster." + mobName + ".coef",100);
                        int quantity = this.config.getInt("myDungeon." + dungeonName + ".monster." + mobName + ".quantity",10);
                        mobsSpawnHashMap.put(mobName, new Mobs(name,lvlmini,lvlmax,coef,quantity));
                    }
                    dungeon.setMobsHashMap(mobsSpawnHashMap);
                }
                /**
                 * Initialisation des différents Boss qui peuplent le donjon en cours d'initialisation
                 */
                if (this.config.getConfigurationSection("myDungeon."+ dungeonName +".boss") != null){
                    bossSpawnHashMap = new HashMap<>();
                    for (String bossName :
                            this.config.getConfigurationSection("myDungeon."+dungeonName+".boss").getKeys(true)){
                        String name = bossName;
                        int lvlmini = this.config.getInt("myDungeon." + dungeonName + ".boss." + bossName + ".lvlmini",1);
                        int lvlmax = this.config.getInt("myDungeon." + dungeonName + ".boss." + bossName + ".lvlmax",1000);
                        int coef = this.config.getInt("myDungeon." + dungeonName + ".boss." + bossName + ".coef",100);
                        int quantity = this.config.getInt("myDungeon." + dungeonName + ".boss." + bossName + ".quantity",10);
                        bossSpawnHashMap.put(bossName, new Boss(name,lvlmini,lvlmax,coef,quantity));
                    }
                    dungeon.setBossHashMap(bossSpawnHashMap);
                }
                /**
                 * Initialisation du donjon avec toutes les données sus-mentionné
                 */
               dungeons.put(dungeonName,dungeon);
            }
        }
        return dungeons;
    }
}
