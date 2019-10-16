package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspListDisplay extends AspAtom {

	ArrayList<AspExpr> aeLst = new ArrayList<>();

	AspListDisplay(int n) {
		super(n);
	}


	public static AspListDisplay parse(Scanner s) {
		enterParser("list display");

		AspListDisplay ald = new AspListDisplay(s.curLineNum());
		int cntComma = 0;

		skip(s, TokenKind.leftBracketToken);

		while(s.curToken().kind != TokenKind.rightBracketToken){
			ald.aeLst.add(AspExpr.parse(s));

			if(s.curToken().kind == TokenKind.commaToken){
				skip(s, TokenKind.commaToken);
				cntComma++;
			}else{
				break;
			}
		}

		skip(s, TokenKind.rightBracketToken);

		if(cntComma + 1 != ald.aeLst.size()){
			parserError("Too many commas, not allowed" + cntComma +" "+ald.aeLst.size(), s.curLineNum());
		}

		leaveParser("list display");
		return ald;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("[");
		int lenght = aeLst.size();

		if(aeLst.size() != 0){
			for (int i = 0; i < lenght-1; i++) {
				aeLst.get(i).prettyPrint();
				prettyWrite(", ");
			}
			aeLst.get(lenght-1).prettyPrint();
		}
		prettyWrite("]");
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return null;
	}
}
