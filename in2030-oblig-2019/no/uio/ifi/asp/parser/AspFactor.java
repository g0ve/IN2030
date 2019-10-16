package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;


public class AspFactor extends AspSyntax {

	ArrayList<AspPrimary> apLst = new ArrayList<>();
	ArrayList<AspFactorOpr> afoLst = new ArrayList<>();
	ArrayList<AspFactorPrefix> afpLst = new ArrayList<>();

	AspFactor(int n) {
		super(n);
	}


	public static AspFactor parse(Scanner s) {
		enterParser("factor");

		AspFactor af = new AspFactor(s.curLineNum());

		while (true) {
			if (!s.isFactorPrefix()) {
				af.afpLst.add(null);
			}else{
				af.afpLst.add(AspFactorPrefix.parse(s));
			}

			af.apLst.add(AspPrimary.parse(s));

			if(s.isFactorOpr()){
				af.afoLst.add(AspFactorOpr.parse(s));
			}else{
				break;
			}

		}

		leaveParser("factor");
		return af;
	}


	@Override
	public void prettyPrint() {
		int teller = 0;
		for (AspPrimary ap : apLst) {
			AspFactorPrefix afp = afpLst.get(teller);
			if(afp != null){
				afp.prettyPrint();
			}

			ap.prettyPrint();

			if(teller < afoLst.size()){
				if(afoLst.get(teller) != null){
					AspFactorOpr afo = afoLst.get(teller);
					afo.prettyPrint();
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
