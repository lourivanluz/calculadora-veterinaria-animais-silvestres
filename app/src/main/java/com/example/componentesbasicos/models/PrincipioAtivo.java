package com.example.componentesbasicos.models;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

public class PrincipioAtivo {
    public String Proprietario;
    public String Nome;
    public List<NomesComerciais> Nomes_Comerciais;

    public PrincipioAtivo(){
        Nomes_Comerciais = new ArrayList<NomesComerciais>() ;
    }

    public static PrincipioAtivo getFromDB(DataSnapshot data){
        //cria o objeto que será inserido na lista estática
        PrincipioAtivo FromDataPA = new PrincipioAtivo();

        //preenche o proprietario
        FromDataPA.Proprietario = data.getKey();

        for(DataSnapshot item : data.getChildren()){
            FromDataPA.Nome = item.getKey();

            for(DataSnapshot subItem : item.child("NomesComerciais").getChildren()){
                FromDataPA.Nomes_Comerciais.add(NomesComerciais.getFromDB(subItem));
            }
        }
        return FromDataPA;
    }
}

