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
		RuntimeValue v = null;
		if(afpLst.get(0) != null){
			TokenKind t = afpLst.get(0).token.kind;
			if(t == TokenKind.minusToken){
				v = apLst.get(0).eval(curScope).evalNegate(this);
			}
			else if(t == TokenKind.plusToken){
				v = apLst.get(0).eval(curScope).evalPositive(this);
			}
			else{
				Main.panic("Do not understand: " + t);
			}
		}
		else{
			v = apLst.get(0).eval(curScope);
		}
		if(!afoLst.isEmpty()){
			for (int i = 1; i < apLst.size(); i++){
				TokenKind to = afoLst.get(i-1).token.kind;
				if(to == TokenKind.slashToken){
					v = v.evalDivide(apLst.get(i).eval(curScope), this);
				}
				else if(to == TokenKind.doubleSlashToken){
					v = v.evalIntDivide(apLst.get(i).eval(curScope), this);
				}
				else if(to == TokenKind.astToken){
					v = v.evalMultiply(apLst.get(i).eval(curScope), this);
				}
				else if(to == TokenKind.percentToken){
					v = v.evalModulo(apLst.get(i).eval(curScope), this);
					// System.out.print(v.toString());
				}
				else{
					Main.panic("Do not understand: " + to);
				}
			}
		}
		return v;
	}
}
