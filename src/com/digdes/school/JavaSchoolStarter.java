package com.digdes.school;
import com.digdes.school.context.Context;

import java.util.List;
import java.util.Map;

public class JavaSchoolStarter {

    public JavaSchoolStarter(){

    }
    //На вход запрос, на выход результат выполнения запроса
    public List<Map<String,Object>> execute(String request) throws Exception {
        return Context.getController().execute(request);
    }

}
