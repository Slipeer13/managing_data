package com.digdes.school.service;

import com.digdes.school.entity.Entity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

public class ServiceImpl implements Service {


    @Override
    public List<Map<String, Object>> insert(String request, Entity entity) throws Exception {
        return InsertService.insert(request, entity);
    }

    @Override
    public List<Map<String, Object>> update(String request, Entity entity) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return UpdateService.update(request, entity);
    }

    @Override
    public List<Map<String, Object>> delete(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return DeleteService.delete(request);
    }

    @Override
    public List<Map<String, Object>> select(String request) throws NoSuchFieldException, InvocationTargetException, NoSuchMethodException, IllegalAccessException {
        return SelectService.select(request);
    }
}
