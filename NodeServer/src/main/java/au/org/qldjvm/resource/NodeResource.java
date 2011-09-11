package au.org.qldjvm.resource;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import au.org.qldjvm.domain.node.Node;
import au.org.qldjvm.service.node.NodeService;

@Component
@Path("node")
public class NodeResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeResource.class);

    private NodeService nodeService;

    @GET
    @Path("{nodeName}/{messageType}/{message}")
    public String receiveMessage(@PathParam("nodeName") String nodeName, @PathParam("messageType") String messageType,
            @PathParam("message") String message) {
        
        LOGGER.debug("message received for node " + nodeName);

        Node node = nodeService.getNode(nodeName);

        if (node == null) {
            throw new WebApplicationException(Status.BAD_REQUEST);
        }

        List<String> messageResponses = node.handleMessage(message);

        String response = "processed node name " + nodeName + " message Type " + messageType + " message " + message
                + "<br>";
        for (String messageResponse : messageResponses) {
            response += " ";
            response += messageResponse;
        }
        return response;
    }

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
