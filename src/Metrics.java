import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


 class Metrics {
    @picocli.CommandLine.Option(names={"-l","--lines"})
            ArrayList<String> lines;
     @picocli.CommandLine.Option(names={"-w","--words"})
             ArrayList<String> words;
     @picocli.CommandLine.Option(names={"-c","--characters"})
             ArrayList<String> characterss;
     @picocli.CommandLine.Option(names={"-s","--sourcelines"})
             ArrayList<String> sourcelines;
     @picocli.CommandLine.Option(names={"-C","--lines"})
             ArrayList<String> commentlines;
     @picocli.CommandLine.Option(names={"-h","--help"})
             ArrayList<String> help;
    @picocli.CommandLine.Parameters
            ArrayList<String> positional;
    public static void main(String [] args){
        boolean headerYes=false;
        boolean jc=false;
        int tic=0;
        String line=null;
        int charz=0;
        int count=0;
        int wordz=0;
        int totchar=0;
        int totcount=0;
        int totword=0;
        //boolean printl=false;
        //boolean printw=false;
        //boolean printc=false;
        boolean everything=false;
        ArrayList<Boolean> printer=new ArrayList<>();
        String fileName="";
        int siz=args.length;
        if (args.length==0) {//nothing in command line
            System.out.println("0 0 0 ");
        }else if(args[0].equals("wc")) {
            printer = instructions(args, siz,everything);

            for (int i = 0; i < args.length; i++) {//at least one thing in command line
                try {

                    fileName = args[tic];
                    FileReader reading = new FileReader(fileName);
                    BufferedReader buff = new BufferedReader(reading);
                    while ((line = buff.readLine()) != null) {
                        if(line.contains(".java")||line.contains(".c")||line.contains(".h")||line.contains(".cpp")||line.contains(".hpp")){
                            jc=true;
                        }
                        charz = charz + line.length();
                        wordz = wordz + line.split("\\s+").length;
                        count++;
                    }
                    buff.close();
                } catch (Exception e) {
                    System.out.println("error reading file");
                }
                if(printer.get(0)) {
                    if (count != 0) {
                        System.out.print(count + "  ");
                    }
                }
                if(printer.get(1)) {
                    if (wordz != 0) {
                        System.out.print(wordz + "  ");
                    }
                }
                if(printer.get(2)) {
                    if(charz!=0) {
                        System.out.print(charz + "  ");
                    }
                }
                if(printer.get(0)||printer.get(1)||printer.get(2)) {
                    if(count!=0) {
                        System.out.print(fileName);
                    }
                }
                System.out.println(" ");
                totchar = totchar + charz;
                totcount = totcount + count;
                totword = totword + wordz;
                charz = 0;
                count = 0;
                wordz = 0;
                tic++;
            }
        }
        if(printer.get(0)) {
            System.out.print(totcount + "  ");
        }
        if(printer.get(1)) {
            System.out.print(totword + "  ");
        }
        if (printer.get(2)) {
            System.out.print(totchar + "  ");
        }
        if(printer.get(0)||printer.get(1)||printer.get(2)) {
            System.out.print("Total");
        }
    }
    public static ArrayList<Boolean> instructions(String[]a,int inst,boolean e){
        ArrayList<Boolean> rets=new ArrayList<>();
        rets.add(false);
        rets.add(false);
        rets.add(false);
            if(inst==1){
                    System.out.println("wc will print instructions for how to use wc ");
                    System.out.println("wc -l <filename> will print the line count of a file");
                    System.out.println("wc -c <filename> will print the character count");
                    System.out.println("wc -w <filename> will print the word count");
                    System.out.println("wc <filename> will print all of the above");
            }else if(a[1].charAt(0)!='-') {
                e = true;
            }

            for(int i=1; i<inst-1;i++){
                char c=a[i].charAt(0);
                if(c=='-'){
                    if(a[i].contains("l")){
                        rets.set(0,true);
                    }
                    if(a[i].contains("w")){
                        rets.set(1,true);
                    }
                    if(a[i].contains("c")){
                        rets.set(2,true);
                    }
                }
            }
            if(e){
                rets.set(0,true);
                rets.set(1,true);
                rets.set(2,true);
            }
        return rets;
            }


   public static void headerPrint(boolean a,boolean b, ArrayList<String> l,ArrayList<String> w,ArrayList<String> c,ArrayList<String> s,ArrayList<String> cm){
        if(a&&b){
            if(l!=null){
                System.out.println("Lines   ");
            }
            if(w!=null){
                System.out.println("Words  ");
            }
            if(c!=null){
                System.out.println("Characters   ");
            }
            if(s!=null){
                System.out.println("SourceLines   ");
            }
            if(cm!=null){
                System.out.println("Comments   ");
            }
        }


   }
 }

