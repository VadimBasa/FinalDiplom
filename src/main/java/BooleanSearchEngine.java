import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BooleanSearchEngine implements SearchEngine {

    Map<String, List<PageEntry>> indexWords = new HashMap<>();

    public BooleanSearchEngine(File pdfsDir) throws IOException, NullPointerException {
        List<File> nameFile = new ArrayList<>(Arrays.asList(pdfsDir.listFiles()));
        for (int i = 0; i < nameFile.size(); i++) {
            var doc = new PdfDocument(new PdfReader(nameFile.get(i)));//возможно i+1
            for (int j = 0; j < doc.getNumberOfPages(); j++) {
                int indexPage = j + 1;
                var text = PdfTextExtractor.getTextFromPage(doc.getPage(indexPage));
                var words = (text.toLowerCase().split("\\P{IsAlphabetic}+"));
                Map<String, Integer> freqs = new HashMap<>(); // мапа, где ключом будет слово, а значением - частота
                for (var word : words) { // перебираем слова
                    if (word.isEmpty()) {
                        continue;
                    }
                    word = word.toLowerCase();
                    freqs.put(word, freqs.getOrDefault(word, 0) + 1);//добавляем в мапу слова в нижнем регистре
                }
                for (var word : freqs.entrySet()) {//перебираем мапу по сету ключ-слово-количество
                    List<PageEntry> wordCount;// = new ArrayList<>(); //это работает
                    if (indexWords.containsKey(word.getKey())) {
                        wordCount = indexWords.get(word.getKey());//если в мапе есть это слово, то возврат листа PageEntry
                    } else {
                        wordCount = new ArrayList<>();
                    }
                    wordCount.add(new PageEntry(nameFile.get(i).getName(), indexPage, word.getValue()));
                    Collections.sort(wordCount, Collections.reverseOrder());
                    indexWords.put(word.getKey(), wordCount);
                }
            }
        }
    }

    @Override
    public List<PageEntry> search(String word) {
        return indexWords.get(word.toLowerCase());
    }
}