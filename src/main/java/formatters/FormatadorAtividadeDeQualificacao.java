package formatters;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;

import java.util.regex.Matcher;

public class FormatadorAtividadeDeQualificacao extends FormatadorPadrao {

    public FormatadorAtividadeDeQualificacao(String pathResolucao) {
        super(pathResolucao);
    }

    @Override
    public String obtenhaTipoAtividadeResolucao() {
        return "outras";
    }

    @Override
    public String obtenhaPontuacao(Matcher matcher) {
        return super.obtenhaPontuacaoBaseadaEmAnos(matcher);
    }

    @Override
    public String obtenhaCampo(Matcher matcher, String campo) {
        HashMap<String, Map> anexoIIResolucao = getResolucaoParser().obtenhaAtividadesResolucao();
        String key = matcher.group(1).toLowerCase().trim();

        String codGrupoPontuacao = "";
        HashMap<String, Map> mapaAtividade = (HashMap<String, Map>) anexoIIResolucao.get(obtenhaTipoAtividadeResolucao());

        for(String k : mapaAtividade.keySet()) {
            if(k.equals(key) || k.contains(key)) {
                codGrupoPontuacao = String.valueOf(mapaAtividade.get(k).get(campo));
                break;
            }
        }

        return codGrupoPontuacao;
    }
}