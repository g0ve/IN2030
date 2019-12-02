package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspPrimary extends AspSyntax {

	ArrayList<AspPrimarySuffix> apsLst = new ArrayList<>();
	AspAtom aa = null;

	AspPrimary(int n) {
		super(n);
	}


	public static AspPrimary parse(Scanner s) {
		enterParser("primary");

		AspPrimary ap = new AspPrimary(s.curLineNum());
		ap.aa = AspAtom.parse(s);

		while(s.curToken().kind == TokenKind.leftParToken || s.curToken().kind == TokenKind.leftBracketToken){
			ap.apsLst.add(AspPrimarySuffix.parse(s));

		}

		leaveParser("primary");
		return ap;
	}


	@Override
	public void prettyPrint() {
		aa.prettyPrint();
		for (AspPrimarySuffix aps : apsLst) {
			aps.prettyPrint();
		}
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		trace("Primary");
		RuntimeValue v = aa.eval(curScope);
		RuntimeValue apsV = null;

		for (AspPrimarySuffix aps : apsLst) {
			if(aps instanceof AspSubscription){
				apsV = aps.eval(curScope);
				v = v.evalSubscription(apsV, this);
			}

			if (aps instanceof AspArguments) {
				apsV = aps.eval(curScope);
				v = v.evalAssignElem(apsV, this);
			}
		}
		return v;
	}
}
