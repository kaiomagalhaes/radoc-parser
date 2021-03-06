package formatters;

import utils.MatcherUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class FormatadorAtividadeDeExtensao extends FormatadorPadrao {

    public FormatadorAtividadeDeExtensao(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public int obtenhaNumeroGrupoTabela() {
        return 1;
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "pesquisaExtensao";
    }

    @Override
    public ArrayList<String> obtenhaRegistros(Matcher matcher, String regexRegistrosIndividuais, String regexRegistroUnico, HashMap substituicoes) {
        ArrayList<String> atividadesDeOrientacao = new ArrayList<String>();
        String conteudoDoArquivo;

        if(matcher.find()) {
            conteudoDoArquivo = matcher.group();
            conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

            String regexAtividadesIndividuais = "Tabela:.+?(?=Tabela)";
            Matcher matcherAtividadesIndividuais = MatcherUtils.obtenhaMatcher(regexAtividadesIndividuais, conteudoDoArquivo);
            int contadorAtividadesIndividuais = 0;

            while(matcherAtividadesIndividuais.find()) {
                Matcher matcherAtividade;
                String regexAtividadeUnica = "Tabela:(.+)?CHA:.+?(\\d+).+?Data início:.+?(\\d+\\/\\d+\\/\\d+).+?Data término:.+?(\\d+\\/\\d+\\/\\d+).+?Descrição da atividade:.+?(.+).+?Descrição da clientela:.+";
                matcherAtividade = MatcherUtils.obtenhaMatcher(regexAtividadeUnica, matcherAtividadesIndividuais.group());
                while(matcherAtividade.find()) {
                    String atividadeTratada = trateAtividadeDeExtensao(matcherAtividade);
                    Matcher matcherAtividadeTratada = MatcherUtils.obtenhaMatcher("(.+?)\\n(.+?)\\n(.+?)\\n(.+)\\n(.+)",atividadeTratada);
                    matcherAtividadeTratada.find();
                    atividadesDeOrientacao.add(obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividadeTratada));
                }

                contadorAtividadesIndividuais++;
            }
        }
        return atividadesDeOrientacao;
    }

    private String trateAtividadeDeExtensao(Matcher matcher){
        return matcher.group(1) + "\n" + matcher.group(5) + "\n" + matcher.group(2) + "\n" + matcher.group(3) + "\n" + matcher.group(4);
    }

    @Override
    public String obtenhaPontuacao(Matcher matcher) {
        return super.obtenhaPontuacaoBaseadaEmAnos(matcher);
    }
}
