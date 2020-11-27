package com.example.componentesbasicos.classes;

import android.os.Build;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.componentesbasicos.models.Animais;
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

public class ServiceProvider {
    private static DatabaseReference reference = null ;
    private static Geral geralService = null;

    public static DatabaseReference getDataBaseService(){
        if (reference == null){
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            reference = database.getReference();

            //atualiza as propriedades globais (ex. Principio ativo)
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    List<PrincipioAtivo> _TodosPrincipios = new ArrayList<PrincipioAtivo>();
                    List<Animais> _TodosAnimais = new ArrayList<Animais>();

                    //atualiza principios ativos
                    if (snapshot.child("PA").exists()) {
                        for (DataSnapshot data : snapshot.child("PA").getChildren()) {
                            _TodosPrincipios.add(PrincipioAtivo.getFromDB(data));
                        }

                        getGeralService().setTodosPrincipios(_TodosPrincipios);
                    }

                    //atualiza animais
                    if (snapshot.child("tipo-animal").exists()) {
                        for (DataSnapshot data : snapshot.child("tipo-animal").getChildren()) {
                            _TodosAnimais.addAll(Animais.getFromDB(data));
                        }

                        getGeralService().setTodosAnimais(_TodosAnimais);
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



    //        List<PrincipioAtivo> meusprincipiosAtivos = DataClient.TodosPrincipios
//                .stream()
//                .filter(c -> c.Proprietario == "Nome Do Usuario aqui com tratativa de pontos e arrobas ===> onde : 'lourivan@webmat.com.br' , vira 'lourivan webmatcombr'")
//                .collect(Collectors.toList());
}
