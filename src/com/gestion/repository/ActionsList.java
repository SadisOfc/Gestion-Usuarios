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
    public void crearRegistro(int id){
        historial.put(id,new ArrayList<>());
    }
    public void crearRegistro(int id, Action action){
        if(historial.containsKey(id)){
            List<Action> listaModificada = historial.get(id);
            listaModificada.add(action);
        }
    }
    public List<Action> listaUsuario(int id){
        if(!historial.isEmpty()) return historial.get(id);
        return null;
    }
    public void imprimirUsuario(int id){
        List<Action> lista = historial.get(id);
        System.out.println("Id: " + id);
        lista.forEach(System.out::println);
    }

    public void imprimitTodos(){
        for(Map.Entry<Integer,List<Action>> map : historial.entrySet()){
            int id = map.getKey();
            imprimirUsuario(id);
            System.out.println();
        }
    }
}
