package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public abstract class AspAtom extends AspSyntax {

	AspAtom(int n) {
		super(n);
	}


	public static AspAtom parse(Scanner s) {
		enterParser("atom");

		AspAtom aa = null;
		TokenKind tokenkind = s.curToken().kind;

		if(tokenkind == TokenKind.leftParToken){
			aa = AspInnerExpr.parse(s);
		}else if (tokenkind == TokenKind.leftBracketToken) {
			aa = AspListDisplay.parse(s);
		}else if (tokenkind == TokenKind.leftBraceToken) {
			aa = AspDictDisplay.parse(s);
		}else if (tokenkind == TokenKind.integerToken) {
			aa = AspIntegerLit.parse(s);
		}else if (tokenkind == TokenKind.floatToken) {
			aa = AspFloatLit.parse(s);
		}else if (tokenkind == TokenKind.stringToken) {
			aa = AspStringLit.parse(s);
		}else if (tokenkind == TokenKind.falseToken || tokenkind == TokenKind.trueToken) {
			aa = AspBooleanLit.parse(s);
		}else if (tokenkind == TokenKind.noneToken) {
			aa = AspNoneLit.parse(s);
		}else if (tokenkind == TokenKind.nameToken) {
			aa = AspName.parse(s);
		}else{
			parserError("Expected an ATOM but found a " + s.curToken().kind.toString(), s.curLineNum());
		}


		leaveParser("atom");
		return aa;
	}


	abstract void prettyPrint();
	abstract RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue;
}
