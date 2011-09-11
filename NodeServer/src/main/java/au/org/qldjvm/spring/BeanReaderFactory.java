package au.org.qldjvm.spring;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;

public interface BeanReaderFactory {

    public BeanDefinitionReader buildBeanReader(GenericApplicationContext applicationContext);
}
