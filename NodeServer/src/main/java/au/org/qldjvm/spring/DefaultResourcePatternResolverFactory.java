package au.org.qldjvm.spring;

import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class DefaultResourcePatternResolverFactory implements ResourcePatternResolverFactory {

    public ResourcePatternResolver buildResourcePatternResolver(ClassLoader classLoader) {
        return new PathMatchingResourcePatternResolver(classLoader);
    }
}
