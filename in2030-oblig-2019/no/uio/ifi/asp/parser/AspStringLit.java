package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspStringLit extends AspAtom {
	String aspStr;

	AspStringLit(int n) {
		super(n);
	}


	public static AspStringLit parse(Scanner s) {
		enterParser("string literal");

		AspStringLit asl = new AspStringLit(s.curLineNum());

		asl.aspStr = s.curToken().stringLit;

		skip(s, TokenKind.stringToken);

		leaveParser("string literal");
		return asl;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("\"" + aspStr + "\"");
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return new RuntimeStringValue(aspStr);
	}
}