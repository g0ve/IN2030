package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public abstract class AspSmallStmt extends AspSyntax {

	AspSmallStmt(int n) {
		super(n);
	}


	public static AspSmallStmt parse(Scanner s) {
		enterParser("small stmt");

		AspSmallStmt ass = null;
		TokenKind tokenkind = s.curToken().kind;

		if(tokenkind == TokenKind.nameToken){        //name
	      if(s.anyEqualToken()){
	        ass = AspAssignment.parse(s);
	      }
	      else{
	        ass = AspExprStmt.parse(s);
	      }
	    }
		else if(tokenkind == TokenKind.passToken){          //if
	      ass = AspPass.parse(s);
	    }
		else if(tokenkind == TokenKind.returnToken){       //while
		  ass = AspReturn.parse(s);
		}
		else{
			parserError("Expected an SMALL STMT but found a " + s.curToken().kind.toString(), s.curLineNum());
		}

		leaveParser("small stmt");
		return ass;
	}


	abstract void prettyPrint();
	abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}