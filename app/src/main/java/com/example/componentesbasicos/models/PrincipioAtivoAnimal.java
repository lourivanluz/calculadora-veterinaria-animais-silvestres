package com.example.componentesbasicos.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.componentesbasicos.classes.ServiceProvider;
import com.google.firebase.database.DataSnapshot;

public class PrincipioAtivoAnimal {
    public PrincipioAtivo PrincipioAtivo;
    public int Frequencia;
    public int Posologia;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static PrincipioAtivoAnimal getFromDB(DataSnapshot data){
        PrincipioAtivoAnimal paa = new PrincipioAtivoAnimal();
        for(DataSnapshot item : data.getChildren()){
            paa.Frequencia = item.child("Frequencia").getValue(Integer.class);
            paa.Posologia = item.child("Posologia").getValue(Integer.class);
            paa.PrincipioAtivo = ServiceProvider.getGeralService().TodosPrincipios.stream().filter(q -> q.Nome.equals(item.getKey())).findFirst().orElse(null);
        }

        return paa;
    }
}
