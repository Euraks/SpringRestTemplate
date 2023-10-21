package org.example.service.DTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SimpleEntityDTOTest {

    @Test
    void testGetUuid() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        UUID uuid = UUID.randomUUID();
        simpleEntityDTO.setUuid( uuid );
        assertEquals( uuid, simpleEntityDTO.getUuid() );
    }

    @Test
    void testGetDescription() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        String description = "Sample description";
        simpleEntityDTO.setDescription( description );
        assertEquals( description, simpleEntityDTO.getDescription() );
    }

    @Test
    void testGetUuidWhenNotSet() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        assertNull( simpleEntityDTO.getUuid() );
    }

    @Test
    void testGetDescriptionWhenNotSet() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        assertNull( simpleEntityDTO.getDescription() );
    }
}
