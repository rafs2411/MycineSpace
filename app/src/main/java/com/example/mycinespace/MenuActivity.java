package com.example.mycinespace;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private EditText editTextBusca;
    private RecyclerView rvFilmes;
    private TextView tvVazio;
    private ImageButton btnHome;
    private ImageButton btnLista;
    private FilmeAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        editTextBusca = findViewById(R.id.editTextBusca);
        rvFilmes = findViewById(R.id.rvFilmes);
        tvVazio = findViewById(R.id.tvVazio);
        btnHome = findViewById(R.id.btnHome);
        btnLista = findViewById(R.id.btnLista);

        adapter = new FilmeAdapter(criarFilmes(), new FilmeAdapter.OnFilmeClickListener() {
            @Override
            public void onFilmeClick(Filme filme) {
                Intent intent = new Intent(MenuActivity.this, DetalheActivity.class);
                intent.putExtra(DetalheActivity.EXTRA_FILME, filme);
                startActivity(intent);
            }
        });

        rvFilmes.setLayoutManager(new GridLayoutManager(this, 2));
        rvFilmes.setAdapter(adapter);

        editTextBusca.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filtrar(s.toString());
                tvVazio.setVisibility(adapter.estaVazio() ? View.VISIBLE : View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        editTextBusca.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                esconderTeclado();
                editTextBusca.clearFocus();
                return true;
            }
            return false;
        });

        btnHome.setOnClickListener(v -> rvFilmes.smoothScrollToPosition(0));

        btnLista.setOnClickListener(v ->
                Toast.makeText(this, "Em breve", Toast.LENGTH_SHORT).show());
    }

    private List<Filme> criarFilmes() {
        List<Filme> filmes = new ArrayList<>();

        filmes.add(new Filme("O Diabo Veste Prada", 2006, "Comédia/Drama", "Livre",
                R.drawable.poster_diabo_veste_prada,
                "Andy, recém-formada e sonhando com o jornalismo, aceita um emprego como assistente de Miranda Priestly, a exigente e temida editora de uma revista de moda. Entre pedidos impossíveis e noites sem dormir, ela precisa decidir até onde está disposta a ir pelo sucesso.",
                "David Frankel",
                "Aline Brosh McKenna",
                "Meryl Streep, Anne Hathaway, Emily Blunt, Stanley Tucci",
                "ZoknZInQM3o"));

        filmes.add(new Filme("Interestelar", 2014, "Aventura/Sci-fi", "10",
                R.drawable.poster_interestelar,
                "Uma equipe de exploradores viaja através de um buraco de minhoca no espaço, na tentativa de garantir a sobrevivência da humanidade.",
                "Christopher Nolan",
                "Jonathan Nolan, Christopher Nolan",
                "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
                "i6avfCqKcQo"));

        filmes.add(new Filme("Convenção das Bruxas", 2020, "Fantasia/Mistério", "10",
                R.drawable.poster_convencao_bruxas,
                "Depois de perder os pais, um garoto vai morar com a avó e descobre que existe uma convenção secreta de bruxas, que planejam transformar todas as crianças em ratos. Agora ele precisa descobrir como impedir esse plano.",
                "Robert Zemeckis",
                "Robert Zemeckis, Kenya Barris, Guillermo del Toro",
                "Anne Hathaway, Octavia Spencer, Stanley Tucci",
                "8TQLGcnJ3mc"));

        filmes.add(new Filme("Deu a Louca na Chapeuzinho", 2005, "Comédia/Crime", "Livre",
                R.drawable.poster_chapeuzinho,
                "Depois que as receitas de doces da região são roubadas, a polícia investiga o caso e ouve a versão de cada suspeito: Chapeuzinho, o Lobo, a Vovó e o Lenhador. Cada um conta a história do seu jeito, e a verdade vai surgindo aos poucos.",
                "Cory Edwards",
                "Cory Edwards, Todd Edwards, Tony Leech",
                "Anne Hathaway, Glenn Close, James Belushi, Patrick Warburton",
                "lFlR6MZ1Xf0"));

        filmes.add(new Filme("Noivas em Guerra", 2009, "Comédia/Romance", "10",
                R.drawable.poster_noivas_em_guerra,
                "Liv e Emma são melhores amigas desde a infância e sonham com o casamento perfeito. Quando um erro de agenda marca as duas cerimônias para o mesmo dia, a amizade vira uma verdadeira guerra.",
                "Gary Winick",
                "Greg DePaul, Casey Wilson, June Diane Raphael",
                "Kate Hudson, Anne Hathaway, Candice Bergen, Bryan Greenberg",
                "KL9_5mrp4kc"));

        filmes.add(new Filme("Um Dia", 2011, "Drama/Romance", "12",
                R.drawable.poster_um_dia,
                "Dexter e Emma se conhecem na noite da formatura e passam a se encontrar, ano após ano, sempre no mesmo dia: 15 de julho. A história acompanha duas décadas da vida dos dois, entre encontros, desencontros e uma amizade que nunca termina.",
                "Lone Scherfig",
                "David Nicholls",
                "Anne Hathaway, Jim Sturgess, Patricia Clarkson",
                "jVH35GR_uRk"));

        return filmes;
    }

    private void esconderTeclado() {
        View foco = getCurrentFocus();
        if (foco != null) {
            InputMethodManager imm =
                    (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(foco.getWindowToken(), 0);
            }
        }
    }
}