import com.google.gson.annotations.SerializedName;

public class PageEntry implements Comparable<PageEntry> {
    @SerializedName("pdf")
    private final String pdfName;
    private final int page;
    private final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
    }

    public String getPdfName() {
        return pdfName;
    }

    public int getPage() {
        return page;
    }

    public int getCount() {
        return count;
    }

    @Override
    public int compareTo(PageEntry pageEntry) {
        return this.count - pageEntry.count;
    }

    @Override
    public String toString() {
        return "PageEntry{pdf = " + pdfName + ", page = " + page + ", count = " + count + "}";
    }
}
