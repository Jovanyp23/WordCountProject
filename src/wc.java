import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class wc {
    public static void main(String [] args){
        System.out.println("Hey hm");
        String fileName="temp.txt";
        String line=null;
        try{


            FileReader reading= new FileReader(fileName);
            BufferedReader buff= new BufferedReader(reading);
            while((line=buff.readLine()) !=null){
                System.out.println(line);
            }
             buff.close();
        }
        catch(FileNotFoundException e){
            System.out.println("Cannot open file");
        }
        catch(IOException e){
            System.out.println("error reading file");
        }

    }
}
