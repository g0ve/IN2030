package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspAssignment extends AspSmallStmt{
  AspAssignment(int n){
    super(n);
  }

  AspName an;
  AspExpr expr;
  ArrayList<AspSubscription> asLst = new ArrayList<>();

  public static AspAssignment parse(Scanner s){
    enterParser("assignment");
    AspAssignment aas = new AspAssignment(s.curLineNum());

    if(s.curToken().kind != nameToken){
      parserError("No name to assignment", s.curLineNum());
    }

    aas.an = AspName.parse(s);

    while(s.curToken().kind == leftBracketToken){
	  aas.asLst.add(AspSubscription.parse(s));

    }

    skip(s, equalToken);

    aas.expr = AspExpr.parse(s);

    leaveParser("assignment");

    return aas;
  }

  @Override
  public void prettyPrint(){
    an.prettyPrint();
    for (AspSubscription as : asLst) {
    	as.prettyPrint();
    }

    prettyWrite(" = ");

    expr.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    //-- Must be changed in part 3:


    RuntimeValue exprValue = expr.eval(curScope);

    if(asLst.size() > 0){
        RuntimeValue v = an.eval(curScope);
      int lenght = asLst.size();

      for (int i = 0; i < lenght-1; i++) {
          RuntimeValue v2 = asLst.get(i).eval(curScope);
          v = v.evalSubscription(v2, this);
      }

      AspSubscription lastSub = asLst.get(lenght-1);
      RuntimeValue lastIndex = lastSub.eval(curScope);
      trace(an.token.name +"[" + lastIndex + "] = " + exprValue);
      v.evalAssignElem(lastIndex, exprValue, this);

    }
    else{
        String id = an.getTokenName();
        curScope.assign(id, exprValue);
        trace(id + " = " + exprValue.toString());
    }
    return null;
  }
}
