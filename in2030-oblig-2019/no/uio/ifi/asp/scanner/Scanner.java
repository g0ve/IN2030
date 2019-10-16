package no.uio.ifi.asp.scanner;

import java.io.*;
import java.util.*;

import no.uio.ifi.asp.main.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class Scanner {
    private LineNumberReader sourceFile = null;
    private String curFileName;
    private ArrayList<Token> curLineTokens = new ArrayList<>();
    private Stack<Integer> indents = new Stack<>();
    private final int TABDIST = 4;


    public Scanner(String fileName) {
        curFileName = fileName;
        indents.push(0);

        try {
            sourceFile = new LineNumberReader(
            new InputStreamReader(
            new FileInputStream(fileName),
            "UTF-8"));
        } catch (IOException e) {
            scannerError("Cannot read " + fileName + "!");
        }
    }


    private void scannerError(String message) {
        String m = "Asp scanner error";
        if (curLineNum() > 0)
        m += " on line " + curLineNum();
        m += ": " + message;

        Main.error(m);
    }


    public Token curToken() {
        while (curLineTokens.isEmpty()) {
            readNextLine();
        }
        return curLineTokens.get(0);
    }


    public void readNextToken() {
        if (! curLineTokens.isEmpty())
        curLineTokens.remove(0);
    }


    private void readNextLine() {
        curLineTokens.clear();

        // Read the next line:
        String line = null;
        try {
            line = sourceFile.readLine();
            if (line == null) {
                sourceFile.close();
                sourceFile = null;
            } else {
                Main.log.noteSourceLine(curLineNum(), line);
            }
        } catch (IOException e) {
            sourceFile = null;
            scannerError("Unspecified I/O error!");
        }

        //-- Must be changed in part 1:

        int cntIntedt = 0;
        int pos = 0;
        Token token;
        TokenKind tokenkind = null;
        TokenKind tokenkind2 = null;


        //If the line is emty its the last line
        if(line == null){

            while (cntIntedt < indents.peek()) {
                indents.pop();
                curLineTokens.add(new Token(dedentToken, curLineNum()));
            }

            curLineTokens.add(new Token(eofToken, curLineNum()));

            for (Token t: curLineTokens){
                Main.log.noteToken(t);
            }
            return;
        }
        //expanding Leading tabs in the line
        String modded_line = expandLeadingTabs(line);
        modded_line.trim();

        //The number of indents in the line
        cntIntedt = findIndent(modded_line);

        //Handeling if the line is empty or if the line is a coment
        if(modded_line.isEmpty() || modded_line.charAt(cntIntedt) == '#'){
            return;
        }

        //If the number of indents is higher than the peek of indents
        if(cntIntedt > indents.peek()){
            indents.push(cntIntedt);
            curLineTokens.add(new Token(indentToken, curLineNum()));
        }
        //If its less
        else if(cntIntedt < indents.peek()){
            while(cntIntedt < indents.peek()){
                indents.pop();
                curLineTokens.add(new Token(dedentToken, curLineNum()));
            }
        }
        if(cntIntedt != indents.peek()){
            scannerError("indents error");
        }

        /*
        * In this code segement we need to read every character in every line,
        * and check whether if the character is a name or keyword, int or float number or
        * begining of a string. If the character is none of these things (not a letter nor whitespace).
        * That means the character is a operator.
        *
        * But before we do all of these checks. We need to check if there has been added a
        * comment after a line of code in the same line.
        *
        * Because strings, name/keywords and digits can consists of more than 1 or 2 characters.
        * We need to use a for loop to check the next character if it meets the same conditions.
        * And ofcourse if it is a whitespace. We have reach the end of the string, name/keyword or digit.
        */

        //While loop that reads every character 'c' in line.
        while(pos < line.length()){
            char c = line.charAt(pos);

            if (c == '#') {
                break;
            } else if(isLetterAZ(c)) {
                String nameStr = "";

                for (int nextPos = pos; nextPos < line.length(); nextPos++){
                    char nextC = line.charAt(nextPos);
                    if(isDigit(nextC)){
                        nameStr = nameStr + nextC;
                        pos = nextPos;
                    }else if(isLetterAZ(nextC)){
                        nameStr = nameStr + nextC;
                        pos = nextPos;
                    }else if(nextC == ' '){
                        break;
                    }else{
                        break;
                    }
                }

                tokenkind = checkTokenExists(nameStr);

                if (tokenkind != null) {
                    token = new Token(tokenkind, curLineNum());
                    curLineTokens.add(token);
                }else if (tokenkind == null) {
                    token = new Token(nameToken, curLineNum());
                    token.name = nameStr;
                    curLineTokens.add(token);
                }
            } else if (isDigit(c) || c == '.') {
                boolean floatNumber = false;
                String numberLit = "";

                for (int nextPos = pos; nextPos < line.length(); nextPos++) {
                    char nextC = line.charAt(nextPos);
                    if(nextC == '.') {
                        numberLit = numberLit + nextC;
                        floatNumber = true;
                        pos = nextPos;
                    } else if(isDigit(nextC)) {
                        numberLit = numberLit + nextC;
                        pos = nextPos;
                    } else {
                        break;
                    }
                }

                if (floatNumber) {
                    if(numberLit.length() < 3){
                        scannerError("Not valid float number: " + numberLit);
                    }else{
                        token = new Token(floatToken, curLineNum());
                        Double floatN = Double.parseDouble(numberLit);
                        token.floatLit = floatN;
                        curLineTokens.add(token);
                    }
                } else if(!floatNumber) {
                    if(numberLit.charAt(0) == '0' && numberLit.length() > 1){
                        scannerError("Not a valid int/long number: " + numberLit);
                    }else{
                        token = new Token(integerToken, curLineNum());
                        long longN = Long.parseLong(numberLit);
                        token.integerLit = longN;
                        curLineTokens.add(token);
                    }
                }
            } else if (c == '"' || c == '\'') {
                String stringLit = "";
                boolean stringTermin = false;

                for (int nextPos = pos+1; nextPos < line.length(); nextPos++) {
                    char nextC = line.charAt(nextPos);
                    if (nextC == '\"' || nextC == '\'') {
                        stringTermin = true;
                        break;
                    } else if (nextC != '\"' || nextC != '\'') {
                        stringLit = stringLit + nextC;
                        pos = nextPos;
                    }
                }


                if (!stringTermin) {
                    scannerError("String is not terminated");
                }

                token = new Token(stringToken, curLineNum());
                token.stringLit = stringLit;
                curLineTokens.add(token);
                pos++;
            } else if(!isLetterAZ(c) && c != ' '){
                int nextPos = pos + 1;

                tokenkind = checkTokenExists("" + c);

                if(line.length() > nextPos){
                    char nextC = line.charAt(nextPos);
                    tokenkind2 = checkTokenExists("" + c + nextC);

                    if(tokenkind != null  && tokenkind2 == null){
                        token = new Token(tokenkind, curLineNum());
                        curLineTokens.add(token);
                    }else if(tokenkind2 != null){
                        token = new Token(tokenkind2, curLineNum());
                        curLineTokens.add(token);
                        pos = nextPos;
                    }else{
                        scannerError("This is not a valid token: " + c);
                    }
                }else{
                    token = new Token(tokenkind, curLineNum());
                    curLineTokens.add(token);
                }

            }

            pos++;
        }

        // Terminate line:
        curLineTokens.add(new Token(newLineToken,curLineNum()));

        for (Token t: curLineTokens)
        Main.log.noteToken(t);
    }

    public int curLineNum() {
        return sourceFile!=null ? sourceFile.getLineNumber() : 0;
    }

    private int findIndent(String s) {
        int indent = 0;

        while (indent<s.length() && s.charAt(indent)==' ') indent++;
        return indent;
    }

    private String expandLeadingTabs(String s) {
        String newS = "";
        for (int i = 0;  i < s.length();  i++) {
            char c = s.charAt(i);
            if (c == '\t') {
                do {
                    newS += " ";
                } while (newS.length()%TABDIST > 0);
            } else if (c == ' ') {
                newS += " ";
            } else {
                newS += s.substring(i);
                break;
            }
        }
        return newS;
    }


    private boolean isLetterAZ(char c) {
        return ('A'<=c && c<='Z') || ('a'<=c && c<='z') || (c=='_');
    }


    private boolean isDigit(char c) {
        return '0'<=c && c<='9';
    }


    public boolean isCompOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return k == lessToken || k == greaterToken || k == doubleEqualToken
                || k == greaterEqualToken || k == lessEqualToken || k == notEqualToken;
    }


    public boolean isFactorPrefix() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return k == plusToken || k == minusToken;
    }


    public boolean isFactorOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return k == astToken || k == slashToken || k == percentToken
                || k == doubleSlashToken;
    }


    public boolean isTermOpr() {
        TokenKind k = curToken().kind;
        //-- Must be changed in part 2:
        return k == plusToken || k == minusToken;
    }


     public boolean anyEqualToken() {
         for (Token t: curLineTokens) {
             if (t.kind == equalToken) return true;
             if (t.kind == semicolonToken) return false;
         }
         return false;
     }

     public TokenKind checkTokenExists(String tokenStr){
         for(TokenKind tokenkind: TokenKind.values){
             if(tokenStr.equals(tokenkind.toString())){
                 return tokenkind;
             }
         }
         return null;
         /*
         private TokenKind checkOperator(String tokenStr){
         if (tokenStr == "and") {
         return andToken;
     }else if (tokenStr == "def") {
     return defToken;
    }else if (tokenStr == "elif") {
    return elifToken;
    }else if (tokenStr == "else") {
    return elseToken;
    }else if (tokenStr == "False") {
    return falseToken;
    }else if (tokenStr == "for") {
    return forToken;
    }else if (tokenStr == "if") {
    return ifToken;
    }else if (tokenStr == "in") {
    return inToken;
    }else if (tokenStr == "None") {
    return noneToken;
    }else if (tokenStr == "not") {
    return notToken;
    }else if (tokenStr == "or") {
    return orToken;
    }else if (tokenStr == "pass") {
    return passToken;
    }else if (tokenStr == "return") {
    return returnToken;
    }else if (tokenStr == "True") {
    return trueToken;
    }else if (tokenStr == "while") {
    return whileToken;
    }else{
    return null;
    }
    }
    */
     }
   }
