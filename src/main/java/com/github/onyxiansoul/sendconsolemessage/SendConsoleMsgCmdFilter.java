package com.github.onyxiansoul.sendconsolemessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.Filter;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;


public class SendConsoleMsgCmdFilter extends AbstractFilter {
    
    private final List<String> aliases;
    
    public SendConsoleMsgCmdFilter(String commandName, List<String> commandAliases){
        aliases = new ArrayList<>();
        aliases.add(commandName.toLowerCase());
        for (String commandAlias: commandAliases){
            aliases.add(commandAlias.toLowerCase());
        }
    }

        private Filter.Result validateMessage(Message message) {
            if (message == null) {
                return Result.NEUTRAL;
            }
            
            return validateMessage(message.getFormattedMessage());
        }

        private Filter.Result validateMessage(String rawMessage) {
            if (rawMessage.contains(" issued server command: /")){
                String trimmedMSG = StringUtils.substringAfter(rawMessage, "issued server command: /").toLowerCase();
                for (String consoleMessageCommand: aliases){
                    if (trimmedMSG.startsWith(consoleMessageCommand)){
                            return Result.DENY;
                    }
                }
            }
            return Result.NEUTRAL;
        }

        @Override
        public Filter.Result filter(LogEvent event) {
            Message candidate = null;
            if (event != null) {
                candidate = event.getMessage();
            }
            return validateMessage(candidate);
        }

        /*@Override
        public Filter.Result filter(Logger logger, Level level, Marker marker, Message msg, Throwable t) {
            return validateMessage(msg);
        }*/

        @Override
        public Filter.Result filter(Logger logger, Level level, Marker marker, Object msg, Throwable t) {
            String candidate = null;
            if (msg != null) {
                candidate = msg.toString();
            }
            return validateMessage(candidate);
        }

        @Override
        public Filter.Result filter(Logger logger, Level level, Marker marker, String msg, Object... params) {
            return validateMessage(msg);
        }
}
