package be.akalyax.mylittlelabyrinth;


import be.akalyax.mylittlelabyrinth.data.MyConfig;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;

public final class Mylittlelabyrinth extends JavaPlugin {
    private Logger logger = Logger.getLogger("Minecraft");
    private MyConfig myConfig;
    public MyConfig getMyConfig() {
        return myConfig;
    }
    public void onEnable() {
        saveDefaultConfig();
        logger.info("be.akalyax.mylittlelaby.Starting");
        myConfig = new MyConfig(this);
        myConfig.getDungeon();
        getCommand("mll").setExecutor(new Command(this));
    }
    public void onDisable() {
        logger.info("be.akalyax.mylittlelaby.End");
    }
}
