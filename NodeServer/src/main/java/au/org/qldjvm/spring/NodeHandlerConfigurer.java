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

@Component("nodeHandlerConfigurer")
public class NodeHandlerConfigurer implements ApplicationContextAware, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(NodeHandlerConfigurer.class);

    private ApplicationContext applicationContext;
    private NodeService nodeService;

    private ApplicationContextFactory applicationContextFactory;
    private ResourcePatternResolverFactory resourcePatternResolverFactory;
    private BeanReaderFactory beanReaderFactory;

    public void afterPropertiesSet() throws Exception {

        LOGGER.debug("NodeHandlerConfigurer");

        ResourcePatternResolver pmrl = resourcePatternResolverFactory.buildResourcePatternResolver(applicationContext
                .getClassLoader());
        Resource[] resources = pmrl.getResources("classpath*:/au/org/qldjvm/additionalNodes/*/*NodeContext.xml");

        for (Resource resource : resources) {

            LOGGER.debug("Processing a resource " + resource.getURI());

            GenericApplicationContext newContext = applicationContextFactory
                    .buildApplicationContext(applicationContext);
            BeanDefinitionReader beanReader = beanReaderFactory.buildBeanReader(newContext);
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
