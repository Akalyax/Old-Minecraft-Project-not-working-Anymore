package be.akalyax.mylittlelabyrinth.data;

import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FilePlayerConfig {

    public String path = "";
    public File file = new File(".");
    public File f;
    public int floor;

    public void fileCreation(Player play, String lvlIndication) throws IOException {
        try {
            myFile(play);
            f.createNewFile();
            modifyFile(f,lvlIndication);
        }
        catch (Exception e){
            System.err.println(e);
        }
    }

    public void addLvl(Player play) throws IOException {
        myFile(play);
        Scanner myReader = new Scanner(f);
        floor = Integer.parseInt(myReader.nextLine())+1;
        modifyFile(f,String.valueOf(floor));
    }

    public void fileDestroy(Player play) throws IOException {
        myFile(play);
        f.delete();
    }

    public int getLvl(Player play) throws IOException {
        myFile(play);
        Scanner myReader = new Scanner(f);
        return Integer.parseInt(myReader.nextLine());
    }

    public void modifyFile(File f, String s) throws IOException {
        Path pathing = Paths.get(String.valueOf(f));
        byte[] bs = s.getBytes();
        Path written = Files.write(pathing,bs);
    }

    public void myFile(Player play) throws IOException {
        path = file.getCanonicalPath();
        f = new File(path + File.separator+ "plugins" + File.separator + "Mylittlelabyrinth" + File.separator + "instances" + File.separator + play.getUniqueId() + ".yml" );
    }

}
