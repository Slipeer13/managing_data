package com.digdes.school.repository;

import com.digdes.school.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository{
    private static final List<Map<String,Object>> MAPS_ENTITIES = new ArrayList<>();
    private static final List<Entity> ENTITIES = new ArrayList<>();

    @Override
    public List<Map<String, Object>> insert(Entity entity) throws NoSuchFieldException, IllegalAccessException {
        List<Map<String, Object>> result = new ArrayList<>();
        if(entity.hasNonNullFields()) {
            Map<String, Object> map = getMapFromEntity(entity);
            result.add(map);
            MAPS_ENTITIES.add(map);
            ENTITIES.add(entity);

        }  else {
        throw new NoSuchFieldException("all fields in the request are empty");
        }
        return result;
    }

    @Override
    public void delete(Entity entity) throws IllegalAccessException {
        ENTITIES.remove(entity);
        MAPS_ENTITIES.remove(getMapFromEntity(entity));
    }

    @Override
    public List<Map<String, Object>> updateValues(List<Entity> entities,
                                                  Map<Field, Object> fieldUpdate, List<String> deleteFields) throws IllegalAccessException, NoSuchFieldException {
        List<Map<String, Object>> sourceListMap = getMapFromEntity(entities);
        for (Entity entity:
             entities) {
            for (Map.Entry<Field, Object> mapFields :
                    fieldUpdate.entrySet()) {
                Field field = mapFields.getKey();
                String nameField = field.getName();
                String typeField = field.getType().getSimpleName();
                String methodName = "set" + nameField.substring(0, 1).toUpperCase() + nameField.substring(1);
                Method method;
                try {
                    switch (typeField) {
                        case "Long", "long" -> {
                            method = entity.getClass().getMethod(methodName, Long.class);
                            method.invoke(entity, Long.valueOf(mapFields.getValue().toString()));
                        }
                        case "String" -> {
                            method = entity.getClass().getMethod(methodName, String.class);
                            method.invoke(entity, mapFields.getValue().toString());
                        }
                        case "double", "Double" -> {
                            method = entity.getClass().getMethod(methodName, Double.class);
                            method.invoke(entity, Double.valueOf(mapFields.getValue().toString()));
                        }
                        case "boolean", "Boolean" -> {
                            method = entity.getClass().getMethod(methodName, Boolean.class);
                            method.invoke(entity, Boolean.valueOf(mapFields.getValue().toString()));
                        }
                    }
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            for (String deleteField :
                    deleteFields) {
                Field field = entity.getClass().getDeclaredField(deleteField);
                if (field.getName().equals(deleteField)) {
                    field.setAccessible(true);
                    field.set(entity, null);
                }

            }
        }
        List<Map<String, Object>> result = new ArrayList<>();
        List<Map<String, Object>> updateListMap = getMapFromEntity(entities);
        for (int i = 0; i < entities.size(); i++) {
            Map<String, Object> map = sourceListMap.get(i);
            if (MAPS_ENTITIES.contains(map)) {
                int index = MAPS_ENTITIES.indexOf(map);
                MAPS_ENTITIES.set(index, updateListMap.get(i));
            }
        }
        return result;

    }

    @Override
    public List<Map<String, Object>> getMapsEntities() {
        return MAPS_ENTITIES;
    }

    @Override
    public List<Entity> getENTITIES() {
        return ENTITIES;
    }

    @Override
    public Map<String, Object> getMapFromEntity(Entity entity) throws IllegalAccessException {
        Map<String, Object> row = new HashMap<>();
        Field[] fields = entity.getClass().getDeclaredFields();
        for (Field field :
                fields) {
            field.setAccessible(true);
            Object object = field.get(entity);
            if(object != null) {
                row.put(field.getName(), field.get(entity));
            }
        }
        return row;
    }

    @Override
    public List<Map<String, Object>> getMapFromEntity(List<Entity> entities) throws IllegalAccessException {
        List<Map<String, Object>> result = new ArrayList<>();
        for (Entity entity:
                entities) {
            Map<String, Object> row = getMapFromEntity(entity);
            result.add(row);
        }
        return result;
    }

}
