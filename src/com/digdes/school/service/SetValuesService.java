package com.digdes.school.service;

import com.digdes.school.entity.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

public class SetValuesService {

    public static Entity setValuesFromRequest(String request, Entity entity) {
        StringTokenizer stringTokenizer = new StringTokenizer(request, ",='‘’ ");
        List<Field> fields = Arrays.asList(entity.getClass().getDeclaredFields());
        while (stringTokenizer.hasMoreTokens()) {
            String token = stringTokenizer.nextToken().toLowerCase(Locale.ROOT);
            fields.forEach(field -> {
                field.setAccessible(true);
                String nameField = field.getName().toLowerCase(Locale.ROOT);
                if (nameField.equals(token)) {
                    String typeField = field.getType().getSimpleName();
                    String methodName = "set" + nameField.substring(0, 1).toUpperCase() + nameField.substring(1);
                    Method method;
                    String value = stringTokenizer.nextToken();
                    try {
                        switch (typeField) {
                            case "Long", "long" -> {
                                method = entity.getClass().getMethod(methodName, Long.class);
                                method.invoke(entity, Long.parseLong(value));
                            }
                            case "int", "Integer" -> {
                                method = entity.getClass().getMethod(methodName, Integer.class);
                                method.invoke(entity, Integer.parseInt(value));
                            }
                            case "String" -> {
                                field.set(entity, value);
                            }
                            case "double", "Double" -> {
                                method = entity.getClass().getMethod(methodName, Double.class);
                                method.invoke(entity, Double.valueOf(value));
                            }
                            case "boolean", "Boolean" -> {
                                method = entity.getClass().getMethod(methodName, Boolean.class);
                                method.invoke(entity, Boolean.parseBoolean(value));
                            }
                        }
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return entity;
    }
}
