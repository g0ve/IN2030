package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspComparison extends AspSyntax {

	ArrayList<AspTerm> atLst = new ArrayList<>();
	ArrayList<AspCompOpr> acoLst = new ArrayList<>();

	AspComparison(int n) {
		super(n);
	}


	public static AspComparison parse(Scanner s) {
		enterParser("comparison");

		AspComparison ac = new AspComparison(s.curLineNum());

		while (true) {
			ac.atLst.add(AspTerm.parse(s));

			if(s.isCompOpr()){
				ac.acoLst.add(AspCompOpr.parse(s));
			}else{
				break;
			}
		}

		leaveParser("comparison");
		return ac;
	}


	@Override
	public void prettyPrint() {
		int teller = 0;
		for (AspTerm at : atLst) {

			at.prettyPrint();

			if(teller < acoLst.size()){
				if(acoLst.get(teller) != null){
					AspCompOpr aco = acoLst.get(teller);
					aco.prettyPrint();
				}
			}
			teller++;
		}
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:

		RuntimeValue v = atLst.get(0).eval(curScope);
		for (int i = 1; i < atLst.size(); i++){
			v = atLst.get(i-1).eval(curScope);
			TokenKind t = acoLst.get(i-1).token.kind;
			if(t == TokenKind.lessToken){
				v = v.evalLess(atLst.get(i).eval(curScope), this);
			}
			else if(t == TokenKind.greaterToken){
				v = v.evalGreater(atLst.get(i).eval(curScope), this);
			}
			else if(t == TokenKind.doubleEqualToken){
				v = v.evalEqual(atLst.get(i).eval(curScope), this);
			}
			else if(t == TokenKind.greaterEqualToken){
				v = v.evalGreaterEqual(atLst.get(i).eval(curScope), this);
			}
			else if(t == TokenKind.lessEqualToken){
				v = v.evalLess(atLst.get(i).eval(curScope), this);
			}
			else if(t == TokenKind.notEqualToken){
				v = v.evalNotEqual(atLst.get(i).eval(curScope), this);
			}
			else{
				Main.panic("Do not find comparison operator: " + t);
			}
			if(!v.getBoolValue("comparison", this)){
				return v;
			}
		}
		return v;
	}
}
