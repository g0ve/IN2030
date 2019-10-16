package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspDefFunc extends AspCompoundStmt{
  AspDefFunc(int n){
    super(n);
  }
  AspSuite as;
  AspName an;
  ArrayList<AspName> anLst = new ArrayList<>();


  public static AspDefFunc parse(Scanner s){
    enterParser("func def");
    AspDefFunc adf = new AspDefFunc(s.curLineNum());
    //skip(s, TokenKind.defToken);
    skip(s, defToken);
    adf.an = AspName.parse(s);
    skip(s, leftParToken);

    while(s.curToken().kind != rightParToken){
      adf.anLst.add(AspName.parse(s));
      if(s.curToken().kind != commaToken){
        break;
      }
      skip(s, commaToken);
    }

    skip(s, rightParToken);
    skip(s, colonToken);
    adf.as = AspSuite.parse(s);

    leaveParser("func def");
    return adf;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("def ");
    an.prettyPrint();
    prettyWrite(" (");
    for(AspName name : anLst){
      name.prettyPrint();
      if(name != anLst.get(anLst.size()-1)) {
            prettyWrite(", ");
        }
    }
    prettyWrite(")");
    prettyWrite(":");
    as.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
