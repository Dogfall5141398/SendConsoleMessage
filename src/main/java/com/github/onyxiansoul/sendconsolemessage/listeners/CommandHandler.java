package com.github.onyxiansoul.sendconsolemessage.listeners;

import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

public class CommandHandler implements CommandExecutor {
    private String commandName;
    private ConsoleCommandSender consoleSender;
    public CommandHandler(String commandName, ConsoleCommandSender consoleSender) {
        this.commandName = commandName;
        this.consoleSender = consoleSender;
    }


        @Override
        public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

            if (command.getName().equalsIgnoreCase(commandName)) {

                //If there is no message, return.
                if( args.length ==0) {
                    return false;
                }

                //Translate color codes to color, and add all the words to the message that needs to be sent.
                String message = ChatColor.translateAlternateColorCodes('&',StringUtils.join(args, " "));

                //send the message
                consoleSender.sendMessage(message);

                return true;
            }
            return false;
    }


}
