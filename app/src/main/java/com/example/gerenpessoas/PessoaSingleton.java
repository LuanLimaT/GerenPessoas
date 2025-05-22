package com.example.gerenpessoas;

import java.util.ArrayList;
import java.util.List;


    //modificar a mesma lista de pessoas sem
    // precisar passar dados entre atividades via intents
public class PessoaSingleton {
    private static PessoaSingleton instance;
    private List<Pessoa> pessoas;

    private PessoaSingleton() {
        pessoas = new ArrayList<>();
    }

    public static synchronized PessoaSingleton getInstance() {
        if (instance == null) {
            instance = new PessoaSingleton();
        }
        return instance;
    }

    public void adicionarPessoa(Pessoa pessoa) {
        pessoas.add(pessoa);
    }

    public void removerPessoa(Pessoa pessoa) {
        pessoas.remove(pessoa);
    }

    public void atualizarPessoa(int position, Pessoa pessoa) {
        pessoas.set(position, pessoa);
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }

    public int getPositionByNome(String nome) {
        for (int i = 0; i < pessoas.size(); i++) {
            if (pessoas.get(i).getNome().equals(nome)) {
                return i;
            }
        }
        return -1;
    }

    public boolean nomeExiste(String nome) {
        return getPositionByNome(nome) != -1;
    }
}