package it.sijmen.movienotifier.util;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.UnsynchronizedAppenderBase;
import com.rollbar.Rollbar;

import java.util.HashMap;

public class RollbarAppender extends UnsynchronizedAppenderBase<ILoggingEvent> {

    private String apiKey;
    private String environment;
    private Rollbar rollbar;

    @Override
    public void start() {
        started = true;
        if (this.apiKey == null || this.apiKey.isEmpty()) {
            addError("No apiKey set for the appender named [" + getName() + "].");
            started = false;
            return;
        }
        if (this.environment == null || this.environment.isEmpty()) {
            addError("No environment set for the appender named [" + getName() + "].");
            started = false;
            return;
        }
        if(this.apiKey.equals("disable"))
            return;
        rollbar = new Rollbar(apiKey, environment)
                .framework("Spring Boot");
    }

    @Override
    protected void append(ILoggingEvent eventObject) {
        if(rollbar == null)
            return;
        if(eventObject.getMessage().startsWith("ROLLBAR:"))
            return;
        try{
            sendRollbarMessage(eventObject.getLevel(), eventObject.getMessage(), makeArgsMap(eventObject.getArgumentArray()));
        }catch (Exception e){
            addError("ROLLBAR: cannot store rollbar error");
            e.printStackTrace();
        }
    }

    private void sendRollbarMessage(Level level, String message, HashMap<String, Object> msgMap) {
        switch (level.levelInt){
            case Level.ERROR_INT:
                rollbar.error(message, msgMap);
                break;
            case Level.WARN_INT:
                rollbar.warning(message, msgMap);
                break;
            default:
                break;
        }
    }

    private HashMap<String, Object> makeArgsMap(Object[] argumentArray){
        HashMap<String, Object> out = new HashMap<>();
        if(argumentArray == null)
            return out;
        for (int i = 0; i < argumentArray.length; i++)
            out.put("arg_"+i, argumentArray[i]);
        return out;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setEnvironment(String environment) {
        this.environment = environment;
    }
}
