package com.example.componentesbasicos.models;

import java.util.List;
import java.util.Observable;

public class Geral extends Observable {

    public List<PrincipioAtivo> TodosPrincipios;
    public List<Animais> TodosAnimais;

    public void setTodosPrincipios(List<PrincipioAtivo> todosPrincipios) {
        TodosPrincipios = todosPrincipios;
        setChanged();
        notifyObservers();
    }

    public void setTodosAnimais(List<Animais> todosAnimais){
        TodosAnimais = todosAnimais;
        setChanged();
        notifyObservers();
    }
}
