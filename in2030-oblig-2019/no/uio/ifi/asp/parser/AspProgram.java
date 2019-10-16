package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspProgram extends AspSyntax {
    ArrayList<AspStmt> asLst = new ArrayList<>();

    AspProgram(int n) {
      super(n);
    }


    public static AspProgram parse(Scanner s) {
      enterParser("program");

    	AspProgram ap = new AspProgram(s.curLineNum());
    	while (s.curToken().kind != eofToken) {
        ap.asLst.add(AspStmt.parse(s));
    	}
    	leaveParser("program");
    	return ap;
    }


    @Override
    public void prettyPrint() {
      for(AspStmt s : asLst){
        s.prettyPrint();
        Main.log.finish();
      }
      prettyWrite(TokenKind.eofToken.toString());
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      //-- Must be changed in part 4:
	    return null;
    }
}
