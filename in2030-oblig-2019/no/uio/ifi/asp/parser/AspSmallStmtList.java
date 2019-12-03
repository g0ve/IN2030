package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspSmallStmtList extends AspStmt{

  AspSmallStmtList(int n){
    super(n);
  }

  ArrayList<AspSmallStmt> assLst = new ArrayList<>();
  static int cntSemiColon = 0;

  public static AspSmallStmtList parse(Scanner s){
    enterParser("small stmt list");
    AspSmallStmtList assl = new AspSmallStmtList(s.curLineNum());

	while(s.curToken().kind != TokenKind.newLineToken){
		assl.assLst.add(AspSmallStmt.parse(s));

		if(s.curToken().kind == TokenKind.semicolonToken){
			cntSemiColon++;
			skip(s, TokenKind.semicolonToken);
		}
	}

    skip(s, TokenKind.newLineToken);

    leaveParser("small stmt list");

    return assl;
  }

  @Override
  public void prettyPrint(){
 	// a; b; c;

    for (int i = 0; i < assLst.size(); i++) {
    	assLst.get(i).prettyPrint();
		if(i == assLst.size()-1){
			if(assLst.size() == cntSemiColon){
				prettyWrite("; ");
			}
		}else{
			prettyWrite("; ");
		  }
    }
    prettyWriteLn();

  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      //-- Must be changed in part 3:
      RuntimeValue v = null;

      for (AspSmallStmt ass : assLst) {
          v = ass.eval(curScope);

      }

      return v;
  }
}
