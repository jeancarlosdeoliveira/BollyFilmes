package com.vimprime.bollyfilmes;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    private int posicaoItem = ListView.INVALID_POSITION;
    private static final String KEY_POSICAO = "SELECIONADO";
    private ListView list;
    private boolean useFilmeDestaque = false;
    FilmesAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        // executado depois de ter criado a classe do filme, o layout individual e o adapter
        list = view.findViewById(R.id.list_filmes);
        final ArrayList<ItemFilme> arrayList = new ArrayList<>();

        arrayList.add(new ItemFilme("Homem Aranha", "Filme de herói picado por" +
                "uma aranha", "10/04/2016", 4));

        arrayList.add(new ItemFilme("A Bela e a Fera", "Moradora de uma pequena " +
                "aldeia francesa, Bela (Emma Watson) tem o pai capturado pela Fera (Dan Stevens) " +
                "e decide entregar sua vida ao estranho ser em troca da liberdade dele. " +
                "No castelo, ela conhece objetos mágicos e descobre que a Fera é, na verdade, " +
                "um príncipe que precisa de amor para voltar à forma humana.",
                "01/01/2018", 3.5f));

        arrayList.add(new ItemFilme("Homem Cobra", "Filme de herói picado por" +
                "uma cobra", "1/05/2016", 2));

        arrayList.add(new ItemFilme("Homem Formiga", "Filme de herói picado por" +
                "uma formiga", "10/06/2016", 3));

        arrayList.add(new ItemFilme("Homem Javali", "Filme de herói mordido por" +
                "um Javali", "10/07/2016", 5));

        arrayList.add(new ItemFilme("Homem Passaro", "Filme de herói picado por" +
                "um pássaro", "10/07/2016", 3.5f));

        arrayList.add(new ItemFilme("Homem Gato", "Filme de herói mordido por" +
                "um gato", "10/08/2016", 2.5f));

        adapter = new FilmesAdapter(getContext(), arrayList);
        adapter.setUseFilmeDestaque(useFilmeDestaque);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ItemFilme itemFilme = arrayList.get(position);
                Callback callback = (Callback) getActivity();
                callback.ontItemSelected(itemFilme);
                posicaoItem = position;
            }
        });

        if ( savedInstanceState != null && savedInstanceState.containsKey(KEY_POSICAO)) {
            posicaoItem = savedInstanceState.getInt(KEY_POSICAO);
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if (posicaoItem != ListView.INVALID_POSITION) {
            outState.putInt(KEY_POSICAO, posicaoItem);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (posicaoItem != ListView.INVALID_POSITION && list != null ) {
            list.smoothScrollToPosition(posicaoItem);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_Atualizar:
                Toast.makeText(getContext(), "Atualizando os filmes...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.menu_Sobre:
                Toast.makeText(getContext(), "Desenvolvido por Jean Carlos...",
                        Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setUseFilmeDestaque(boolean useFilmeDestaque) {
        this.useFilmeDestaque = useFilmeDestaque;

        if (adapter != null) {
            adapter.setUseFilmeDestaque(useFilmeDestaque);
        }
    }

    public interface Callback {
        void ontItemSelected(ItemFilme filme);
    }
}
