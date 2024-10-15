import frontend.Parser;

import java.io.*;

import static frontend.Lexer.*;

public class Compiler {
    public static void main(String[] args){
        String filePath = "testfile.txt";
        String TrueResultPath="parser.txt";
        String ErrorResultPath="error.txt";
        String TrueAnswer="ans.txt";
        int lineCount=1; //记录行数
        boolean isError=false; //是否存在错误
        Boolean isInAnnotation=false;

        //词法分析
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                isInAnnotation=analyze(line,isInAnnotation,lineCount);
                lineCount++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }

        //语法分析
        Parser parser=new Parser(getErrorList(),getTokenList());
        parser.CompUnit();


        if(!parser.getParserErrorList().isEmpty())isError=true;

        if(!isError){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(TrueResultPath))) {
                writer.write(parser.outTrueParser());
            } catch (IOException e) {
                System.out.println(e);
            }
        }else{
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(ErrorResultPath))) {
                writer.write(parser.outFalseParser());
            } catch (IOException e) {
                System.out.println(e);
            }
        }

//        compareFiles(TrueResultPath, TrueAnswer);

    }

    private static void compareFiles(String filePath1, String filePath2) {
        try (BufferedReader reader1 = new BufferedReader(new FileReader(filePath1));
             BufferedReader reader2 = new BufferedReader(new FileReader(filePath2))) {

            String line1, line2;
            int lineNumber = 0;
            boolean filesAreEqual = true;

            while (true) {
                line1 = reader1.readLine();
                line2 = reader2.readLine();
                lineNumber++;

                if (line1 == null && line2 == null) {
                    break;
                }

                if (line1 == null || line2 == null) {
                    filesAreEqual = false;
                    System.out.println("Files differ at line " + lineNumber);
                    break;
                }

                if (!line1.equals(line2)) {
                    filesAreEqual = false;
                    System.out.println("Files differ at line " + lineNumber + ":");
                    System.out.println("File1: " + line1);
                    System.out.println("File2: " + line2);
                    break;
                }
            }

            if (filesAreEqual) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}