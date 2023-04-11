package com.digdes.school.service;

import com.digdes.school.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public interface Service {
    List<Map<String,Object>> insert(String request, Entity entity) throws Exception;

    List<Map<String,Object>> update(String request, Entity entity) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    List<Map<String,Object>> delete(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;

    List<Map<String,Object>> select(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException;
}
