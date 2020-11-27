package com.example.componentesbasicos.classes;

import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.example.componentesbasicos.models.Geral;
import com.example.componentesbasicos.models.PrincipioAtivo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DataClient {
    private static DatabaseReference reference = null ;
    private static Geral geralService = null;

    public static DatabaseReference getClient(){
        if (reference == null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            reference = database.getReference();

            //atualiza as propriedades globais (ex. Principio ativo)
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    List<PrincipioAtivo> _TodosPrincipios = new ArrayList<PrincipioAtivo>();

                    if (snapshot.child("PA").exists()) {
                        for (DataSnapshot data : snapshot.child("PA").getChildren()) {
                            _TodosPrincipios.add(PrincipioAtivo.getFromDB(data));
                        }

                        getGeralService().setTodosPrincipios(_TodosPrincipios);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }
        return reference;
    }
    public static Geral getGeralService() {
        if (geralService == null){
            geralService = new Geral();
        }

        return geralService;
    }
}
