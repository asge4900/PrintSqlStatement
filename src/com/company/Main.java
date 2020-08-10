package com.company;

import com.company.domain.Person;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Map;

public class Main {

    public static void main(String[] args) {
        Person person = new Person();
        person.setFirstName("Jens");
        person.setLastName("Niels");
        person.setAge(30);


        String sql = "";

        Map<String, Object> maps = Helper.beanProperties(person);

        for (Map.Entry<String, Object> entry : maps.entrySet()) {
            if (entry != maps.entrySet().toArray()[0]) {
                System.out.println(",");
            }
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        maps.forEach((k, v) -> System.out.println(k + ": " + v));

//        try {
//            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//            String url = "jdbc:sqlserver://localhost:1433;Database=DummyDB;user=sa;password=ujglqlvkekyy;";
//            Connection con = DriverManager.getConnection(url);
//            sql = Helper.getSqlString(person, "dbo.crud_PersonsInsert");
//            PreparedStatement ps = con.prepareStatement(sql);
//            ps.executeUpdate();
//
//        } catch (Exception ex) {
//            System.out.println(ex.getMessage());
//        }
//
//        System.out.println(sql);
    }
}
