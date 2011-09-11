package au.org.qldjvm.spring;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

// need to say classpath* otherwise you get some prefixes based on the loader
@ContextConfiguration(locations = { "classpath*:META-INF/spring/applicationContext.xml" })
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
@RunWith(SpringJUnit4ClassRunner.class)
public class NodeHandlerConfigurerSpringWiredTest implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Test
    public void testApplicationContextIsInitialised() {

        NodeHandlerConfigurer nodeHandlerConfigurer = (NodeHandlerConfigurer) applicationContext
                .getBean("nodeHandlerConfigurer");

        TestCase.assertNotNull(nodeHandlerConfigurer);
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}
