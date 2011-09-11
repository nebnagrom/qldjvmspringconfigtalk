package au.org.qldjvm.spring;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import au.org.qldjvm.domain.node.Node;
import au.org.qldjvm.service.node.NodeService;

/**
 * Configures the NodeService with additional Nodes and Handlers based on what
 * is found on the classpath.
 * 
 * Implements ApplicationContextAware which means that an ApplicationContext
 * will be wired in by Spring. Implements InitializingBean which means that
 * Spring will call afterPropertiesSet once the bean has been initialised.
 * 
 * @author Benjamin Morgan
 * 
 */
@Component("nodeHandlerConfigurer")
public class NodeHandlerConfigurer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeHandlerConfigurer.class);

    private ApplicationContext applicationContext;
    private NodeService nodeService;

    private ApplicationContextFactory applicationContextFactory;
    private ResourcePatternResolverFactory resourcePatternResolverFactory;
    private BeanReaderFactory beanReaderFactory;

    public void afterPropertiesSet() throws Exception {

        LOGGER.debug("NodeHandlerConfigurer is running afterPropertiesSet");

        /**
         * The constructors are hidden behind interfaces to make testing easier
         * but this block will work with
         * "ResourcePatternResolver pmrl = new PathMatchingResourcePatternResolver(classLoader);"
         * "GenericApplicationContext newContext = new GenericApplicationContext(parentContext);"
         * "BeanDefinitionReader beanReader = new XmlBeanDefinitionReader(applicationContext);"
         */

        // Find the resources that are in the classpath under
        // au/org/qldjvm/additionalNodes/*/ that end with NodeContext.xml
        ResourcePatternResolver pmrl = resourcePatternResolverFactory.buildResourcePatternResolver(applicationContext
                .getClassLoader());
        Resource[] resources = pmrl.getResources("classpath*:/au/org/qldjvm/additionalNodes/*/*NodeContext.xml");

        for (Resource resource : resources) {

            LOGGER.debug("Processing a resource " + resource.getURI());

            // Build a new application context that a child of the main context
            GenericApplicationContext newContext = applicationContextFactory
                    .buildApplicationContext(applicationContext);
            // Read the beans into the new context using the resource
            BeanDefinitionReader beanReader = beanReaderFactory.buildBeanReader(newContext);
            int i = beanReader.loadBeanDefinitions(resource);

            LOGGER.debug("just loaded " + i + " beans from resource");

            // Find all beans that are Nodes
            Map nodeBeans = newContext.getBeansOfType(Node.class);
            LOGGER.debug("found this many beans of node type " + nodeBeans.size());
            for (Object nodeObject : nodeBeans.values()) {
                Node node = (Node) nodeObject;
                LOGGER.debug("adding a node with name " + node.getName());
                // Register a Node with the NodeService
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

    @Autowired
    public void setResourcePatternResolverFactory(ResourcePatternResolverFactory resourcePatternResolverFactory) {
        this.resourcePatternResolverFactory = resourcePatternResolverFactory;
    }

    @Autowired
    public void setBeanReaderFactory(BeanReaderFactory beanReaderFactory) {
        this.beanReaderFactory = beanReaderFactory;
    }

    @Autowired
    public void setApplicationContextFactory(ApplicationContextFactory applicationContextFactory) {
        this.applicationContextFactory = applicationContextFactory;
    }
}
