package com.example.mycinespace;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RenderEffect;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DetalheActivity extends AppCompatActivity {

    public static final String EXTRA_FILME = "filme";

    private static final int STATUS_NENHUM = 0;
    private static final int STATUS_QUERO_ASSISTIR = 1;
    private static final int STATUS_JA_ASSISTI = 2;

    private static final int COR_DOURADO = Color.parseColor("#EBB65F");
    private static final int COR_TEXTO_ESCURO = Color.parseColor("#30251B");

    private Filme filme;
    private SharedPreferences preferencias;
    private int status;
    private boolean trailerAberto = false;

    private Button btnJaAssisti;
    private Button btnQueroAssistir;
    private Button btnSalvarAvaliacao;
    private LinearLayout secaoAvaliacao;
    private LinearLayout modoEdicao;
    private LinearLayout modoSalvo;
    private RatingBar ratingBar;
    private RatingBar ratingBarSalva;
    private TextView tvNota;
    private TextView tvComentarioSalvo;
    private EditText etComentario;
    private View cardTrailer;
    private ImageView imgTrailer;
    private View btnPlayTrailer;
    private WebView webViewTrailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        filme = lerFilme();
        if (filme == null) {
            filme = filmeDeTeste();
        }

        preferencias = getSharedPreferences("mycinespace", MODE_PRIVATE);

        ImageView imgFundo = findViewById(R.id.imgFundo);
        ImageView imgPoster = findViewById(R.id.imgPoster);
        TextView tvTitulo = findViewById(R.id.tvTitulo);
        TextView tvInfo = findViewById(R.id.tvInfo);
        TextView tvSinopse = findViewById(R.id.tvSinopse);
        TextView tvDirecao = findViewById(R.id.tvDirecao);
        TextView tvRoteiristas = findViewById(R.id.tvRoteiristas);
        TextView tvEstrelas = findViewById(R.id.tvEstrelas);
        TextView tvAbrirYoutube = findViewById(R.id.tvAbrirYoutube);
        TextView tvExcluir = findViewById(R.id.tvExcluir);
        View btnVoltar = findViewById(R.id.btnVoltar);

        cardTrailer = findViewById(R.id.cardTrailer);
        imgTrailer = findViewById(R.id.imgTrailer);
        btnPlayTrailer = findViewById(R.id.btnPlayTrailer);
        webViewTrailer = findViewById(R.id.webViewTrailer);

        btnJaAssisti = findViewById(R.id.btnJaAssisti);
        btnQueroAssistir = findViewById(R.id.btnQueroAssistir);
        btnSalvarAvaliacao = findViewById(R.id.btnSalvarAvaliacao);
        secaoAvaliacao = findViewById(R.id.secaoAvaliacao);
        modoEdicao = findViewById(R.id.modoEdicao);
        modoSalvo = findViewById(R.id.modoSalvo);
        ratingBar = findViewById(R.id.ratingBar);
        ratingBarSalva = findViewById(R.id.ratingBarSalva);
        tvNota = findViewById(R.id.tvNota);
        tvComentarioSalvo = findViewById(R.id.tvComentarioSalvo);
        etComentario = findViewById(R.id.etComentario);

        imgFundo.setImageResource(filme.getPoster());
        imgPoster.setImageResource(filme.getPoster());
        imgPoster.setContentDescription(filme.getTitulo());
        imgTrailer.setImageResource(filme.getPoster());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            imgFundo.setRenderEffect(
                    RenderEffect.createBlurEffect(40f, 40f, Shader.TileMode.CLAMP));
        }

        tvTitulo.setText(filme.getTitulo());
        tvInfo.setText(filme.getInfo());
        tvSinopse.setText(filme.getSinopse());
        tvDirecao.setText(filme.getDirecao());
        tvRoteiristas.setText(filme.getRoteiristas());
        tvEstrelas.setText(filme.getEstrelas());

        status = preferencias.getInt(chaveStatus(), STATUS_NENHUM);
        atualizarTela();
        carregarAvaliacao();

        btnJaAssisti.setOnClickListener(v -> alternarStatus(STATUS_JA_ASSISTI));
        btnQueroAssistir.setOnClickListener(v -> alternarStatus(STATUS_QUERO_ASSISTIR));
        btnSalvarAvaliacao.setOnClickListener(v -> salvarAvaliacao());
        modoSalvo.setOnClickListener(v -> editarAvaliacao());
        tvExcluir.setOnClickListener(v -> excluirAvaliacao());

        ratingBar.setOnRatingBarChangeListener((barra, nota, doUsuario) ->
                atualizarTextoNota(nota));

        cardTrailer.post(() -> {
            int largura = cardTrailer.getWidth();
            if (largura > 0) {
                ViewGroup.LayoutParams params = cardTrailer.getLayoutParams();
                params.height = largura * 9 / 16;
                cardTrailer.setLayoutParams(params);
            }
        });

        cardTrailer.setOnClickListener(v -> mostrarTrailer());
        tvAbrirYoutube.setOnClickListener(v -> abrirNoYoutube());
        btnVoltar.setOnClickListener(v -> finish());
    }

    @SuppressWarnings("deprecation")
    private Filme lerFilme() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return getIntent().getSerializableExtra(EXTRA_FILME, Filme.class);
        }
        return (Filme) getIntent().getSerializableExtra(EXTRA_FILME);
    }

    private Filme filmeDeTeste() {
        return new Filme("Interestelar", 2014, "Aventura/Sci-fi", "10",
                R.drawable.poster_interestelar,
                "Uma equipe de exploradores viaja através de um buraco de minhoca no espaço, na tentativa de garantir a sobrevivência da humanidade.",
                "Christopher Nolan",
                "Jonathan Nolan, Christopher Nolan",
                "Matthew McConaughey, Anne Hathaway, Jessica Chastain",
                "i6avfCqKcQo");
    }

    private void carregarAvaliacao() {
        float nota = preferencias.getFloat(chaveNota(), 0f);
        String comentario = preferencias.getString(chaveComentario(), "");

        ratingBar.setRating(nota);
        etComentario.setText(comentario);
        atualizarTextoNota(nota);

        mostrarModo(preferencias.getBoolean(chaveSalva(), false));
    }

    private void mostrarModo(boolean salvo) {
        if (salvo) {
            float nota = preferencias.getFloat(chaveNota(), 0f);
            String comentario = preferencias.getString(chaveComentario(), "");

            ratingBarSalva.setRating(nota);
            if (comentario.isEmpty()) {
                tvComentarioSalvo.setText(R.string.detalhe_sem_comentario);
            } else {
                tvComentarioSalvo.setText(comentario);
            }

            modoEdicao.setVisibility(View.GONE);
            modoSalvo.setVisibility(View.VISIBLE);
        } else {
            modoSalvo.setVisibility(View.GONE);
            modoEdicao.setVisibility(View.VISIBLE);
        }
    }

    private void salvarAvaliacao() {
        float nota = ratingBar.getRating();
        String comentario = etComentario.getText().toString().trim();

        if (nota == 0f && comentario.isEmpty()) {
            Toast.makeText(this, R.string.detalhe_avaliacao_vazia, Toast.LENGTH_SHORT).show();
            return;
        }

        preferencias.edit()
                .putBoolean(chaveSalva(), true)
                .putFloat(chaveNota(), nota)
                .putString(chaveComentario(), comentario)
                .apply();

        esconderTeclado();
        etComentario.clearFocus();
        mostrarModo(true);
        Toast.makeText(this, R.string.detalhe_salvo, Toast.LENGTH_SHORT).show();
    }

    private void editarAvaliacao() {
        float nota = preferencias.getFloat(chaveNota(), 0f);
        String comentario = preferencias.getString(chaveComentario(), "");

        ratingBar.setRating(nota);
        etComentario.setText(comentario);
        etComentario.setSelection(etComentario.getText().length());
        atualizarTextoNota(nota);

        mostrarModo(false);
        etComentario.requestFocus();
    }

    private void excluirAvaliacao() {
        preferencias.edit()
                .remove(chaveSalva())
                .remove(chaveNota())
                .remove(chaveComentario())
                .apply();

        ratingBar.setRating(0f);
        etComentario.setText("");
        atualizarTextoNota(0f);

        mostrarModo(false);
        Toast.makeText(this, R.string.detalhe_excluido, Toast.LENGTH_SHORT).show();
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

    @SuppressLint("SetJavaScriptEnabled")
    private void mostrarTrailer() {
        if (trailerAberto) {
            return;
        }

        String id = filme.getTrailerId();
        if (id == null || id.isEmpty()) {
            abrirNoYoutube();
            return;
        }

        trailerAberto = true;
        cardTrailer.setClickable(false);
        imgTrailer.setVisibility(View.GONE);
        btnPlayTrailer.setVisibility(View.GONE);
        webViewTrailer.setVisibility(View.VISIBLE);
        webViewTrailer.setBackgroundColor(Color.BLACK);

        WebSettings config = webViewTrailer.getSettings();
        config.setJavaScriptEnabled(true);
        config.setDomStorageEnabled(true);
        config.setMediaPlaybackRequiresUserGesture(false);

        webViewTrailer.setWebViewClient(new WebViewClient());
        webViewTrailer.setWebChromeClient(new WebChromeClient());

        String html = "<html><head>"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "<style>html,body{margin:0;padding:0;height:100%;background:#000;}"
                + "iframe{border:0;width:100%;height:100%;}</style></head><body>"
                + "<iframe src=\"https://www.youtube.com/embed/" + id
                + "?autoplay=1&playsinline=1&rel=0&fs=0\" "
                + "allow=\"autoplay; encrypted-media\"></iframe>"
                + "</body></html>";

        webViewTrailer.loadDataWithBaseURL("https://" + getPackageName(),
                html, "text/html", "utf-8", null);
    }

    private void abrirNoYoutube() {
        String id = filme.getTrailerId();
        Uri endereco;
        if (id != null && !id.isEmpty()) {
            endereco = Uri.parse("https://www.youtube.com/watch?v=" + id);
        } else {
            String busca = Uri.encode(filme.getTitulo() + " trailer oficial");
            endereco = Uri.parse("https://www.youtube.com/results?search_query=" + busca);
        }

        try {
            startActivity(new Intent(Intent.ACTION_VIEW, endereco));
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Não foi possível abrir o trailer", Toast.LENGTH_SHORT).show();
        }
    }

    private void alternarStatus(int alvo) {
        if (status == alvo) {
            status = STATUS_NENHUM;
        } else {
            status = alvo;
        }
        preferencias.edit().putInt(chaveStatus(), status).apply();
        atualizarTela();
    }

    private void atualizarTela() {
        estilizarBotao(btnJaAssisti, status == STATUS_JA_ASSISTI);
        estilizarBotao(btnQueroAssistir, status == STATUS_QUERO_ASSISTIR);

        if (status == STATUS_JA_ASSISTI) {
            secaoAvaliacao.setVisibility(View.VISIBLE);
        } else {
            secaoAvaliacao.setVisibility(View.GONE);
        }
    }

    private void estilizarBotao(Button botao, boolean ativo) {
        if (ativo) {
            botao.setBackgroundResource(R.drawable.bg_botao_cheio);
            botao.setTextColor(COR_TEXTO_ESCURO);
        } else {
            botao.setBackgroundResource(R.drawable.bg_botao_contorno);
            botao.setTextColor(COR_DOURADO);
        }
    }

    private void atualizarTextoNota(float nota) {
        if (nota > 0) {
            tvNota.setText("Sua nota: " + (int) nota + " de 5");
        } else {
            tvNota.setText("Toque nas estrelas para avaliar");
        }
    }

    private String chaveStatus() {
        return "status_" + filme.getTitulo();
    }

    private String chaveNota() {
        return "nota_" + filme.getTitulo();
    }

    private String chaveComentario() {
        return "comentario_" + filme.getTitulo();
    }

    private String chaveSalva() {
        return "avaliacao_salva_" + filme.getTitulo();
    }

    @Override
    protected void onPause() {
        if (webViewTrailer != null) {
            webViewTrailer.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (webViewTrailer != null) {
            webViewTrailer.onResume();
        }
    }

    @Override
    protected void onDestroy() {
        if (webViewTrailer != null) {
            ViewGroup pai = (ViewGroup) webViewTrailer.getParent();
            if (pai != null) {
                pai.removeView(webViewTrailer);
            }
            webViewTrailer.stopLoading();
            webViewTrailer.loadUrl("about:blank");
            webViewTrailer.destroy();
            webViewTrailer = null;
        }
        super.onDestroy();
    }
}
