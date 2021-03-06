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

		RuntimeValue v = aa.eval(curScope);
		ArrayList<RuntimeValue> args;

		for(int i = 0; i<apsLst.size(); i++){
			if(apsLst.get(i) instanceof AspSubscription){
				RuntimeValue v2 = apsLst.get(i).eval(curScope);
				v = v.evalSubscription(v2, this);
			}
			else if(apsLst.get(i) instanceof AspArguments){
				args = apsLst.get(i).eval(curScope).getListValue("arguments", this);
				trace("Call " + v + " with params " + args);
				v = v.evalFuncCall(args,this);
			}
		}
		return v;
	}

}
