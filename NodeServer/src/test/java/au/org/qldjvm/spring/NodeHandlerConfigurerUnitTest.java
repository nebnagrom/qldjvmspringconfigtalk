package au.org.qldjvm.spring;

import java.net.URI;
import java.util.HashMap;

import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import au.org.qldjvm.domain.node.DefaultNode;
import au.org.qldjvm.domain.node.Node;
import au.org.qldjvm.service.node.NodeService;
import au.org.qldjvm.spring.ApplicationContextFactory;
import au.org.qldjvm.spring.BeanReaderFactory;
import au.org.qldjvm.spring.NodeHandlerConfigurer;
import au.org.qldjvm.spring.ResourcePatternResolverFactory;

public class NodeHandlerConfigurerUnitTest {

    private NodeHandlerConfigurer nodeHandlerConfigurer;

    private ApplicationContext applicationContext;
    private NodeService nodeService;

    private ResourcePatternResolver testResourcePatternResolver;
    private BeanDefinitionReader testBeanDefinitionReader;
    private GenericApplicationContext testChildContext;

    private IMocksControl mocksControl;

    @Before
    public void setup() {

        nodeHandlerConfigurer = new NodeHandlerConfigurer();

        mocksControl = EasyMock.createControl();

        applicationContext = mocksControl.createMock(ApplicationContext.class);
        nodeService = mocksControl.createMock(NodeService.class);

        testResourcePatternResolver = mocksControl.createMock(ResourcePatternResolver.class);
        testBeanDefinitionReader = mocksControl.createMock(BeanDefinitionReader.class);
        testChildContext = mocksControl.createMock(GenericApplicationContext.class);

        nodeHandlerConfigurer.setNodeService(nodeService);
        nodeHandlerConfigurer.setApplicationContext(applicationContext);
        nodeHandlerConfigurer.setResourcePatternResolverFactory(new TestResourcePatternResolverFactory());
        nodeHandlerConfigurer.setBeanReaderFactory(new TestBeanReaderFactory());
        nodeHandlerConfigurer.setApplicationContextFactory(new TestApplicationContextFactory());
    }

    @Test
    public void testLoadNoResources() throws Exception {

        EasyMock.expect(applicationContext.getClassLoader()).andReturn(getClass().getClassLoader());

        EasyMock.expect(
                testResourcePatternResolver
                        .getResources("classpath*:/au/org/qldjvm/additionalNodes/*/*NodeContext.xml")).andReturn(
                new Resource[] {});
        mocksControl.replay();

        nodeHandlerConfigurer.afterPropertiesSet();

        mocksControl.verify();
    }

    @Test
    public void testLoadSingleResourceNoNodes() throws Exception {

        EasyMock.expect(applicationContext.getClassLoader()).andReturn(getClass().getClassLoader());

        Resource resource = mocksControl.createMock(Resource.class);
        EasyMock.expect(resource.getURI()).andReturn(new URI("testuri"));

        EasyMock.expect(
                testResourcePatternResolver
                        .getResources("classpath*:/au/org/qldjvm/additionalNodes/*/*NodeContext.xml")).andReturn(
                new Resource[] { resource });

        EasyMock.expect(testBeanDefinitionReader.loadBeanDefinitions(resource)).andReturn(1);

        EasyMock.expect(testChildContext.getBeansOfType(Node.class)).andReturn(new HashMap<String, Node>());

        mocksControl.replay();

        nodeHandlerConfigurer.afterPropertiesSet();

        mocksControl.verify();
    }

    @Test
    public void testLoadSingleResourceSingleExtraNode() throws Exception {

        EasyMock.expect(applicationContext.getClassLoader()).andReturn(getClass().getClassLoader());

        Resource resource = mocksControl.createMock(Resource.class);
        EasyMock.expect(resource.getURI()).andReturn(new URI("testuri"));

        EasyMock.expect(
                testResourcePatternResolver
                        .getResources("classpath*:/au/org/qldjvm/additionalNodes/*/*NodeContext.xml")).andReturn(
                new Resource[] { resource });

        EasyMock.expect(testBeanDefinitionReader.loadBeanDefinitions(resource)).andReturn(1);

        HashMap<String, Node> beanNameToNode = new HashMap<String, Node>();
        String nodeName = "newNode";
        DefaultNode node = new DefaultNode(nodeName);
        beanNameToNode.put(nodeName, node);
        EasyMock.expect(testChildContext.getBeansOfType(Node.class)).andReturn(beanNameToNode);

        nodeService.addNode(node);

        mocksControl.replay();

        nodeHandlerConfigurer.afterPropertiesSet();

        mocksControl.verify();
    }

    private class TestResourcePatternResolverFactory implements ResourcePatternResolverFactory {

        public ResourcePatternResolver buildResourcePatternResolver(ClassLoader classLoader) {
            return testResourcePatternResolver;
        }
    }

    private class TestBeanReaderFactory implements BeanReaderFactory {

        public BeanDefinitionReader buildBeanReader(GenericApplicationContext applicationContext) {
            return testBeanDefinitionReader;
        }
    }

    private class TestApplicationContextFactory implements ApplicationContextFactory {

        public GenericApplicationContext buildApplicationContext(ApplicationContext parentContext) {
            return testChildContext;
        }
    }
}
