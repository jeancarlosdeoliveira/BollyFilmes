package com.vimprime.bollyfilmes;

/*
{
"adult":false,
"backdrop_path":"/87hTDiay2N2qWyX4Ds7ybXi9h8I.jpg",
"belongs_to_collection":null,"budget":63000000,
"genres":[{"id":18,"name":"Drama"}],
"homepage":"http://www.foxmovies.com/movies/fight-club",
"id":550,
"imdb_id":"tt0137523",
"original_language":"en",
"original_title":"Fight Club",
"overview":"A ticking-time-bomb insomniac and a slippery soap salesman channel primal male aggression into a shocking new form of therapy. Their concept catches on, with underground \"fight clubs\" forming in every town, until an eccentric gets in the way and ignites an out-of-control spiral toward oblivion.",
"popularity":31.515,
"poster_path":"/adw6Lq9FiC9zjYEpOqfq03ituwp.jpg",
"production_companies":[
{"id":508,"logo_path":"/7PzJdsLGlR7oW4J0J5Xcd0pHGRg.png","name":"Regency Enterprises","origin_country":"US"},
{"id":711,"logo_path":"/tEiIH5QesdheJmDAqQwvtN60727.png","name":"Fox 2000 Pictures","origin_country":"US"},
{"id":20555,"logo_path":null,"name":"Taurus Film","origin_country":""},{"id":54051,"logo_path":null,"name":"Atman Entertainment","origin_country":""},{"id":54052,"logo_path":null,"name":"Knickerbocker Films","origin_country":"US"},
{"id":25,"logo_path":"/qZCc1lty5FzX30aOCVRBLzaVmcp.png","name":"20th Century Fox","origin_country":"US"},
{"id":4700,"logo_path":"/A32wmjrs9Psf4zw0uaixF0GXfxq.png","name":"The Linson Company","origin_country":""}],
"production_countries":[
{"iso_3166_1":"DE","name":"Germany"},
{"iso_3166_1":"US","name":"United States of America"}],
"release_date":"1999-10-15",
"revenue":100853753,
"runtime":139,
"spoken_languages":[{"iso_639_1":"en","name":"English"}],
"status":"Released",
"tagline":"Mischief. Mayhem. Soap.",
"title":"Fight Club",
"video":false,
"vote_average":8.4,
"vote_count":13768
}
 */

import android.net.Uri;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ItemFilme implements Serializable {

    private long id;
    private String titulo, descricao, dataLancamento, posterPath, capaPath;
    private float avaliacao, popularidade;

    public ItemFilme(long id, String titulo, String descricao, String dataLancamento,
                     String posterPath, String capaPath, float avaliacao, float popularidade) {
        this.setId(id);
        this.setTitulo(titulo);
        this.setDescricao(descricao);
        this.setDataLancamento(dataLancamento);
        this.setPosterPath(posterPath);
        this.setCapaPath(capaPath);
        this.setAvaliacao(avaliacao);
        this.setPopularidade(popularidade);
    }

    public ItemFilme(JSONObject jsonObject) throws JSONException {
        this.setId(jsonObject.getLong("id"));
        this.setTitulo(jsonObject.getString("title"));
        this.setDescricao(jsonObject.getString("overview"));
        this.setDataLancamento(jsonObject.getString("release_date"));
        this.setPosterPath(jsonObject.getString("poster_path"));
        this.setCapaPath(jsonObject.getString("backdrop_path"));
        this.setAvaliacao((float) jsonObject.getDouble("vote_average"));
        this.setPopularidade((float) jsonObject.getDouble("popularity"));
    }

    private String buildPath(String width, String path) {
        StringBuilder builder = new StringBuilder();
        builder.append("http://image.tmdb.org/t/p/")
                .append(width)
                .append(path);

        String pathBuilder = builder.toString();
        return pathBuilder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

        Locale locale = new Locale("pt", "BR");

        try {
            Date date = new SimpleDateFormat("yyyy-MM-dd", locale).parse(dataLancamento);
            return new SimpleDateFormat("dd/MM/yyyy", locale).format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dataLancamento;
    }

    public void setDataLancamento(String dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public String getPosterPath() {
        return buildPath("w500", this.posterPath);
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getCapaPath() {
        return buildPath("w780", this.capaPath);
    }

    public void setCapaPath(String capaPath) {
        this.capaPath = capaPath;
    }

    public float getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(float avaliacao) {
        this.avaliacao = avaliacao;
    }

    public float getPopularidade() {
        return this.popularidade;
    }

    public void setPopularidade(float popularidade) {
        this.popularidade = popularidade;
    }


}
