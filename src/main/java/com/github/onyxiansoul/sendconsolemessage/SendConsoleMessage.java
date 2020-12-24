package com.github.onyxiansoul.sendconsolemessage;

import com.github.onyxiansoul.sendconsolemessage.commands.SendConsoleMessageCommand;
import com.github.onyxiansoul.sendconsolemessage.listeners.CommandHandler;
import com.github.onyxiansoul.sendconsolemessage.listeners.TabCompleter;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.command.CommandMap;

public class SendConsoleMessage extends JavaPlugin {

    @Override
    public void onEnable(){

        String rawMessage = "Onyxtite issued server command: /scmsg &S:(3"; 
        String commandMessage = rawMessage.split(" issued server command: /")[1];
        System.out.println("command message:"+commandMessage);
        if(commandMessage.startsWith("scmsg")){
            System.out.println("Starts with scmsg");
        }
        
        
        
        //Instantiate consolesender to send warnings and messages with custom formatting.
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

        String commandName = getConfig().getString("CommandName", "SendConsoleMessage");
        String permissionNode = getConfig().getString("PermissionRequired", "sendConsoleMessage.sendMessage");
        String consolePrefix = getConfig().getString("ConsoleMessagePrefix", "");
        List<String> commandAliases = getConfig().getStringList("CommandAliases");
        boolean shouldFilter = getConfig().getBoolean("PreventDoubleMessages", true);
        
        try{
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
            commandMap.register("OnyxianSoulSendConsoleMessage",new SendConsoleMessageCommand(commandName, permissionNode, consolePrefix, commandAliases, Bukkit.getConsoleSender()));
            if(shouldFilter){
                /*ConsoleFilter cFilter = new ConsoleFilter(commandName, commandAliases);
                getLogger().setFilter(cFilter);
                Bukkit.getLogger().setFilter(cFilter);
                Logger.getLogger("Minecraft").setFilter(cFilter);*/
                
                
                Logger logger = (Logger) LogManager.getRootLogger();
                logger.addFilter(new SendConsoleMsgCmdFilter(commandName, commandAliases));
            }
        }
        catch(IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e){
            e.printStackTrace();
        }

        
        //((CommandMap) commandMap).

    }




}

