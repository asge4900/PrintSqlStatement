package com.company;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class Helper {

    public static String getSqlString(Object object, String storedProcedure) {
        Field[] fields = object.getClass().getDeclaredFields();
        String campos = "";
        String valores = "";
        String parameters = "";

        for (int i = 0; i < fields.length; i++) {
            try {
                fields[i].setAccessible(true);
                String name = fields[i].getName();
                String value = fields[i].get(object).toString();
                if(i != 0) {
                    campos = campos + ",";
                    valores = valores + ",";
                    parameters += ", ";
                }
                if(fields[i].get(object) instanceof String){
                    valores = valores + "'" + value + "'";
                    parameters += "@" + name + " = " + "'" + value + "'";
                }
                else {
                    valores = valores + value;
                    parameters += "@" + name + " = " +  value;
                }
                fields[i].setAccessible(false);
                campos = campos + name;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }

        String sql = "insert into dbo." + object.getClass().getSimpleName().toLowerCase() + "s" + "(" + campos + ")values(" + valores + ");";
        String sp = "Exec " + storedProcedure + " " + parameters + ";";
        return  sp;
    }

    public static Map<String, Object> beanProperties(Object bean) {
        try {
            Map<String, Object> map = new HashMap<>();
            Arrays.asList(Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors())
                    .stream()
                    // filter out properties with setters only
                    .filter(pd -> Objects.nonNull(pd.getReadMethod()))
                    .forEach(pd -> { // invoke method to get value
                        try {
                            Object value = pd.getReadMethod().invoke(bean);
                            if (value != null) {
                                map.put(pd.getName(), value);
                            }
                        } catch (Exception e) {
                            // add proper error handling here
                        }
                    });
            return map;
        } catch (IntrospectionException e) {
            // and here, too
            return Collections.emptyMap();
        }
    }
}
