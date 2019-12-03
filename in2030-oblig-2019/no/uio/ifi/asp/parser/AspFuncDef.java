package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspFuncDef extends AspCompoundStmt{
  AspFuncDef(int n){
    super(n);
  }
  AspSuite as;
  ArrayList<AspName> anLst = new ArrayList<>();

  public AspSuite getSuite(){
    return as;
  }

  public ArrayList<AspName> getLstName(){
      return anLst;
  }

  public ArrayList<AspName> getAspNameList(){
    return anLst;
  }

  public static AspFuncDef parse(Scanner s){
    enterParser("func def");
    AspFuncDef afd = new AspFuncDef(s.curLineNum());
    skip(s, defToken);
    afd.anLst.add(AspName.parse(s));
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
    anLst.get(0).prettyPrint();
    prettyWrite(" (");

    for(AspName name : anLst){
      if(name != anLst.get(anLst.size()-1)) {
            prettyWrite(", ");
        }
        name.prettyPrint();
    }

    prettyWrite(")");
    prettyWrite(":");
    as.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeFunc rf = new RuntimeFunc(this, curScope, an.token.name);
      trace("def " + an.token.name);

     for (AspName an : anLst) {
         RuntimeValue v = new RuntimeStringValue(an.token.name);
         rf.formalParam.add(v);
     }

     curScope.assign(an.token.name, rf);
     return rf;
  }

  public void runFunction(RuntimeScope curScope) throws RuntimeReturnValue {
    as.eval(curScope);
  }
}
