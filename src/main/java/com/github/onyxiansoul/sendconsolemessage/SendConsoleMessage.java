package com.github.onyxiansoul.sendconsolemessage;

import com.github.onyxiansoul.sendconsolemessage.filters.SendConsoleMsgCmdFilter;
import com.github.onyxiansoul.sendconsolemessage.commands.SendConsoleMessageCmd;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.lang.reflect.Field;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandMap;

public class SendConsoleMessage extends JavaPlugin {

    @Override
    public void onEnable(){
                
        //Get consolesender instance to send warnings and messages with custom formatting.
        ConsoleCommandSender consoleSender = getServer().getConsoleSender();

        //Create the plugin folder if it doesn't exist.
        getDataFolder().mkdirs();

        //create the default config file if it doesn't exist.
        File configfile = new File(getDataFolder(), "config.yml");
        if (!configfile.exists()) {
            consoleSender.sendMessage(ChatColor.GOLD+"[Warning][ConsoleMessageSender] config.yml not found, creating!");
            saveDefaultConfig();
        }

        //shut down if the config says the plugin shouldn't be enabled
        if (!getConfig().getBoolean("enabled")){
            consoleSender.sendMessage(Color.RED+"[Warning][ConsoleMessageSender] Plugin has been disabled via config. It will now shut down.");
            getPluginLoader().disablePlugin(this);
        }

        //Get the configuration values
        String commandName = getConfig().getString("CommandName", "SendConsoleMessage");
        String permissionNode = getConfig().getString("PermissionRequired", "sendConsoleMessage.sendMessage");
        String consolePrefix = getConfig().getString("ConsoleMessagePrefix", "");
        List<String> commandAliases = getConfig().getStringList("CommandAliases");
        boolean shouldFilter = getConfig().getBoolean("PreventDoubleMessages", true);
        boolean bStatsEnabled = getConfig().getBoolean("bStats",false);
        
        //If the command map is accesible, register the command.
        try{
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("OnyxianSoulSendConsoleMessage",new SendConsoleMessageCmd(commandName, permissionNode, consolePrefix, commandAliases, Bukkit.getConsoleSender()));
            
            //If its enabled, add the filter to prevent duplicate console messages.
            if(shouldFilter){
                Logger logger = (Logger) LogManager.getRootLogger();
                logger.addFilter(new SendConsoleMsgCmdFilter(commandName, commandAliases));
            }
            
            //If its allowed, enable bStats.
            if(bStatsEnabled){
               Metrics metrics = new Metrics(this, 9750);
            }
        }
        catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e){
            e.printStackTrace();
        }
    }
}

