package au.org.qldjvm.spring;

import junit.framework.TestCase;

import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import au.org.qldjvm.service.node.NodeService;

public class NodeHandlerConfigurerManualCreationTest {

    @Test
    public void testCanCreateApplicationContextWithDefaultConfiguration() {

        new ClassPathXmlApplicationContext("META-INF/spring/applicationContext.xml");
    }

    @Test
    public void testCreateNodeHandlerWithDefaultConfiguration() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "META-INF/spring/applicationContext.xml");

        NodeHandlerConfigurer nodeHandlerConfigurer = (NodeHandlerConfigurer) applicationContext
                .getBean("nodeHandlerConfigurer");

        TestCase.assertNotNull(nodeHandlerConfigurer);
    }

    @Test
    public void testGetNodeServiceDefaultConfiguration() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "META-INF/spring/applicationContext.xml");

        NodeService nodeService = (NodeService) applicationContext.getBean("nodeService");

        TestCase.assertNotNull(nodeService);
        TestCase.assertNotNull(nodeService.getNode("garageNode"));
    }

    @Test
    public void testGetTestNodeThrowsError() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "META-INF/spring/applicationContext.xml");

        // Child context is not visible to parent context!
        try {
            applicationContext.getBean("testNode");
        } catch (NoSuchBeanDefinitionException e) {
            return;
        }
        TestCase.fail("exception should have been thrown");
    }

    @Test
    public void testGetTestNodeFromNodeService() {

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
                "META-INF/spring/applicationContext.xml");

        NodeService nodeService = (NodeService) applicationContext.getBean("nodeService");

        TestCase.assertNotNull(nodeService.getNode("testNode"));
    }
}
