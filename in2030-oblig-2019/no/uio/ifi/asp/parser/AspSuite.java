package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspSuite extends AspSyntax {

	ArrayList<AspStmt> asLst = new ArrayList<>();
	AspSmallStmtList assl = null;

	AspSuite(int n) {
		super(n);
	}


	public static AspSuite parse(Scanner s) {
		enterParser("suite");

		AspSuite as = new AspSuite(s.curLineNum());

		if(s.curToken().kind == TokenKind.newLineToken){
			skip(s, TokenKind.newLineToken);
			skip(s, TokenKind.indentToken);

			while (s.curToken().kind != TokenKind.dedentToken) {
				as.asLst.add(AspStmt.parse(s));
			}

			skip(s, TokenKind.dedentToken);
		}else{
			as.assl = AspSmallStmtList.parse(s);
		}

		leaveParser("suite");
		return as;
	}


	@Override
	public void prettyPrint() {
		if(assl == null){
			prettyWriteLn();
			prettyIndent();

			for (AspStmt as : asLst) {
				as.prettyPrint();
			}

			prettyDedent();
		}else{
			assl.prettyPrint();
		}
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		RuntimeValue v = null;

		if(assl == null){
			for (AspStmt as : asLst) {
				v = as.eval(curScope);
			}
		}else{
			v = assl.eval(curScope);
		}

		return v;
	}
}
