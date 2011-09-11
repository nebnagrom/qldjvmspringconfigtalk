package au.org.qldjvm.spring;

import org.springframework.core.io.support.ResourcePatternResolver;

public interface ResourcePatternResolverFactory {

    public ResourcePatternResolver buildResourcePatternResolver(ClassLoader classLoader);
}
