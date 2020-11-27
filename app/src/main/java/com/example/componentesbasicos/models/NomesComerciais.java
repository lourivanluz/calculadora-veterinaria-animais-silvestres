package com.example.componentesbasicos.models;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class NomesComerciais {
    public String Nome;
    public List<Integer> Concentracoes ;

    public NomesComerciais(){
        Concentracoes = new ArrayList<Integer>();
    }

    public static NomesComerciais getFromDB(DataSnapshot subItem){
        NomesComerciais nc = new NomesComerciais();

        nc.Nome = subItem.getKey();

        for(DataSnapshot cc : subItem.child("Concentracoes").getChildren()){
            nc.Concentracoes.add(cc.getValue(Integer.class));
        }

        return nc;
    }
}
