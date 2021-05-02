package project3;

public class tesr {
    public static void main(String []args){
        String str = "USA_yob2000.txt";
        int count = 0;
        String year = "";
        for(int i=0;i<str.length();++i){
            if(Character.isDigit(str.charAt(i))){
                year += str.charAt(i);
                count++;
                if(count == 4) break;
            }else {
                count = 0;
            }
        }
        str.matches(".*\\d{4}.*");
        System.out.println(count+" "+Integer.parseInt(year));
    }
}
