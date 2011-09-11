package au.org.qldjvm.service.node;

import java.util.HashMap;
import java.util.Map;

import au.org.qldjvm.domain.node.Node;

public class NodeService {

    private Map<String, Node> nodes = new HashMap<String, Node>();

    public Node getNode(String nodeName) {
        return nodes.get(nodeName);
    }

    public void addNode(Node node) {

        nodes.put(node.getName(), node);
    }

    public void setNodes(Map<String, Node> nodes) {
        this.nodes = nodes;
    }
}
