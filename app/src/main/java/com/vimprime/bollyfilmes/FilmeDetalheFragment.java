package com.vimprime.bollyfilmes;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.vimprime.bollyfilmes.data.FilmesContract;

public class FilmeDetalheFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private Uri filmeUri;
    private TextView tituloView, dataLancamentoView, descricaoView;
    private RatingBar avaliacaoView;
    private ImageView capaView, posterView;
    private static final int FILME_DETALHE_LOADER = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            filmeUri = (Uri) getArguments().getParcelable(MainActivity.FILME_DETALHE_URI);
        }

        getLoaderManager().initLoader(FILME_DETALHE_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_filme_detalhe, container, false);
        tituloView = view.findViewById(R.id.item_titulo);
        dataLancamentoView = view.findViewById(R.id.item_data);
        descricaoView = view.findViewById(R.id.item_desc);
        avaliacaoView = view.findViewById(R.id.item_avaliacao);
        capaView = (ImageView) view.findViewById(R.id.item_poster);
        posterView = (ImageView) view.findViewById(R.id.item_poster);

        return view;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        String[] projection = {
                FilmesContract.FilmeEntry._ID,
                FilmesContract.FilmeEntry.COLUMN_TITULO,
                FilmesContract.FilmeEntry.COLUMN_DESCRICAO,
                FilmesContract.FilmeEntry.COLUMN_POSTER_PATH,
                FilmesContract.FilmeEntry.COLUMN_CAPA_PATH,
                FilmesContract.FilmeEntry.COLUMN_AVALIACAO,
                FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO
        };
        return new CursorLoader(getContext(), filmeUri, projection, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

        if (data == null || data.getCount() < 1)
            return;

        if (data.moveToFirst()) {

            int tituloIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_TITULO);
            int descricaoIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DESCRICAO);
            int posterIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_POSTER_PATH);
            int capaIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_CAPA_PATH);
            int avaliacaoIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_AVALIACAO);
            int dataLancamentoIndex = data.getColumnIndex(FilmesContract.FilmeEntry.COLUMN_DATA_LANCAMENTO);

            String titulo = data.getString(tituloIndex);
            String descricao = data.getString(descricaoIndex);
            String poster = data.getString(posterIndex);
            String capa = data.getString(capaIndex);
            float avaliacao = data.getFloat(avaliacaoIndex);
            String dataLancamento = data.getString(dataLancamentoIndex);

            tituloView.setText(titulo);
            descricaoView.setText(descricao);
            avaliacaoView.setRating(avaliacao);
            dataLancamentoView.setText(dataLancamento);

            new DownloadImageTask(capaView).execute(capa);

            if (posterView != null)
                new DownloadImageTask(posterView).execute(poster);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
