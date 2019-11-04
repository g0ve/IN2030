package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspFloatLit extends AspAtom {
	double aspFloat;

	AspFloatLit(int n) {
		super(n);
	}


	public static AspFloatLit parse(Scanner s) {
		enterParser("float literal");

		AspFloatLit afl = new AspFloatLit(s.curLineNum());

		if(s.curToken().kind != TokenKind.integerToken){
			parserError("Expected a FLOAT but found a " + s.curToken().kind, s.curLineNum());
		}

		afl.aspFloat = s.curToken().floatLit;

		skip(s, TokenKind.floatToken);

		leaveParser("float literal");
		return afl;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("" + aspFloat);
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return new RuntimeFloatValue(aspFloat);
	}
}
