package Project;

public class BabyForTraverse implements Comparable<BabyForTraverse> {
    private String name;
    private String gender;
    private int frequency;

    public BabyForTraverse(){

    }
    public BabyForTraverse(String name, char gender, int frequency) {
        this.name = name;
        this.frequency = frequency;
        if(gender == 'F' || gender == 'f')
            this.gender = "Female";
        if(gender == 'M' || gender == 'm')
            this.gender = "Male";

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }


    @Override
    public String toString() {
        return name + ", " + gender + ", " + frequency;
    }

    @Override
    public int compareTo(BabyForTraverse o) {
        return 0;
    }
}
