package au.org.qldjvm.domain.node;

import java.util.List;

import au.org.qldjvm.service.handler.Handler;

public interface Node {

    public List<String> handleMessage(String message);

    public void setHandlers(List<Handler> handlers);

    public List<Handler> getHandlers();

    public boolean addHandler(Handler handler);

    public String getName();
    
    public void setName(String name);
}
