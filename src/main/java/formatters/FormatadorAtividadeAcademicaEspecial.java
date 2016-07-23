package formatters;

import utils.MatcherUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;

public class FormatadorAtividadeAcademicaEspecial extends FormatadorPadrao {

    public FormatadorAtividadeAcademicaEspecial(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividade() {
        return "atividadeAcademicaEspecial";
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "outras";
    }

    @Override
    public ArrayList<String> obtenhaRegistros(Matcher matcher, String regexRegistrosIndividuais, String regexRegistroUnico, HashMap substituicoes) {
        String conteudoDoArquivo;
        ArrayList<String> atividadesAcademicasEspeciais = new ArrayList<String>();

        if(matcher.find()) {
            conteudoDoArquivo = matcher.group();
            conteudoDoArquivo = substituiOcorrencias(conteudoDoArquivo, substituicoes);

            Matcher matcherAtividadesIndividuais = MatcherUtils.obtenhaMatcher(regexRegistrosIndividuais, conteudoDoArquivo);
            int contadorAtividadesIndividuais = 0;

            while(matcherAtividadesIndividuais.find()) {
                Matcher matcherAtividade;
                matcherAtividade = MatcherUtils.obtenhaMatcher(regexRegistroUnico, matcherAtividadesIndividuais.group());
                while(matcherAtividade.find()) {
                    String atividadeTratada = trateAtividadeAcademicaEspecial(matcherAtividade);
                    Matcher matcherAtividadeTratada = MatcherUtils.obtenhaMatcher("(.+?)\\n(.+?)\\n(.+?)\\n(.+)\\n(.+)",atividadeTratada);
                    matcherAtividadeTratada.find();
                    atividadesAcademicasEspeciais.add(obtenhaLinhaDeRegistroPadronizado(contadorAtividadesIndividuais, matcherAtividadeTratada));
                }

                contadorAtividadesIndividuais++;
            }
        }

        return atividadesAcademicasEspeciais;
    }

    /**
     * Rearranjo das atividadades acadêmicas especiais para a adequação ao formato
     * definido pelo método obtenhaLinhaDeRegistroPadronizado.
     * @param matcher Matcher para a atividade.
     * @return Rearranjo das atividades;
     */
    private String trateAtividadeAcademicaEspecial(Matcher matcher) {
        return matcher.group(1) + "\n" + matcher.group(5) + "\n" + matcher.group(2) + "\n" + matcher.group(3) + "\n" + matcher.group(4);
    }
}
