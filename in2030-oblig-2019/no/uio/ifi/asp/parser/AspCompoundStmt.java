package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public abstract class AspCompoundStmt extends AspStmt {

	AspCompoundStmt(int n) {
		super(n);
	}


	public static AspCompoundStmt parse(Scanner s) {
		enterParser("compound stmt");

		AspCompoundStmt acs = null;

		TokenKind tokenkind = s.curToken().kind;

		if(tokenkind == TokenKind.forToken){         //for
	      acs = AspForStmt.parse(s);
	    }
		else if(tokenkind == TokenKind.defToken){              //def
		  acs = AspFuncDef.parse(s);
		}
		else if(tokenkind == TokenKind.ifToken){          //if
	      acs = AspIfStmt.parse(s);
	    }
		else if(tokenkind == TokenKind.whileToken){       //while
		  acs = AspWhileStmt.parse(s);
		}
		else{
			parserError("Expected an COMPOUND STMT but found a " + s.curToken().kind.toString(), s.curLineNum());
		}

		leaveParser("compound stmt");
		return acs;
	}


	abstract void prettyPrint();
	abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
