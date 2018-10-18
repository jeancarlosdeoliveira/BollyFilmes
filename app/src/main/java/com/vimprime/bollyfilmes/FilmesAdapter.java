package com.vimprime.bollyfilmes;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vimprime.bollyfilmes.data.FilmesContract;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class FilmesAdapter extends CursorAdapter {

    private static final int VIEW_TYPE_DESTAQUE = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private boolean useFilmeDestaque = false;

    public FilmesAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    public static class ItemFilmeHolder {
        TextView titulo, desc, dataLancamento;
        RatingBar avaliacao;
        ImageView poster, capa;

        public ItemFilmeHolder (View view) {
            titulo = view.findViewById(R.id.item_titulo);
            desc = view.findViewById(R.id.item_desc);
            dataLancamento = view.findViewById(R.id.item_data);
            avaliacao = view.findViewById(R.id.item_avaliacao);
            poster = view.findViewById(R.id.item_poster);
            capa = view.findViewById(R.id.item_poster);
        }
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        int viewType = getItemViewType(cursor.getPosition());
        int layoutID = -1;

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                layoutID = R.layout.item_filme_destaque;
                break;
            }
            case VIEW_TYPE_ITEM: {
                layoutID = R.layout.item_filme;
                break;
            }
        }

        View view = LayoutInflater.from(context).inflate(layoutID, parent, false);

        ItemFilmeHolder holder = new ItemFilmeHolder(view);
        view.setTag(holder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ItemFilmeHolder holder = (ItemFilmeHolder) view.getTag();
        int viewType = getItemViewType(cursor.getPosition());

        int tituloIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_TITULO);
        int descricaoIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DESCRICAO);
        int posterIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_POSTER_PATH);
        int capaIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_CAPA_PATH);
        int avaliacaoIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_AVALIACAO);
        int dataLancamentoIndex = cursor.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO);

        switch (viewType) {
            case VIEW_TYPE_DESTAQUE: {
                holder.titulo.setText(cursor.getString(tituloIndex));
                holder.avaliacao.setRating(cursor.getFloat(avaliacaoIndex));
                new DownloadImageTask(holder.capa).execute(cursor.getString(capaIndex));
/*
                if (view.findViewById(R.id.item_poster) != null) {
                    ImageView poster = (ImageView) view.findViewById(R.id.item_poster);
                    new DownloadImageTask(holder.capa).execute(cursor.getString(capaIndex));
                }
*/
                break;
            }
            case VIEW_TYPE_ITEM: {
                holder.titulo.setText(cursor.getString(tituloIndex));
                holder.desc.setText(cursor.getString(descricaoIndex));
                holder.dataLancamento.setText(cursor.getString(dataLancamentoIndex));
                holder.avaliacao.setRating(cursor.getFloat(avaliacaoIndex));
                new DownloadImageTask(holder.poster).execute(cursor.getString(posterIndex));
                /*if (view.findViewById(R.id.item_poster) != null) {
                    ImageView poster = (ImageView) view.findViewById(R.id.item_poster);
                    new DownloadImageTask(holder.poster).execute(cursor.getString(posterIndex));
                }*/
                break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == 0 && useFilmeDestaque ? VIEW_TYPE_DESTAQUE : VIEW_TYPE_ITEM);
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;
    }
}
