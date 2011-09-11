package au.org.qldjvm.spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

public interface ApplicationContextFactory {

    public GenericApplicationContext buildApplicationContext(ApplicationContext parentContext);
}
