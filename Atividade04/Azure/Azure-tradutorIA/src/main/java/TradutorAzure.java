import com.azure.ai.translation.text.TextTranslationClient;
import com.azure.ai.translation.text.TextTranslationClientBuilder;
import com.azure.ai.translation.text.models.TranslatedTextItem;
import com.azure.core.credential.AzureKeyCredential;
import java.util.Collections;

public class TradutorAzure {

    private static final String ENDPOINT = "https://tradutorlivraria.cognitiveservices.azure.com/";
    private static final String KEY = "8G06hE6eeoYykBSH6lmvdkF79Il49nbFPBisDG2on72f7HUqmxSKJQQJ99BJACHrzpqXJ3w3AAAbACOGQLBP";
    private static final String REGION = "northcentralus";

    public static void main(String[] args) {
        System.out.println("--- Azure AI Translator ---");

        TextTranslationClient client = new TextTranslationClientBuilder()
                .credential(new AzureKeyCredential(KEY))
                .endpoint(ENDPOINT)
                .region(REGION)
                .buildClient();

        String textoOriginal = "Pode ser que estejamos todos no fundo do mar, dentro de submarinos. Sonhando essa realidade.";
        String idiomaDestino = "en";

        System.out.println("\nTexto Original (pt): " + textoOriginal);
        System.out.println("Traduzindo para: " + idiomaDestino);

        try {
            // Traduz o texto
            for (TranslatedTextItem item : client.translate(idiomaDestino, Collections.singletonList(textoOriginal))) {
                item.getTranslations().forEach(t -> {
                    System.out.printf(
                        "\nResultado da Tradução (%s): %s%n",
                        t.getTargetLanguage(),   
                        t.getText()              
                    );
                });
            }

        } catch (Exception e) {
            System.err.println("Erro ao traduzir. Verifique suas credenciais e configurações.");
            e.printStackTrace();
        }
    }
}
