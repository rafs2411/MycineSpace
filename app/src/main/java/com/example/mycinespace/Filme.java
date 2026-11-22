package com.example.mycinespace;

import java.io.Serializable;

public class Filme implements Serializable {

    private final String titulo;
    private final int ano;
    private final String generos;
    private final String classificacao;
    private final int poster;
    private final String sinopse;
    private final String direcao;
    private final String roteiristas;
    private final String estrelas;
    private final String trailerId;

    public Filme(String titulo, int ano, String generos, String classificacao, int poster,
                 String sinopse, String direcao, String roteiristas, String estrelas,
                 String trailerId) {
        this.titulo = titulo;
        this.ano = ano;
        this.generos = generos;
        this.classificacao = classificacao;
        this.poster = poster;
        this.sinopse = sinopse;
        this.direcao = direcao;
        this.roteiristas = roteiristas;
        this.estrelas = estrelas;
        this.trailerId = trailerId;
    }

    public String getTitulo() {
        return titulo;
    }

    public int getPoster() {
        return poster;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getDirecao() {
        return direcao;
    }

    public String getRoteiristas() {
        return roteiristas;
    }

    public String getEstrelas() {
        return estrelas;
    }

    public String getTrailerId() {
        return trailerId;
    }

    public String getInfo() {
        return ano + " \u00B7 " + generos + " \u00B7 " + classificacao;
    }
}