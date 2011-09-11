package au.org.qldjvm.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.stereotype.Component;

import au.org.qldjvm.domain.node.Node;
import au.org.qldjvm.service.node.NodeService;

@Component
public class NodeHandlerConfigurer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeHandlerConfigurer.class);

    private ApplicationContext applicationContext;
    private NodeService nodeService;

    public void afterPropertiesSet() throws Exception {

        LOGGER.debug("NodeHandlerConfigurer");

        PathMatchingResourcePatternResolver pmrl = new PathMatchingResourcePatternResolver(
                applicationContext.getClassLoader());
        Resource[] resources = pmrl.getResources("classpath*:/au/org/qldjvm/additionalNodes/*/*NodeContext.xml");

        for (Resource resource : resources) {

            LOGGER.debug("Processing a resource " + resource.getURI());

            GenericApplicationContext newContext = new GenericApplicationContext(applicationContext);
            XmlBeanDefinitionReader beanReader = new XmlBeanDefinitionReader(newContext);
            int i = beanReader.loadBeanDefinitions(resource);

            Map nodeBeans = newContext.getBeansOfType(Node.class);
            for (Object nodeObject : nodeBeans.values()) {
                Node node = (Node) nodeObject;
                LOGGER.debug("adding a node with name " + node.getName());
                nodeService.addNode(node);
            }
        }
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Autowired
    public void setNodeService(NodeService nodeService) {
        this.nodeService = nodeService;
    }
}
