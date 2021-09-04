package be.akalyax.mylittlelabyrinth;

import be.akalyax.mylittlelabyrinth.data.FilePlayerConfig;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.io.IOException;
import java.util.Objects;

public class Command implements CommandExecutor {
    private Mylittlelabyrinth plugin;
    public Command(Mylittlelabyrinth plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {

        if (!(sender instanceof Player))
        {
            return true;
        }
        final Player player = (Player) sender;
        if (!player.isOp())
        {
            player.sendMessage("§c"+ plugin.getConfig().getString("messages.dont-have-permission"));
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("list"))
        {
            player.sendMessage("§ddonjon disponible:");
            player.sendMessage("§----------------");
            for (String string :plugin.getConfig().getConfigurationSection("myDungeon").getKeys(false)) {
                player.sendMessage("§d"+ string);
            }
            return true;
        }
        if (args.length == 1 && args[0].equalsIgnoreCase("test"))
        {
            FilePlayerConfig data = new FilePlayerConfig();
            try {
                player.sendMessage("Votre niveau enregistre : "+String.valueOf(data.getLvl(player)));
            } catch (IOException e) {
                player.sendMessage("Vous n'avez pas de niveau enregistre.");
            }

        }else if(args.length == 1 && (args[0].equalsIgnoreCase("r" ) || args[0].equalsIgnoreCase("reload")))
        {
            plugin.saveDefaultConfig();
            plugin.reloadConfig();
            player.sendMessage("§a"+ plugin.getConfig().getString("messages.reload"));
        }else if(args.length == 1 && args[0].equalsIgnoreCase("help"))
        {
            for (String string : plugin.getConfig().getStringList("messages.help-command"))
            {
                player.sendMessage("§e"+string);
            }
        }else if(args.length == 1 && args[0].equalsIgnoreCase("destroy"))
        {
            FilePlayerConfig data = new FilePlayerConfig();
            try {
                data.fileDestroy(player);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(args.length == 3)
        {
            dungeoncommand(player,args);
        }else
        {
            player.sendMessage(Objects.requireNonNull(plugin.getConfig().getString("messages.command-doesnt-exist")));
        }
        return true;
    }
    public void dungeoncommand(Player player,String[] args){
        StructureCreation pos = new StructureCreation();
        FilePlayerConfig data = new FilePlayerConfig();
        try
        {
            int xParameter = plugin.getConfig().getInt("principal-configuration.x-grid-length");
            int yParameter = plugin.getConfig().getInt("principal-configuration.y-grid-length");
            int zParameter = Integer.parseInt(args[1]);
            pos.comsender(player);
            if(plugin.getConfig().getConfigurationSection("myDungeon").contains(args[2]))
            {
                if(args[0].equalsIgnoreCase("gen")){
                    player.performCommand("dxl play mfd");
                    data.fileCreation(player,"1");
                }else if(args[0].equalsIgnoreCase("nextlvl")){

                    if(!player.getWorld().getName().equals("world")){
                    player.performCommand("dxl leave");
                    }
                    player.performCommand("dxl play mfd");
                    data.addLvl(player);
                }

                pos.structureDataForCreation(xParameter, zParameter, yParameter, plugin.getMyConfig(), args[2]);
            }else
            {
                //player.sendMessage(Objects.requireNonNull(myConfigs.getConfig().getString("messages.doesnt-exist")));
                player.sendMessage("La structure de donjon demandee est inexistante : " + args[2]);
            }
        } catch (Exception e)
        {
            //player.sendMessage(Objects.requireNonNull(myConfigs.getConfig().getString("messages.command-doesnt-exist")));
            player.sendMessage("Erreur de creation de donjon... Contactez un admin.");
            e.printStackTrace();
        }
    }
}
