import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


 class Metrics {
    @picocli.CommandLine.Option(names={"-l","--lines"})
    static
    ArrayList<String> lines;
     @picocli.CommandLine.Option(names={"-w","--words"})
     static
     ArrayList<String> words;
     @picocli.CommandLine.Option(names={"-c","--characters"})
     static
     ArrayList<String> characters;
     @picocli.CommandLine.Option(names={"-s","--sourcelines"})
     static
     ArrayList<String> sourcelines;
     @picocli.CommandLine.Option(names={"-C","--lines"})
     static
     ArrayList<String> commentlines;
     @picocli.CommandLine.Option(names={"-h","--help"})
     static
     ArrayList<String> help;
    @picocli.CommandLine.Parameters
            ArrayList<String> positional;
    public static void main(String [] args){//change instruction method to just print instructions, fill arrays with something if everything is printed
        boolean headerYes=false;            //make sloc and com reader,keep tallies of sloc and com and their totals
        boolean jc=false;
        int tic=0;
        String line=null;
        int charz=0;
        int count=0;
        int wordz=0;
        int totchar=0;
        int totcount=0;
        int totword=0;
        boolean wasRead=false;
        boolean everything=false;
        ArrayList<Boolean> printer=new ArrayList<>();
        String fileName="";
        int siz=args.length;
        if (args.length==0) {//nothing in command line
            System.out.println("0 0 0 ");
        }
        else if(help!=null){
            //print out the instructions
        } else if(help==null) {
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
                headerPrint(headerYes,jc,lines,words,characters,sourcelines,commentlines);
                if(lines!=null) {
                    if (count != 0) {
                        wasRead=true;
                        System.out.print(count + "  ");
                    }
                }
                if(words!=null) {
                    if (wordz != 0) {
                        wasRead=true;
                        System.out.print(wordz + "  ");
                    }
                }
                if(characters!=null) {
                    if(charz!=0) {
                        wasRead=true;
                        System.out.print(charz + "  ");
                    }
                }
                if(sourcelines!=null) {//placeholder for sourcelines counter
                    if(charz!=0) {
                        wasRead=true;
                        System.out.print(charz + "  ");
                    }
                }
                if(commentlines!=null) {//placeholder for commentlines counter
                    if(charz!=0) {
                        wasRead=true;
                        System.out.print(charz + "  ");
                    }
                }
                if(wasRead) {
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
        if(lines!=null) {
            System.out.print(totcount + "  ");
        }
        if(words!=null) {
            System.out.print(totword + "  ");
        }
        if (characters!=null) {
            System.out.print(totchar + "  ");
        }
        if (sourcelines!=null) {//placeholder for tot sourcelines
            System.out.print(totchar + "  ");
        }
        if (commentlines!=null) {//placeholder for tot commentlines
            System.out.print(totchar + "  ");
        }
        if(wasRead) {
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

