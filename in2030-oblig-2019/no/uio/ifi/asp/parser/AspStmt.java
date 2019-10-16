package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public abstract class AspStmt extends AspSyntax{

    AspStmt(int n){
        super(n);
    }

    public static AspStmt parse(Scanner s){
        enterParser("stmt");

        TokenKind tokenkind = s.curToken().kind;
        AspStmt as;

        if(tokenkind == TokenKind.nameToken || tokenkind == TokenKind.passToken || tokenkind == TokenKind.returnToken){              //def
            as = AspSmallStmtList.parse(s);
        }
        else if(tokenkind == TokenKind.forToken || tokenkind == TokenKind.ifToken || tokenkind == TokenKind.whileToken || tokenkind == TokenKind.defToken){          //if
            as = AspCompoundStmt.parse(s);
        }
        else{
            parserError("Expected an STMT but found a " + s.curToken().kind.toString(), s.curLineNum());
            return null;
        }

        leaveParser("stmt");
        return as;
    }

    abstract void prettyPrint();
    abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}

