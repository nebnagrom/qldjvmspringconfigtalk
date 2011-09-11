package au.org.qldjvm.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultApplicationContextFactory implements ApplicationContextFactory {

    public GenericApplicationContext buildApplicationContext(ApplicationContext parentContext) {
        return new GenericApplicationContext(parentContext);
    }

}
