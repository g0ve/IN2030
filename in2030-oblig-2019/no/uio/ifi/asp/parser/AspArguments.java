package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspArguments extends AspPrimarySuffix {

	ArrayList<AspExpr> aeLst = new ArrayList<>();

	AspArguments(int n) {
		super(n);
	}


	public static AspArguments parse(Scanner s) {
		enterParser("arguments");

		AspArguments aarg = new AspArguments(s.curLineNum());

		skip(s, TokenKind.leftParToken);

		while(s.curToken().kind != TokenKind.rightParToken){
			aarg.aeLst.add(AspExpr.parse(s));

			if(s.curToken().kind == TokenKind.commaToken){
				skip(s, TokenKind.commaToken);
			}else{
				break;
			}
		}

		skip(s, TokenKind.rightParToken);

		leaveParser("arguments");
		return aarg;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("(");

		int lenght = aeLst.size();

		for (int i = 0; i < lenght-1; i++) {
			aeLst.get(i).prettyPrint();
			prettyWrite(", ");
		}

		if(!aeLst.isEmpty()){
			aeLst.get(aeLst.size()-1).prettyPrint();
		}
		prettyWrite(")");
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		ArrayList<RuntimeValue> lst = new ArrayList<>();
		for (AspExpr ae : aeLst) {
			lst.add(ae.eval(curScope));
		}

		trace("Arguments Call function")
		return new RuntimeListValue(lst);
	}
}
