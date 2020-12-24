package com.github.onyxiansoul.sendconsolemessage.listeners;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private final String commandName;


    public TabCompleter(String commandName){
        this.commandName = commandName;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player ) {
            String commandToComplete = command.getName();
            if (commandToComplete.equalsIgnoreCase(commandName)) {
                if (strings.length == 0) {
                    ArrayList<String> suggestions = new ArrayList<>();
                    suggestions.add(":O . 0");
                    return suggestions;
                }
                if (strings.length == 1) {
                    ArrayList<String> suggestions = new ArrayList<>();
                    suggestions.add("message");
                    return suggestions;
                }
                if (strings.length == 3) {
                    ArrayList<String> suggestions = new ArrayList<>();
                    suggestions.add(":O . 3");
                    return suggestions;
                }
            }
        }
        return null;
    }

}