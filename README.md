# MyCineSpace

MyCineSpace é um aplicativo Android para quem gosta de acompanhar o que já assistiu e o que ainda quer assistir. Ele reúne um catálogo de filmes em uma vitrine de pôsteres, mostra os detalhes de cada título com trailer e permite registrar uma nota e um comentário pessoal sobre os filmes que a pessoa já viu.

O projeto foi feito em Java, com Android Studio, como um estudo de desenvolvimento mobile nativo.

## O que o app faz

Ao abrir, o app leva para a tela de login, onde o e-mail e a senha são validados antes de liberar o acesso. Quem ainda não tem conta pode ir para a tela de cadastro, que confere nome, e-mail, senha e confirmação de senha e avisa exatamente o que está errado em cada campo.

Depois de entrar, a tela principal mostra os filmes em uma grade de dois pôsteres por linha. Há uma barra de busca que filtra o catálogo enquanto a pessoa digita, ignorando maiúsculas e acentos, e uma mensagem amigável quando nada é encontrado.

Tocando em um filme, abre a tela de detalhes, com pôster, ano, gêneros, classificação indicativa, sinopse, direção, roteiro e elenco. O trailer toca dentro do próprio app, e há também a opção de abrir o vídeo direto no YouTube.

Em cada filme dá para marcar "Quero assistir" ou "Já assisti". Ao marcar que já assistiu, aparece a área de avaliação, com nota de 1 a 5 estrelas e um comentário opcional. A avaliação pode ser editada ou excluída depois, e tudo fica salvo no aparelho, então continua lá quando o app é fechado e aberto de novo.

## Telas

Login, cadastro, menu com o catálogo de filmes e detalhes do filme.

## Tecnologias

O app é escrito em Java e usa XML para os layouts, com AppCompat, Material Components e RecyclerView para a grade de filmes. As avaliações e os status dos filmes são guardados com SharedPreferences, e o trailer é exibido por meio de um WebView com o player incorporado do YouTube.

## Como rodar

Você vai precisar do Android Studio instalado e de um emulador ou celular com Android 13 (API 33) ou superior.

Clone o repositório, abra a pasta do projeto no Android Studio e aguarde o Gradle sincronizar. Depois é só escolher o dispositivo e clicar em Run. Para o trailer funcionar, o aparelho precisa estar conectado à internet.

```
git clone https://github.com/rafs2411/mycinespace.git
```

## Estrutura do projeto

As classes ficam em `app/src/main/java/com/example/mycinespace`. `LoginActivity` e `CadastroActivity` cuidam do acesso, `MenuActivity` monta o catálogo e a busca, `DetalheActivity` mostra o filme, o trailer e a avaliação, `Filme` representa os dados de cada título e `FilmeAdapter` alimenta a grade. Os layouts, cores, textos e pôsteres estão em `app/src/main/res`.

## Estado atual e próximos passos

Esta é a primeira versão do app. O login e o cadastro validam os campos, mas ainda não existe um servidor ou banco de dados de contas, e o catálogo é fixo, com seis filmes cadastrados no código. A aba de lista pessoal no menu está marcada como "Em breve".

Entre as ideias para as próximas versões estão criar contas de verdade, buscar filmes de uma API, e montar a tela com a lista de filmes marcados como "Quero assistir" e "Já assisti".

## Autora

Desenvolvido por Rafa.