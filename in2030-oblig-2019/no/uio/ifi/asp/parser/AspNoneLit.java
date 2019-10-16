package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspNoneLit extends AspAtom {

	AspNoneLit(int n) {
		super(n);
	}


	public static AspNoneLit parse(Scanner s) {
		enterParser("none literal");

		AspNoneLit an = new AspNoneLit(s.curLineNum());
		skip(s, TokenKind.noneToken);

		leaveParser("none literal");
		return an;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("none");
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return null;
	}
}

