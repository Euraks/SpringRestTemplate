package org.example.service.DTO;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class SimpleEntityDTOTest {

    @Test
    public void testGetUuid() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        UUID uuid = UUID.randomUUID();
        simpleEntityDTO.setUuid( uuid );
        assertEquals( uuid, simpleEntityDTO.getUuid() );
    }

    @Test
    public void testGetDescription() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        String description = "Sample description";
        simpleEntityDTO.setDescription( description );
        assertEquals( description, simpleEntityDTO.getDescription() );
    }

    @Test
    public void testGetUuidWhenNotSet() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        assertNull( simpleEntityDTO.getUuid() );
    }

    @Test
    public void testGetDescriptionWhenNotSet() {
        SimpleEntityDTO simpleEntityDTO = new SimpleEntityDTO();
        assertNull( simpleEntityDTO.getDescription() );
    }
}
