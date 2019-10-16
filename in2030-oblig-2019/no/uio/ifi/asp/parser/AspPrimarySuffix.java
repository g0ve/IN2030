package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public abstract class AspPrimarySuffix extends AspSyntax {

	AspPrimarySuffix(int n) {
		super(n);
	}


	public static AspPrimarySuffix parse(Scanner s) {
		enterParser("primary suffix");

		AspPrimarySuffix aps = null;
		TokenKind tokenkind = s.curToken().kind;

		if (tokenkind == TokenKind.leftBracketToken) {
			aps = AspSubscription.parse(s);
		}
		else if(tokenkind == TokenKind.leftParToken){
			aps = AspArguments.parse(s);
		}
		else{
			parserError("Expected an PRIMARY SUFFIX but found a " + s.curToken().kind.toString(), s.curLineNum());
		}
		leaveParser("primary suffix");
		return aps;
	}


	abstract void prettyPrint();
	abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}