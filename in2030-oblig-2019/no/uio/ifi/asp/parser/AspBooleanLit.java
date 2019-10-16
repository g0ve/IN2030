package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspBooleanLit extends AspAtom {
	boolean aspBoolean;

	AspBooleanLit(int n) {
		super(n);
	}


	public static AspBooleanLit parse(Scanner s) {
		enterParser("boolean literal");

		AspBooleanLit abl = new AspBooleanLit(s.curLineNum());

		TokenKind true_or_false = s.curToken().kind;
		if(true_or_false == TokenKind.falseToken){
			abl.aspBoolean = false;
		}else{
			abl.aspBoolean = true;
		}
		skip(s, true_or_false);

		leaveParser("boolean literal");
		return abl;
	}


	@Override
	public void prettyPrint() {
		if(aspBoolean){
			prettyWrite("True");
		}else{
			prettyWrite("False");
		}
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return null;
	}
}
