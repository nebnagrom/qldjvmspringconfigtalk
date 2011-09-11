package au.org.qldjvm.domain.node;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.org.qldjvm.service.handler.Handler;

public class DefaultNode implements Node {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultNode.class);

    private String name;

    private List<Handler> handlers = new ArrayList<Handler>();

    public DefaultNode() {
        super();
    }

    public DefaultNode(String name) {
        super();
        this.name = name;
    }

    public List<String> handleMessage(String message) {

        LOGGER.debug("node " + name + " handling message " + message);

        List<String> handlingResults = new ArrayList<String>();
        for (Handler handler : handlers) {
            handlingResults.add(handler.handle(message));
        }

        return handlingResults;
    }

    public void setHandlers(List<Handler> handlers) {
        this.handlers = handlers;
    }

    public List<Handler> getHandlers() {
        return handlers;
    }

    public boolean addHandler(Handler handler) {
        return handlers.add(handler);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
