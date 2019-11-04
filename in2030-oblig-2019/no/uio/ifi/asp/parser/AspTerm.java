package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspTerm extends AspSyntax {

	ArrayList<AspFactor> afLst = new ArrayList<>();
	ArrayList<AspTermOpr> atoLst = new ArrayList<>();

	AspTerm(int n) {
		super(n);
	}


	public static AspTerm parse(Scanner s) {
		enterParser("term");

		AspTerm at = new AspTerm(s.curLineNum());

		while (true) {
			at.afLst.add(AspFactor.parse(s));

			if(s.isTermOpr()){
				at.atoLst.add(AspTermOpr.parse(s));
			}else{
				break;
			}
		}

		leaveParser("term");
		return at;
	}


	@Override
	public void prettyPrint() {
		int teller = 0;
		for (AspFactor af : afLst) {

			af.prettyPrint();
			if(teller < atoLst.size()){
				if(atoLst.get(teller) != null){
					AspTermOpr ato = atoLst.get(teller);
					ato.prettyPrint();
				}
			}

			teller++;
		}
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		RuntimeValue v = afLst.get(0).eval(curScope);
		for (int i = 1; i < afLst.size(); i++){
			TokenKind t = atoLst.get(i-1).token.kind;
			if(t == TokenKind.minusToken){
			v = v.evalSubtract(afLst.get(i).eval(curScope), this);
			break;
			}
			else if (t == TokenKind.plusToken){
				v = v.evalAdd(afLst.get(i).eval(curScope), this);
				break;
			}
			else{
				Main.panic("Do not find term: " + t);
			}
		}
		return v;
	}
}
