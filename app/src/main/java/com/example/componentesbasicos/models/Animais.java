package com.example.componentesbasicos.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Animais {
    public String Proprietario;
    public String Tipo;
    public String Nome;
    public int Grupo;
    public int K;
    public PrincipioAtivoAnimal PA_Animal;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static List<Animais> getFromDB(DataSnapshot data){
        List<Animais> listRetorno = new ArrayList<Animais>();

        for(DataSnapshot item : data.child("Animais").getChildren()) {

            for(DataSnapshot subItem : item.getChildren()){
                Animais ani = new Animais();

                ani.Proprietario = item.getKey();
                ani.Tipo = data.getKey();
                ani.K = data.child("K").getValue(Integer.class);

                ani.Nome = subItem.getKey();
                ani.Grupo = subItem.child("Grupo").getValue(Integer.class);

                ani.PA_Animal = PrincipioAtivoAnimal.getFromDB(subItem.child("PrincipioAtivo"));

                listRetorno.add(ani);
            }

        }

        return listRetorno;
    }
}
