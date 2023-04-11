package com.digdes.school.repository;

import com.digdes.school.entity.Entity;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public interface UserRepository {
    List<Map<String, Object>> insert(Entity object) throws NoSuchFieldException, IllegalAccessException;

    void delete(Entity entity) throws IllegalAccessException;

    List<Map<String, Object>> updateValues(List<Entity> entities, Map<Field, Object> fieldUpdate, List<String> deleteField) throws IllegalAccessException, NoSuchFieldException;

    List<Map<String, Object>> getMapsEntities();

    List<Entity> getENTITIES();
    List<Map<String, Object>> getMapFromEntity(List<Entity> entities) throws IllegalAccessException;

    Map<String, Object> getMapFromEntity(Entity entity) throws IllegalAccessException;
}
