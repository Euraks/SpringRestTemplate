package org.example.model.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SimpleEntityTest {

    private SimpleEntity simpleEntity;
    private final UUID uuid = UUID.randomUUID();
    private final String description = "Sample Description";

    @BeforeEach
    public void setUp() {
        simpleEntity = new SimpleEntity();
        simpleEntity.setUuid(uuid);
        simpleEntity.setDescription(description);
    }

    @Test
    void testSimpleEntityConstructor() {
        String expectedDescription = "Test Description";

        SimpleEntity simpleEntity = new SimpleEntity(expectedDescription);

        assertEquals(expectedDescription, simpleEntity.getDescription());
    }

    @Test
    void gettersAndSettersWorkCorrectly() {
        assertThat(simpleEntity.getUuid()).isEqualTo(uuid);
        assertThat(simpleEntity.getDescription()).isEqualTo(description);
    }

    @Test
    void equalsAndHashCodeWorkCorrectly() {
        SimpleEntity anotherEntity = new SimpleEntity();
        anotherEntity.setUuid(uuid);
        anotherEntity.setDescription(description);

        assertThat(simpleEntity).isEqualTo(anotherEntity);
        assertThat(simpleEntity.hashCode()).isEqualTo(anotherEntity.hashCode());

        anotherEntity.setDescription("Different Description");
        assertThat(simpleEntity).isNotEqualTo(anotherEntity);
    }

    @Test
    void toStringReturnsCorrectString() {
        String expectedString = "SimpleEntity{id=" + uuid + ", description='" + description + "'}";
        assertThat(simpleEntity.toString()).isEqualTo(expectedString);
    }
}
