package com.example.gerenpessoas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PessoaAdapter extends RecyclerView.Adapter<PessoaAdapter.PessoaViewHolder> {
    private List<Pessoa> pessoas;
    private OnPessoaClickListener listener;

    public interface OnPessoaClickListener {
        void onPessoaClick(int position);
    }

    public PessoaAdapter(List<Pessoa> pessoas, OnPessoaClickListener listener) {
        this.pessoas = pessoas;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PessoaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(android.R.layout.simple_list_item_1, parent, false);
        return new PessoaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PessoaViewHolder holder, int position) {
        Pessoa pessoa = pessoas.get(position);
        holder.tvNome.setText(pessoa.getNome() + " (" + pessoa.getIdade() + " anos)");
    }

    @Override
    public int getItemCount() {
        return pessoas.size();
    }

    class PessoaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvNome;

        public PessoaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNome = itemView.findViewById(android.R.id.text1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onPessoaClick(position);
            }
        }
    }
}