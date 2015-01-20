package demo4;

public class TextSearch {

    public static void main(String[] args) {
        BoyerMooreTextSearchStrategy strategy = new BoyerMooreTextSearchStrategy();
        strategy.setSubtext("ab");

        long startTime = System.currentTimeMillis();

        int checksum = 0;
        for (int i = 0; i < 1000000; i++) {
            checksum += strategy.indexOf("abracadabra");
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Elapsed: " + (endTime - startTime) + ", checksum = " + checksum);
    }
}
