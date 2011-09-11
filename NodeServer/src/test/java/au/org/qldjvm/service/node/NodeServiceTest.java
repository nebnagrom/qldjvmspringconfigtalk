package au.org.qldjvm.service.node;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import au.org.qldjvm.domain.node.DefaultNode;
import au.org.qldjvm.domain.node.Node;

public class NodeServiceTest {

    private NodeService nodeService;
    
    @Before
    public void setup() {
        nodeService = new NodeService();
    }
    
    @Test
    public void testAddNode() {

        String nodeName = "foo";
        Node node = new DefaultNode(nodeName);
        nodeService.addNode(node);
        TestCase.assertEquals(node, nodeService.getNode(nodeName));
    }
}
