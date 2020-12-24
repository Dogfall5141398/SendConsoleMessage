package com.github.onyxiansoul.sendconsolemessage.commands;
import com.google.common.collect.Lists;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class SendConsoleMessageCommand extends BukkitCommand {
    private final ConsoleCommandSender consoleSender;
    private final String consolePrefix;
    
    public SendConsoleMessageCommand(String commandName, String permissionNode, String consolePrefix, List<String> aliases, ConsoleCommandSender consoleSender){
        super(commandName,"Sends a message to the console","SendConsoleMessage <Your message>", aliases);
        this.consoleSender = consoleSender;
        this.consolePrefix = ChatColor.translateAlternateColorCodes('&',consolePrefix);
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {

        //If there is no message, return.
        if( args.length ==0) {
            return false;
        }

        //Translate color codes to color, and add all the words to the message that needs to be sent.
        String message = ChatColor.translateAlternateColorCodes('&',StringUtils.join(args, " "));

        //send the message
        consoleSender.sendMessage(consolePrefix+message);

        return true;
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return Lists.newArrayList("message");
        //return super.tabComplete(sender, alias, args); 
    }
}


    

