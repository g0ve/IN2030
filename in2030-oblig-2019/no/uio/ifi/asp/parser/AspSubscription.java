package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspSubscription extends AspPrimarySuffix {

	AspExpr ae;

	AspSubscription(int n) {
		super(n);
	}


	public static AspSubscription parse(Scanner s) {
		enterParser("subscription");

		AspSubscription asub = new AspSubscription(s.curLineNum());

		skip(s, TokenKind.leftBracketToken);
		asub.ae = AspExpr.parse(s);
		skip(s, TokenKind.rightBracketToken);

		leaveParser("subscription");
		return asub;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("[");
		ae.prettyPrint();
		prettyWrite("]");
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return ae.eval(curScope);
	}
}