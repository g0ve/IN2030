package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspExpr extends AspSyntax {
    //-- Must be changed in part 2:
    ArrayList<AspAndTest> aatLst = new ArrayList<>();

    AspExpr(int n) {
      super(n);
    }


  public static AspExpr parse(Scanner s) {
	   enterParser("expr");

    	//-- Must be changed in part 2:
    	AspExpr ae = new AspExpr(s.curLineNum());
      boolean run = true;
      while(run){
        ae.aatLst.add(AspAndTest.parse(s));
        if(s.curToken().kind != TokenKind.orToken){
          run = false;
        }
        else {
          skip(s, TokenKind.orToken);
        }
      }

    	leaveParser("expr");
    	return ae;
    }


    @Override
    public void prettyPrint() {
	  //-- Must be changed in part 2:
    int nPrinted = 0;

    for (AspAndTest aat : aatLst) {
      if(nPrinted > 0){
        prettyWrite(" or ");
      }
      aat.prettyPrint();
      ++nPrinted;
      }
    }


    @Override
    public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      //-- Must be changed in part 3:
      RuntimeValue rv = aatLst.get(0).eval(curScope);
      for(int i = 0; i < aatLst.size(); i++){
        if(rv.getBoolValue("expr", this)){
          return rv;
        }
        rv = aatLst.get(i).eval(curScope);
      }
      return rv;
    }
}
