package org.example.config;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MySpringMvcDispatcherServletInitializerTest {

    private final MySpringMvcDispatcherSerlvetIntitializer initializer = new MySpringMvcDispatcherSerlvetIntitializer();

    @Test
    void getRootConfigClassesShouldReturnNull() {
        assertThat( initializer.getRootConfigClasses() ).isNull();
    }

    @Test
    void getServletConfigClassesShouldReturnAppConfig() {
        Class<?>[] configClasses = initializer.getServletConfigClasses();
        assertThat( configClasses ).containsExactly( AppConfig.class );
    }

    @Test
    void getServletMappingsShouldReturnRootPath() {
        String[] mappings = initializer.getServletMappings();
        assertThat( mappings ).containsExactly( "/" );
    }
}
