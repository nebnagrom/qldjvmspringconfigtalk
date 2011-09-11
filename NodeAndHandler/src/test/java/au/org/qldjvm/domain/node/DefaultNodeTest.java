package au.org.qldjvm.domain.node;

import junit.framework.TestCase;

import org.junit.Test;

import au.org.qldjvm.domain.node.Node;
import au.org.qldjvm.service.handler.CarHandler;
import au.org.qldjvm.service.handler.Handler;

public class DefaultNodeTest {

    @Test
    public void testConstructor() {
        String name = "foo";
        Node node = new DefaultNode(name);
        TestCase.assertEquals(name, node.getName());
    }
    
    @Test
    public void testAddHandler() {
        Handler handler = new CarHandler();
        Node node = new DefaultNode();
        node.addHandler(handler);
        TestCase.assertEquals(handler, node.getHandlers().get(0));
    }
}
