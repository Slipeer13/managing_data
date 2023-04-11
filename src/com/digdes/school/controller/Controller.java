package com.digdes.school.controller;

import java.util.List;
import java.util.Map;

public interface Controller {
    List<Map<String,Object>> execute(String request)throws Exception;
}
