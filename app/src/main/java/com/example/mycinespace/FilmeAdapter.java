package com.example.mycinespace;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilmeAdapter extends RecyclerView.Adapter<FilmeAdapter.FilmeViewHolder> {

    public interface OnFilmeClickListener {
        void onFilmeClick(Filme filme);
    }

    private final List<Filme> todos;
    private final List<Filme> visiveis;
    private final OnFilmeClickListener listener;

    public FilmeAdapter(List<Filme> filmes, OnFilmeClickListener listener) {
        this.todos = new ArrayList<>(filmes);
        this.visiveis = new ArrayList<>(filmes);
        this.listener = listener;
    }

    @NonNull
    @Override
    public FilmeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filme, parent, false);
        return new FilmeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilmeViewHolder holder, int position) {
        final Filme filme = visiveis.get(position);

        holder.imgPoster.setImageResource(filme.getPoster());
        holder.imgPoster.setContentDescription(filme.getTitulo());
        holder.tvTitulo.setText(filme.getTitulo());
        holder.tvInfo.setText(filme.getInfo());

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onFilmeClick(filme);
            }
        });
    }
    @Override
    public int getItemCount() {
        return visiveis.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filtrar(String texto) {
        String busca = normalizar(texto);
        visiveis.clear();

        if (busca.isEmpty()) {
            visiveis.addAll(todos);
        } else {
            for (Filme filme : todos) {
                if (normalizar(filme.getTitulo()).contains(busca)) {
                    visiveis.add(filme);
                }
            }
        }
        notifyDataSetChanged();
    }

    public boolean estaVazio() {
        return visiveis.isEmpty();
    }

    private static String normalizar(String texto) {
        String decomposto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        StringBuilder resultado = new StringBuilder();
        for (int i = 0; i < decomposto.length(); i++) {
            char c = decomposto.charAt(i);
            if (Character.getType(c) != Character.NON_SPACING_MARK) {
                resultado.append(c);
            }
        }
        return resultado.toString().toLowerCase(Locale.ROOT).trim();
    }

    public static class FilmeViewHolder extends RecyclerView.ViewHolder {
        final ImageView imgPoster;
        final TextView tvTitulo;
        final TextView tvInfo;

        FilmeViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgPoster);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvInfo = itemView.findViewById(R.id.tvInfo);
        }
    }
}