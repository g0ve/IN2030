package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspIntegerLit extends AspAtom {
	Long aspInt;

	AspIntegerLit(int n) {
		super(n);
	}


	public static AspIntegerLit parse(Scanner s) {
		enterParser("integer literal");

		AspIntegerLit ail = new AspIntegerLit(s.curLineNum());

		if(s.curToken().kind != TokenKind.integerToken){
			parserError("Expected an INTEGER but found a " + s.curToken().kind, s.curLineNum());
		}

		ail.aspInt = s.curToken().integerLit;

		skip(s, TokenKind.integerToken);

		leaveParser("integer literal");
		return ail;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("" + aspInt);
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return null;
	}
}
