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

  AspName name;
  AspExpr expr;
  ArrayList<AspSubscription> asLst = new ArrayList<>();

  public static AspAssignment parse(Scanner s){
    enterParser("assignment");
    AspAssignment aas = new AspAssignment(s.curLineNum());

    if(s.curToken().kind != nameToken){
      parserError("No name to assignment", s.curLineNum());
    }

    aas.name = AspName.parse(s);

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
    name.prettyPrint();
    for (AspSubscription as : asLst) {
    	as.prettyPrint();
    }

    prettyWrite(" = ");

    expr.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    //-- Must be changed in part 3:

  //   RuntimeValue v = expr.eval(curScope);
  //   RuntimeValue navn = null;
  //
  //   if(!asLst.isEmpty()){
  //     navn = name.eval(curScope);
  //     for (int i = 0; i < asLst.size() - 1; i++){
  //       navn = navn.evalSubscription(asLst.get(i).eval(curScope), this);
  //     }
  //     RuntimeValue last = asLst.get(asLst.size() - 1).eval(curScope);
  //     navn.evalAssignElem(last, v, this);
  //     return v;
  //   }
  //   else{
  //     curScope.assign(name.token.name, v);
  //     return v;
  //   }
  // }



    RuntimeValue rv = expr.eval(curScope);
    RuntimeValue rvName = null;

    if(asLst.size() > 0){
      rvName = name.eval(curScope);
      for(int i = 0; i < asLst.size() -1; i++){
        rvName = rvName.evalSubscription(asLst.get(i).eval(curScope), this);
      }
      RuntimeValue lastIndex = asLst.get(asLst.size() -1).eval(curScope);
      rvName.evalAssignElem(lastIndex, rv, this);
      return rv;
    }
    else{
      curScope.assign(name.token.name, rv);
      trace(name.token.name + " = " + rv.showInfo());
    }
    return null;
  }
}
