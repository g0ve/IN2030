package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspName extends AspAtom {
	Token token;

    public AspName(int n) {
		super(n);
    }


	public static AspName parse(Scanner s) {
		enterParser("name");

		AspName aName = new AspName(s.curLineNum());
		if(s.curToken().kind != TokenKind.nameToken){
			parserError("Expected a NAME but found a " + s.curToken().kind, s.curLineNum());
		}

		aName.token = s.curToken();
		skip(s, TokenKind.nameToken);

		leaveParser("name");
		return aName;
	}


	@Override
	public void prettyPrint() {
		prettyWrite("" + token.name);
	}


	@Override
	public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
		//-- Must be changed in part 3:
		return null;
	}
}
