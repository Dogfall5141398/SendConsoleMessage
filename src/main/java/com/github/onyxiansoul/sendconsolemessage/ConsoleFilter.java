package com.github.onyxiansoul.sendconsolemessage;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Filter;
import java.util.logging.LogRecord;

public class ConsoleFilter implements Filter {
    private final List<String> aliases;
    
    public ConsoleFilter(String commandName, List<String> commandAliases){
        aliases = new ArrayList<>();
        aliases.add(commandName);
        for (String commandAlias: commandAliases){
            aliases.add(commandAlias);
        }
    }

    @Override
    public boolean isLoggable(LogRecord record) {
        System.out.println("chekcing loggable. Message: "+ record.getMessage());
        String[] messageParts =record.getMessage().split(" ", 1);
        System.out.println("Message parts:"+messageParts.toString());
        if (messageParts.length>1){
            String message = messageParts[1];
            System.out.println("Message parts length>1 , messageParts[1]="+ message);
                if (message.startsWith("issued server command: /")){
                    System.out.println("Message starts with issued server command...");
                    message = message.replace("issued server command: /", "");
                    for (String consoleMessageCommand: aliases){
                        System.out.println("checking if alias:"+ consoleMessageCommand+ "Is the alias messageparts[1] starts with after replacing issued server command");
                        if(message.startsWith(consoleMessageCommand)){
                            System.out.println("It is. returning false");
                            return false;
                    }
                }
            }
        }
        System.out.println("Returning true");
        return true;
    }
    


    
}
