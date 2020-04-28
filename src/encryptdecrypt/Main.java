package encryptdecrypt;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        if(args.length == 0){
            System.out.println("\nBrak danych."+"\n");
            System.out.println("-----------INSTRUKCJA DZIALANIA-----------");
            System.out.println("Użyj:"+"\n\n"+"-mode enc/dec, aby wybrać działanie - kodowanie lub dekodowanie np. -mode dec \n");
            System.out.println("-key liczba, aby wybrać ziarno kodowania/dekodowania np. -key 5\n");
            System.out.println("-data \"text\", aby dać wiadomość do zakodowania/odkodowania np. -data \"Moj pierwszy program\"\n ");
            System.out.println("-in \"Path to file\", aby przekazać plik z wiadomością do zakodowania np. -in \"Users/file.txt\"\n");
            System.out.println("-out \"Path to file\", aby przekazać gdzie ma być i jak ma się nazywać plik wyjściowy np. -out \"Users/out.txt\"\n");
            System.out.println("Przykładowe użycie: java encryptdecrypt.Main -mode enc -key 5 -data \"Witam serdecznie\"\n");
            System.out.println("-------------------------------");
        } else {
            Arguments arg = new Arguments(args);
            if ((arg.mode).equalsIgnoreCase("enc") || (arg.mode).equalsIgnoreCase("dec")) {

                String outputMessage = "";
                if(arg.data.isEmpty()){
                    if(arg.outFileName.isEmpty()){
                        try {
                            File inFile = new File(arg.inFileName);
                            Scanner scan = new Scanner(inFile);
                            outputMessage = encodeOrDecode(scan.nextLine(), arg.key, outputMessage, arg.mode);
                        } catch (Exception e) {
                            System.out.println("Something went wrong");
                        }
                        System.out.println("-------------------------------\n\n\n\n\t"+outputMessage+"\n\n\n\n-------------------------------");
                    } else {
                        try{
                            File inFile = new File(arg.inFileName);
                            Scanner scan = new Scanner(inFile);
                            outputMessage = encodeOrDecode(scan.nextLine(), arg.key, outputMessage, arg.mode);
                            File outFile = new File(arg.outFileName);
                            FileWriter writer = new FileWriter(outFile);
                            writer.write(outputMessage);
                            writer.flush();
                            System.out.println("Done.");
                        } catch (Exception e) {
                            System.out.println("Something went wrong");
                        }
                    }
                } else {
                    if(arg.outFileName.isEmpty()){
                        outputMessage = encodeOrDecode((arg.data), (arg.key), outputMessage, (arg.mode));
                        System.out.println("-------------------------------\n\n\n\n\t"+outputMessage+"\n\n\n\n-------------------------------");
                    } else {
                        try{
                            outputMessage = encodeOrDecode(arg.data, arg.key, outputMessage, arg.mode);
                            File outFile = new File(arg.outFileName);
                            FileWriter writer = new FileWriter(outFile);
                            writer.write(outputMessage);
                            writer.flush();
                            System.out.println("Done.");
                        } catch (Exception e) {
                            System.out.println("Something went wrong");
                        }
                    }
                }

            } else {
                System.out.println("Idk what to do with this message.");
            }
        }
    }


    static String encodeOrDecode(String rawData, int key, String codedData, String whatToDo) {
        Alphabet alph = new Alphabet();
        switch (whatToDo) {
            case ("enc"): {
                for (int i = 0; i < rawData.length(); ++i) {
                    char letterToCode = rawData.charAt(i);

                    if (Character.isWhitespace(letterToCode)) {
                        codedData += "%";
                    } else if(letterToCode == '\"'){
                        continue;
                    } else {
                        letterToCode = Alphabet.getRightChar(letterToCode, key);
                        //letterToCode += key;
                        codedData += letterToCode;
                    }
                }
                break;
            }

            case ("dec"): {
                for (int i = 0; i < rawData.length(); ++i) {
                    char letterToCode = rawData.charAt(i);

                    if (letterToCode == '%') {
                        codedData += " ";
                    } else {
                        //letterToCode = Alphabet.getRightChar(letterToCode, -key);
                        letterToCode += (-key);
                        codedData += letterToCode;
                    }
                }
                break;
            }

        }

        return codedData;
    }

}

final class Arguments {
    int key = 0;
    String mode = "";
    String data= "";
    String inFileName = "";
    String outFileName = "";

    public Arguments(String[] args) {
        for(int i = 0; i < args.length; ++i) {
            switch(args[i]) {
                case("-mode"): {
                    this.mode = args[i+1];
                    break;
                }
                case("-data"): {
                    if(this.inFileName != ""){
                        this.inFileName = "";
                    }
                    this.data = args[i+1];
                    break;
                }
                case("-in"): {
                    if(!this.data.isEmpty()) {
                        break;
                    } else {
                        this.inFileName = args[i+1];
                        break;
                    }
                }
                case("-out"): {
                    outFileName = args[i+1];
                    break;
                }
                case("-key"): {
                    this.key = Integer.parseInt(args[i+1]);
                    break;
                }
                default: {
                    break;
                }
            }
        }
    }


}


class Alphabet {
    static String alphabet = "!\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    static char[] alph = alphabet.toCharArray();

    public static int indexOfLetter(char X){
        for(int i = 0; i < alph.length; ++i){
            if(X == alph[i]){
                return i;
            }
        }

        return -1;
    }

    public static int whatIndexAfterShifting(int a, int key){
        if (key < 0){
            if (a + key < 0) {
                return (alph.length)-(a+key);
            } else {
                return a + key;
            }
        } else {
            if(a + key > alph.length-1){
                return (a+key)-(alph.length);
            }  else {
                return a + key;
            }
        }

    }

    public static char getRightChar(char toCode, int theKey){

        return alph[whatIndexAfterShifting(indexOfLetter(toCode), theKey)];
    }

}
