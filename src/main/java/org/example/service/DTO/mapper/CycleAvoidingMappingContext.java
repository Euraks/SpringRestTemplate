package org.example.service.DTO.mapper;

import java.util.IdentityHashMap;
import java.util.Map;

public class CycleAvoidingMappingContext {

    private Map<Object, Object> knownInstances = new IdentityHashMap<>();

    public <T> T getMappedInstance(Object source, Class<T> targetClass) {
        return targetClass.cast(knownInstances.get(source));
    }

    public void storeMappedInstance(Object source, Object target) {
        knownInstances.put(source, target);
    }
}
