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
		return null;
	}
}
