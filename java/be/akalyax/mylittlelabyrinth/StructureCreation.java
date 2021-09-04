package be.akalyax.mylittlelabyrinth;

import be.akalyax.mylittlelabyrinth.data.FilePlayerConfig;
import be.akalyax.mylittlelabyrinth.data.Dungeon;
import be.akalyax.mylittlelabyrinth.data.MyConfig;
import com.boydti.fawe.FaweCache;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.math.BlockVector3;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.IOException;
import java.util.*;


public class StructureCreation
{

    public int xGridLength;
    public int zGridLength;
    public int gridHeight;
    public int xEnCours;
    public int yEnCours;
    public int xExplorer;
    public int yExplorer;
    public int explorerYfin;
    public int explorerXfin;
    public int numberOfRoom;
    public int distanceBetweenRoom;
    public int distanceForRoad;
    public int minimalRoom;
    public int maximalRoom;
    public int[][] Grid;
    public String northenSouthernRoad;
    public String easternWesternRoad;
    public String path="";
    public String dungeonType;
    public String sallededepart;
    public String salledarrive;
    public Player play;
    public File schematicFile;
    public File file = new File(".");
    public Clipboard load;
    public int floor;
    public int[][] roomcoord;
    public Dungeon myDungeon;


    public void structureDataForCreation() throws IOException {
        numberOfRoom = 0;
        generateGridAndStartPoint();
    }
    public void structureDataForCreation(int x, int height, int y, MyConfig myConfigs, String dungeonType) throws IOException {

        myDungeon = myConfigs.getDungeon().get(dungeonType);
        this.dungeonType = dungeonType;
        FilePlayerConfig data = new FilePlayerConfig();
        this.floor = data.getLvl(play);
        distanceBetweenRoom = myDungeon.getDistBetRoom();
        distanceForRoad = myDungeon.getDistanceForRoad();
        minimalRoom = myDungeon.getMinimalRoomNumber();
        maximalRoom = myDungeon.getMaximalRoomNumber();
        northenSouthernRoad = myDungeon.getNorthernSouthernRoad();
        easternWesternRoad = myDungeon.getEasternWesternRoad();
        sallededepart=myDungeon.getSalleDepart();
        salledarrive=myDungeon.getSalleArrivee();
        numberOfRoom = 0;
        xGridLength = x;
        zGridLength = y;
        gridHeight = height;
        Grid = new int[xGridLength +1][zGridLength +1];
        generateGridAndStartPoint();

    }
    public void generateGridAndStartPoint() throws IOException {
        for (int x = 0; x < zGridLength; x++)
        {
            for (int y = 0; y < xGridLength; y++)
            {
                Grid[x][y] = 0;
            }
        }
        Random randomX = new Random();
        Random randomY = new Random();
        xExplorer = randomX.nextInt(xGridLength - 1);
        yExplorer = randomY.nextInt(zGridLength - 1);

        explorer(xExplorer, yExplorer);
    }
    public void explorer(int x, int y) throws IOException {
        numberOfRoom++;
        Grid[y][x] = 1;

        List<String> possibleDirection = new ArrayList<>();
        if ((y - 1) >= 0 && Grid[y-1][x] == 0)
        {
            possibleDirection.add("N");

        }
        if ((y + 1) < zGridLength && Grid[y+1][x] == 0)
        {
            possibleDirection.add("S");
        }
        if ((x + 1) < xGridLength && Grid[y][x+1] == 0)
        {
            possibleDirection.add("E");
        }
        if ((x - 1) >= 0 && Grid[y][x-1] == 0)
        {
            possibleDirection.add("O");
        }
        if (possibleDirection.size()==0)
        {
            structureDataForCreation();
            return;
        }
        int nbrOfDirectionAvailable = possibleDirection.size();
        if (nbrOfDirectionAvailable == 1|| numberOfRoom == maximalRoom)
        {
            if (numberOfRoom <= minimalRoom || numberOfRoom >= maximalRoom)
            {
                structureDataForCreation();
            }else
            {
                explorerYfin = y;
                explorerXfin = x;
                generateRoom();
            }
            return;
        }
        int choosenPath = getRandom(0,nbrOfDirectionAvailable-1);
        String path = possibleDirection.get(choosenPath);
        switch (path)
        {
            case "N":
                explorer(x, (y-1));
                break;
            case "S":
                explorer(x, (y+1));
                break;
            case "E":
                explorer((x + 1), y);
                break;
            case "O":
                explorer((x-1), y);
                break;
        }
    }
    private void generateRoom() throws IOException {
        String randomRoom;
        int room=1;
        roomcoord = new int[numberOfRoom][2];
        for (int x = 0; x < xGridLength +1 ; x++)
        {
            for (int y = 0; y < zGridLength +1; y++)
            {
                if (Grid[y][x] == 1) {
                   xEnCours = x * distanceBetweenRoom;
                   yEnCours = y * distanceBetweenRoom;
                   path = file.getCanonicalPath();
                   if (x == xExplorer && y == yExplorer)
                   {
                       roomcoord[0][0] = xEnCours;
                       roomcoord[0][1] = yEnCours;
                        randomRoom = sallededepart;
                    } else if (x == explorerXfin && y == explorerYfin)
                    {
                        roomcoord[numberOfRoom-1][0] = xEnCours;
                        roomcoord[numberOfRoom-1][1] = yEnCours;
                        randomRoom = salledarrive;
                        play.sendMessage("Generation de l'etage " + floor + ". Nombre de pieces : " + numberOfRoom + ".");
                    } else{
                        roomcoord[room][0] = xEnCours;
                        roomcoord[room][1] = yEnCours;
                        randomRoom = randomRoomConfiguration();
                        room++;
                    }
                    schematicFile = new File(path + File.separator + "plugins" + File.separator + "FastAsyncWorldEdit" + File.separator + "schematics" + File.separator + "MyLittleLaby"+File.separator +randomRoom);
                    load = loadclipboard();
                    load.paste(new BukkitWorld(play.getWorld()), BlockVector3.at(xEnCours, gridHeight, yEnCours), true, true, true, null);
                    generateRoad(x,y);
                }
            }
        }
        Location premiereSalle = new Location(play.getWorld(), roomcoord[0][0], gridHeight + 1, roomcoord[0][1]);
        play.teleport(premiereSalle);
        pveCreation(roomcoord,numberOfRoom-1);

    }
    private void pveCreation(int[][] roomcoord, int i) {
        String thisRoomMob = null;
        String command;
        int quantity = 0;
        boolean invocation;
        for (int j = 1; j <= i ; j++) {
            invocation=false;
            if ((j == i) && (floor % 10 == 0)) {
                thisRoomMob = randomBossConfiguration();
                quantity = myDungeon.getBossHashMap().get(thisRoomMob).getQuantity();
                invocation=true;
            } else if(j!=i) {
                thisRoomMob = randomMobsConfiguration();
                quantity = myDungeon.getMobsHashMap().get(thisRoomMob).getQuantity();
                invocation=true;
            }
            if(invocation){
                command = "mm m s -s " + thisRoomMob + ":" + floor + " " + quantity + " " + play.getWorld().getName() + "," + roomcoord[j][0] + "," + gridHeight + "," +
                        roomcoord[j][1];
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
            }
            if (j != i || floor % 10 == 0) {
                command = "mm m s -s barriere:" + myDungeon.getDistanceForRoad()+" 1 " + play.getWorld().getName() + "," + roomcoord[j][0] + "," + gridHeight + "," +
                        roomcoord[j][1];
            }else{
                command = "mm m s -s "+ myDungeon.getName() +" 1 " + play.getWorld().getName() + "," + roomcoord[j][0] + "," + gridHeight + "," + roomcoord[j][1];
            }
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(),command);
        }
        File f = new File(path + File.separator + "plugins" + File.separator + "FastAsyncWorldEdit" + File.separator + "clipboard");
        deleteFolder(f);
    }
    public void generateRoad(int x, int y) throws IOException {
        if ((y - 1) >= 0 && Grid[y - 1][x] != 0 && Material.AIR == play.getWorld().getBlockAt(xEnCours, gridHeight,yEnCours- distanceForRoad).getType())
        {
            schematicFile = northenRoad();
            load = loadclipboard();
            load.paste(new BukkitWorld(play.getWorld()), BlockVector3.at((xEnCours), gridHeight, (yEnCours) - distanceForRoad), true, true, true, null);
        }
        if ((y + 1) < zGridLength && Grid[y + 1][x] != 0 && Material.AIR == play.getWorld().getBlockAt(xEnCours, gridHeight,yEnCours+ distanceForRoad).getType())
        {
            schematicFile = northenRoad();
            load =loadclipboard();
            load.paste(new BukkitWorld(play.getWorld()), BlockVector3.at((xEnCours), gridHeight, (yEnCours) + distanceForRoad), true, true, true, null);
        }
        if ((x + 1) < xGridLength && Grid[y][x + 1] != 0 && Material.AIR == play.getWorld().getBlockAt(xEnCours+ distanceForRoad, gridHeight,yEnCours).getType())
        {
            schematicFile = easternRoad();
            load = loadclipboard();
            load.paste(new BukkitWorld(play.getWorld()), BlockVector3.at((xEnCours) + distanceForRoad, gridHeight, (yEnCours)), true, true, true, null);
        }
        if ((x - 1) >= 0 && Grid[y][x - 1] != 0 && Material.AIR == play.getWorld().getBlockAt(xEnCours- distanceForRoad, gridHeight,yEnCours).getType())
        {
            schematicFile = easternRoad();
            load = loadclipboard();
            load.paste(new BukkitWorld(play.getWorld()), BlockVector3.at((xEnCours) - distanceForRoad, gridHeight, (yEnCours)), true, true, true, null);
        }
    }
    public File northenRoad(){
        return new File(path + File.separator + "plugins" + File.separator + "FastAsyncWorldEdit" + File.separator + "schematics" + File.separator + "MyLittleLaby"+File.separator +northenSouthernRoad);
    }
    public File easternRoad(){
        return new File(path + File.separator + "plugins" + File.separator + "FastAsyncWorldEdit" + File.separator + "schematics" + File.separator + "MyLittleLaby"+File.separator +easternWesternRoad);
    }
    public String randomBossConfiguration(){
        int totalBoss = 0;
        String[] myboss;
        myboss = new String[myDungeon.getBossHashMap().size()/5];
        int numberofboss=0;
        for (String name : myDungeon.getBossHashMap().keySet()) {
            if (!(name.contains(".")) &&
                    myDungeon.getBossHashMap().get(name).getLvlmini()<=floor &&
                    myDungeon.getBossHashMap().get(name).getLvlmax()>=floor) {
                totalBoss += myDungeon.getBossHashMap().get(name).getCoef();
                myboss[numberofboss] = myDungeon.getBossHashMap().get(name).getName();
                numberofboss++;
            }
        }
        numberofboss=0;
        for (int r= (int) (Math.random()*totalBoss); numberofboss<= myDungeon.getBossHashMap().size();++numberofboss){
            r -= myDungeon.getBossHashMap().get(myboss[numberofboss]).getCoef();
            if (r<= 0.0) break;
        }
        return myDungeon.getBossHashMap().get(myboss[numberofboss]).getName();
    }
    public String randomMobsConfiguration() {
        int totalMonster = 0;
        String[] mymob;
        mymob = new String[myDungeon.getMobsHashMap().size()/5];
        int numberofmob=0;
        for (String name : myDungeon.getMobsHashMap().keySet()) {
            if (!(name.contains(".")) &&
                    myDungeon.getMobsHashMap().get(name).getLvlmini()<=floor &&
                    myDungeon.getMobsHashMap().get(name).getLvlmax()>=floor) {
                totalMonster += myDungeon.getMobsHashMap().get(name).getCoef();
                mymob[numberofmob] = myDungeon.getMobsHashMap().get(name).getName();
                numberofmob++;
            }
        }
        numberofmob=0;
        for (int r= (int) (Math.random()*totalMonster); numberofmob<= myDungeon.getMobsHashMap().size();++numberofmob){
            r -= myDungeon.getMobsHashMap().get(mymob[numberofmob]).getCoef();
            if (r<= 0.0) break;
        }
        return myDungeon.getMobsHashMap().get(mymob[numberofmob]).getName();
    }
    public String randomRoomConfiguration()
    {
        int totalRooms=0;

            for(int x=1; x<= (myDungeon.getStringRoomHashMap().size()/3) ;x++){
                totalRooms += myDungeon.getStringRoomHashMap().get(String.valueOf(x)).getCoef();
            }

        int idx =1;
            for (int r = (int) (Math.random() * totalRooms); idx <= myDungeon.getStringRoomHashMap().size(); ++idx) {
                r -= myDungeon.getStringRoomHashMap().get(String.valueOf(idx)).getCoef();
                if (r <= 0.0) break;
            }
        return myDungeon.getStringRoomHashMap().get(String.valueOf(idx)).getSchem();
    }
    public Clipboard loadclipboard() throws IOException
    {
        return Objects.requireNonNull(ClipboardFormats.findByFile(schematicFile)).load(schematicFile);
    }
    public int getRandom(int min,int max)
    {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min);
    }
    public void comsender(Player player)
    {
        play = player;
    }
    public void deleteFolder(File file){
        for (File subFile : file.listFiles()) {
            if(subFile.isDirectory()) {
                deleteFolder(subFile);
            } else {
                subFile.delete();
            }
            file.delete();
        }

    }
}
