package au.org.qldjvm.resource;

import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;

import au.org.qldjvm.domain.node.DefaultNode;
import au.org.qldjvm.domain.node.Node;
import au.org.qldjvm.service.node.NodeService;

public class NodeResourceUnitTest {

    private NodeResource nodeResource;
    private NodeService nodeService;

    private IMocksControl mocksControl;

    @Before
    public void setup() {

        mocksControl = EasyMock.createControl();
        nodeService = mocksControl.createMock(NodeService.class);

        nodeResource = new NodeResource();
        nodeResource.setNodeService(nodeService);
    }

    @Test
    public void testNodesIsLookeUp() {

        String nodeName = "foo";
        Node node = new DefaultNode(nodeName);
        EasyMock.expect(nodeService.getNode(nodeName)).andReturn(node);

        mocksControl.replay();

        nodeResource.receiveMessage(nodeName, "dskla", "sdklf");

        mocksControl.verify();
    }

    @Test
    public void testExceptionWhenNoNode() {

        try {
            String nodeName = "foo";
            EasyMock.expect(nodeService.getNode(nodeName)).andReturn(null);
            mocksControl.replay();
            nodeResource.receiveMessage(nodeName, "dskla", "sdklf");
        } catch (WebApplicationException e) {

            TestCase.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), e.getResponse().getStatus());
            mocksControl.verify();
            return;
        }
        TestCase.fail("no exception thrown");
    }

    @Test
    public void testNodeIsInvoked() {

        Node node = mocksControl.createMock(Node.class);
        String message = " this is a message ";
        String messageResponse = " this is a response ";
        List<String> messageResponses = new LinkedList<String>();
        messageResponses.add(messageResponse);
        EasyMock.expect(node.handleMessage(message)).andReturn(messageResponses);
        String nodeName = "foo";
        EasyMock.expect(nodeService.getNode(nodeName)).andReturn(node);

        mocksControl.replay();

        nodeResource.receiveMessage(nodeName, "dskla", message);

        mocksControl.verify();
    }
}
