package com.github.onyxiansoul.sendconsolemessage.filters;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

/**A filter for the console to prevent duplicate logging on command.*/
public class SendConsoleMsgCmdFilter extends AbstractFilter {
    
    private final List<String> aliases;
    
    public SendConsoleMsgCmdFilter(String commandName, List<String> commandAliases){
        aliases = new ArrayList<>();
        aliases.add(commandName.toLowerCase());
        for (String commandAlias: commandAliases){
            aliases.add(commandAlias.toLowerCase());
        }
    }

    @Override
    public Filter.Result filter(LogEvent event) {
        if(event != null){
            Message message = event.getMessage();
            if(message !=null){
                String rawText = message.getFormattedMessage();
                if (rawText.contains(" issued server command: /")){
                    String trimmedMSG = StringUtils.substringAfter(rawText, "issued server command: /").toLowerCase();
                    for (String consoleMessageCommand: aliases){
                        if (trimmedMSG.startsWith(consoleMessageCommand)){
                            return Result.DENY;
                        }
                    }
                }
            }
        }
        return Result.NEUTRAL;
    }
        

}
