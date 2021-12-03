package app.azure;

import com.azure.core.credential.AzureKeyCredential;
import com.azure.ai.textanalytics.models.*;
import com.azure.ai.textanalytics.TextAnalyticsClientBuilder;
import com.azure.ai.textanalytics.TextAnalyticsClient;

public class JavaAzure {
    private static String KEY = "CHAVE_ULTRA_SECRETA";
    private static String ENDPOINT = "URL_ULTRA_SECRETA";

    public static void main(String[] args) {
        TextAnalyticsClient client = authenticateClient(KEY, ENDPOINT);

        analyseSentiments(client, "I had the best day of my life. I wish you were there with me.");
        analyseSentiments(client, "That game was a shame, the surrender in the first round!");
        analyseSentiments(client, "To capture pirate empress Boa Hancock, we're heading to Amazon Lily.");
        analyseSentiments(client, "Will justice be done? But of course it will, because it's the winner who dictates justice.");
    }

    static TextAnalyticsClient authenticateClient(String key, String endpoint) {
        return new TextAnalyticsClientBuilder()
                .credential(new AzureKeyCredential(key))
                .endpoint(endpoint)
                .buildClient();
    }

    static void analyseSentiments(TextAnalyticsClient client, String text)
    {
        System.out.println("Frase: " + text);

        DocumentSentiment documentSentiment = client.analyzeSentiment(text);

        System.out.printf(
            "Semtimento do documento: %s (positivo: %s, neutro: %s, negativo: %s) %n",
            documentSentiment.getSentiment(),
            documentSentiment.getConfidenceScores().getPositive(),
            documentSentiment.getConfidenceScores().getNeutral(),
            documentSentiment.getConfidenceScores().getNegative());

        for (SentenceSentiment sentenceSentiment : documentSentiment.getSentences()) {
            System.out.printf(
                "Semtimento da frase: %s (positivo: %s, neutro: %s, negativo: %s) %n",
                sentenceSentiment.getSentiment(),
                sentenceSentiment.getConfidenceScores().getPositive(),
                sentenceSentiment.getConfidenceScores().getNeutral(),
                sentenceSentiment.getConfidenceScores().getNegative());
        }
    }
}
