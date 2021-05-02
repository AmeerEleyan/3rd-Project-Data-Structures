package project3;

public class Babys implements Comparable<Babys> {

    /**
     * Attribute
     */
    private String name;
    private char gender;
    private int frequency;

    // no arg constructor
    public Babys() {

    }

    // constructor with a specific data
    public Babys(String name, char gender, int frequency) {
        this.name = name;
        this.gender = Character.toUpperCase(gender);
        this.frequency = frequency;
    }

    // constructor with line of data
    public Babys(String lineOfDate) {
        int FirstQ = lineOfDate.indexOf(',');
        int LastQ = lineOfDate.lastIndexOf(',');
        this.name = lineOfDate.substring(0, FirstQ).trim();
        this.gender = Character.toUpperCase((lineOfDate.substring(FirstQ + 1, LastQ)).trim().charAt(0));
        this.frequency = Integer.parseInt(lineOfDate.substring(LastQ + 1).trim());
    }

    // return the name of this baby
    public String getName() {
        return this.name;
    }

    // set a new name for this baby
    public void setName(String name) {
        this.name = name;
    }

    // return the gander of this baby
    public char getGender() {
        return this.gender;
    }

    // set a new gander for this baby
    public void setGender(char gender) {
        this.gender = gender;
    }

    // return the frequency of this object
    public int getFrequency() {
        return this.frequency;
    }


    // set a new frequency for this object
    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    // check baby if is female
    public boolean isFemale() {
        return this.gender == 'F';
    }

    // check baby if is male
    public boolean isMale() {
        return this.gender == 'M';
    }

    // return the name and gender od this baby as string
    @Override
    public String toString() {
        return name + ", " + gender;
    }

    // compare two object based to the name and gender
    @Override
    public int compareTo(Babys o) {
        int compareName = this.name.compareTo(o.name);
        int thisGender = this.gender;
        int objGender = o.gender;
        if (compareName != 0) {
            return compareName;
        } else {
            if (thisGender == objGender) return 0;
            else if (thisGender > objGender) return 1;
            else return -1;
        }
    }

    // compare to baby based of the name and gander
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Babys)
            return (this.name.equals(((Babys) obj).name) && this.gender == ((Babys) obj).gender);
        return false;
    }
}
