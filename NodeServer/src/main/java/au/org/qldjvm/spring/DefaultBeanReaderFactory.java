package au.org.qldjvm.spring;

import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class DefaultBeanReaderFactory implements BeanReaderFactory {

    public BeanDefinitionReader buildBeanReader(GenericApplicationContext applicationContext) {
        return new XmlBeanDefinitionReader(applicationContext);
    }
}
