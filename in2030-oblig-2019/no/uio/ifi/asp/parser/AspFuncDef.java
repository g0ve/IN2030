package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspFuncDef extends AspCompoundStmt{
    AspName an;
    AspSuite as;
    ArrayList<AspName> anLst = new ArrayList<>();

    AspFuncDef(int n){
        super(n);
    }

  public static AspFuncDef parse(Scanner s){
    enterParser("func def");

    AspFuncDef afd = new AspFuncDef(s.curLineNum());

    skip(s, defToken);
    afd.an = AspName.parse(s);
    skip(s, leftParToken);

    while(s.curToken().kind != rightParToken){
      afd.anLst.add(AspName.parse(s));

      if(s.curToken().kind != commaToken){
        break;
      }
      skip(s, commaToken);
    }

    skip(s, rightParToken);
    skip(s, colonToken);
    afd.as = AspSuite.parse(s);

    leaveParser("func def");
    return afd;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("def ");
    an.prettyPrint();
    prettyWrite(" (");

    int teller = 0;
    for(AspName name : anLst){
        if(teller > 0) {
            prettyWrite(", ");
        }

        name.prettyPrint();
        ++teller;
    }

    prettyWrite(")");
    prettyWrite(":");
    as.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeFunc rf = new RuntimeFunc(this, curScope, an.getTokenName());
      trace("def " + an.getTokenName());

     for (AspName an : anLst) {
         RuntimeValue v = new RuntimeStringValue(an.getTokenName());
         rf.formalParam.add(v);
     }

     curScope.assign(an.getTokenName(), rf);
     return rf;
  }

  public void runFunction(RuntimeScope curScope) throws RuntimeReturnValue {
    as.eval(curScope);
  }
}
