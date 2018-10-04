import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


 public class Metrics implements Runnable {
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
     @picocli.CommandLine.Option(names={"-C","--commentlines"})
     static
     ArrayList<String> commentlines;
     @picocli.CommandLine.Option(names={"-h","--help"})
     static
     ArrayList<String> help;
    @picocli.CommandLine.Parameters
    static ArrayList<String> positional;
     public void run() {
       //picocli features simply wont work without implementing runnable for whatever reason?
     }
    public static void main(String[] args){//fiddle with header printing
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
        int comtrack=0;
        int sourcetrack=0;
        int totComTrack=0;
        int totSourceTrack=0;
        boolean multiComment=false;
        boolean wcParams=false;
        boolean wasRead=false;
        String fileName="";
        picocli.CommandLine.run(new Metrics(),System.err, args);
        ArrayList<String>allArgs=groupFiles(lines,words,characters,sourcelines,commentlines);
        if (args.length==0) {
            System.out.println("0 0 0 ");
        }
        else if(help!=null){
            instructions();
        } else if(help==null) {
            for (int i = 0; i < allArgs.size(); i++) {
                try {

                    fileName = allArgs.get(tic);
                    FileReader reading = new FileReader(fileName);
                    BufferedReader buff = new BufferedReader(reading);
                    while ((line = buff.readLine()) != null) {
                        if(line.contains(".java")||line.contains(".c")||line.contains(".h")||line.contains(".cpp")||line.contains(".hpp")){
                            jc=true;
                        }
                        if(line.contains("//")||line.contains("/*")||line.contains("*/")||multiComment){
                            comtrack++;
                            if(line.contains("/*")) {
                                multiComment = true;
                            }
                            if(line.contains("*/")) {
                                multiComment = false;
                            }
                        }
                        if(!multiComment){
                            if(line.contains("//")||line.contains("/*")){
                                if(line.lastIndexOf("//")>=2||line.lastIndexOf("/*")>=2){
                                    if(!line.equals(""))
                                    sourcetrack++;
                                }
                            }else {
                                sourcetrack++;
                            }
                            }


                        charz = charz + line.length();
                        wordz = wordz + line.split("\\s+").length;
                        if(!line.equals("")) {
                            count++;
                        }
                    }
                    buff.close();
                } catch (Exception e) {
                    System.out.println("error reading file");
                }
                if(sourcelines!=null||commentlines!=null){
                    headerYes=true;
                }
                headerPrint(headerYes,jc,lines,words,characters,sourcelines,commentlines);
                if(positional!=null){
                    wcParams=true;
                }
                if(lines!=null||wcParams) {
                    if (count != 0) {
                        wasRead=true;
                        System.out.print(count + "  ");
                    }
                }
                if(words!=null||wcParams) {
                    if (wordz != 0) {
                        wasRead=true;
                        System.out.print(wordz + "  ");
                    }
                }
                if(characters!=null||wcParams) {
                    if(charz!=0) {
                        wasRead=true;
                        System.out.print(charz + "  ");
                    }
                }
                if(sourcelines!=null) {
                    if(sourcetrack!=0) {
                        wasRead=true;
                        System.out.print(sourcetrack + "  ");
                    }
                }
                if(commentlines!=null) {
                    if(comtrack!=0) {
                        wasRead=true;
                        System.out.print(comtrack + "  ");
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
                totComTrack=totComTrack+comtrack;
                totSourceTrack=totSourceTrack+sourcetrack;
                charz = 0;
                count = 0;
                wordz = 0;
                sourcetrack=0;
                comtrack=0;
                tic++;
            }
        }
        if(lines!=null||wcParams) {
            System.out.print(totcount + "  ");
        }
        if(words!=null||wcParams) {
            System.out.print(totword + "  ");
        }
        if (characters!=null||wcParams) {
            System.out.print(totchar + "  ");
        }
        if (sourcelines!=null) {
            System.out.print(totSourceTrack + "  ");
        }
        if (commentlines!=null) {
            System.out.print(totComTrack + "  ");
        }
        if(wasRead&&allArgs.size()>1) {
            System.out.print("Total");
        }
    }
    public static void instructions(){
         System.out.println("-h or --help will print out these instructions ");
         System.out.println("-l or --lines before a file name will print the line count of a file");
         System.out.println("-w or --words before a file name will print the word count of a file");
         System.out.println("-c or --Characters before a file name will print the character count of a file");
         System.out.println("-s or --sourcelines before a file name will print the sourceline count of a file");
         System.out.println("-C or --commentlines before a file name will print the commentline count of a file");

            }


   public static void headerPrint(boolean a,boolean b, ArrayList<String> l,ArrayList<String> w,ArrayList<String> c,ArrayList<String> s,ArrayList<String> cm){//this is actually never called yet
        if(a&&b){
            System.out.println("Is this thing on?");
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
   public static ArrayList<String> groupFiles(ArrayList<String> l,ArrayList<String> w,ArrayList<String> c,ArrayList<String> s,ArrayList<String> cm){
         ArrayList<String> grouped = new ArrayList<>();
         if (l!=null) {
             grouped.addAll(l);
         }
        if (w!=null) {
           grouped.addAll(w);
        }
        if (c!=null) {
           grouped.addAll(c);
        }
        if (s!=null) {
           grouped.addAll(s);
        }
        if (cm!=null) {
           grouped.addAll(cm);
        }
       if (cm!=null) {
           grouped.addAll(cm);
       }
       if (positional!=null) {
           grouped.addAll(positional);
       }
      return grouped;
     }

 }

