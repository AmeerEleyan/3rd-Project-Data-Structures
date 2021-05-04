package Project;

public class BabyForTable implements Comparable<BabyForTable> {
    private String name;
    private char gender;
    private int frequency;

    public BabyForTable(String name, char gender, int frequency) {
        this.name = name;
        this.gender = gender;
        this.frequency = frequency;
    }

    @Override
    public int compareTo(BabyForTable o) {
        return 0;
    }
}
