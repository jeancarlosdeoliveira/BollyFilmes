package com.vimprime.bollyfilmes;

import android.net.Uri;

import java.io.Serializable;

public class ItemFilme implements Serializable {

    private String id, titulo, descricao, dataLancamento;
    private Uri image;
    private float avaliacao;

    public ItemFilme(String titulo, String descricao, String dataLancamento, float avaliacao) {
        this.setTitulo(titulo);
        this.setDescricao(descricao);
        this.setDataLancamento(dataLancamento);
        this.setAvaliacao(avaliacao);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataLancamento() {
        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public Uri getImage() {
        return image;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }
}
