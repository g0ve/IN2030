package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspDictDisplay extends AspAtom {

	ArrayList<AspStringLit> aslLst = new ArrayList<>();
	ArrayList<AspExpr> aeLst = new ArrayList<>();

	AspDictDisplay(int n) {
		super(n);
	}


	public static AspDictDisplay parse(Scanner s) {
		enterParser("dict display");

		AspDictDisplay add = new AspDictDisplay(s.curLineNum());

		skip(s, TokenKind.leftBraceToken);

		while(s.curToken().kind != TokenKind.rightBraceToken){
			add.aslLst.add(AspStringLit.parse(s));

			skip(s, TokenKind.colonToken);

			add.aeLst.add(AspExpr.parse(s));

			if(s.curToken().kind == TokenKind.commaToken){
				skip(s, TokenKind.commaToken);
			}else{
				break;
			}
		}

		skip(s, TokenKind.rightBraceToken);

		leaveParser("dict display");
		return add;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("{");
		int strLstLenght = aslLst.size();

		if(!aslLst.isEmpty()){
			for (int i = 0; i < strLstLenght; i++) {
				aslLst.get(i).prettyPrint();

				prettyWrite(":");

				aeLst.get(i).prettyPrint();

				if(strLstLenght - 1 != i){
					prettyWrite(", ");
				}else{
					prettyWrite("}");
				}
			}
		}else{
			prettyWrite("}");
		}
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return null;
	}
}
