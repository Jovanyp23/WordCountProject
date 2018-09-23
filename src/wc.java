import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class wc {
    public static void main(String [] args){
        int tic=0;
        String line=null;
        int charz=0;
        int count=0;
        int wordz=0;
        int totchar=0;
        int totcount=0;
        int totword=0;
        String fileName="";
        if (args.length==0) {//nothing in command line
            System.out.println("0 0 0 ");
        }else
        for(int i=0; i<args.length;i++) {//at least one thing in command line
            try {

                fileName=args[tic];
                FileReader reading = new FileReader(fileName);
                BufferedReader buff = new BufferedReader(reading);
                while ((line = buff.readLine()) != null) {
                    //System.out.println(line); used this to test
                    charz = charz + line.length();
                    wordz = wordz + line.split("\\s+").length;
                    //System.out.println(line.split("\\s+").length);
                    count++;
                }
                buff.close();
            } catch (FileNotFoundException e) {
                System.out.println("Cannot open file");
            } catch (IOException e) {
                System.out.println("error reading file");
            }
            System.out.print("Lines:" + count+"  ");
            System.out.print("Words:" + wordz+"  ");
            System.out.print("Characters:" + charz+"  ");
            System.out.print(fileName);
            System.out.println(" ");
            totchar=totchar+charz;
            totcount=totcount+count;
            totword=totword+wordz;
            charz=0;
            count=0;
            wordz=0;
            tic++;
        }
        System.out.print("Lines:"+ totcount+"  ");
        System.out.print("Words:"+totword+"  ");
        System.out.print("Characters:"+totchar+"  ");
        System.out.print("Total");
    }
}
