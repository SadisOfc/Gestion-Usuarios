package com.gestion.repository;

import com.gestion.models.Action;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ActionsList {
    Map<Integer, List<Action>> historial = new LinkedHashMap<>();
    public Map<Integer,List<Action>> getHistorial(){
        return historial;
    }
    public void createRegister(int id){
        historial.put(id,new ArrayList<>());
    }
    public void createRegister(int id, Action action){
        if(historial.containsKey(id)){
            List<Action> modifiedList = historial.get(id);
            modifiedList.add(action);
        }
    }

    public void printUser(int id){
        List<Action> list = historial.get(id);
        System.out.println("Id: " + id + " - Username: " + list.getFirst().getUser().getUsername() + " - Nombre Completo: " + list.getFirst().getUser().getFullname());
        list.forEach(System.out::println);
    }

    public void printAllUsers(){
        for(Map.Entry<Integer,List<Action>> map : historial.entrySet()){
            int id = map.getKey();
            printUser(id);
            System.out.println();
        }
    }
}
