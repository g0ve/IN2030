package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspIfStmt extends AspCompoundStmt{
  AspIfStmt(int n){
    super(n);
  }
    ArrayList<AspSuite> asLst = new ArrayList<>();
    ArrayList<AspExpr> aeLst = new ArrayList<>();

  public static AspIfStmt parse(Scanner s){
    enterParser("if stmt");

    AspIfStmt ais = new AspIfStmt(s.curLineNum());
    skip(s, TokenKind.ifToken);

    boolean run = true;
    while(run){
      ais.aeLst.add(AspExpr.parse(s));
      skip(s, TokenKind.colonToken);
      ais.asLst.add(AspSuite.parse(s));
      if(s.curToken().kind != elifToken){
        run = false;
      }
      else{
        skip(s, TokenKind.elifToken);
      }
    }
    if(s.curToken().kind == elseToken){
      skip(s, TokenKind.elseToken);
      skip(s, TokenKind.colonToken);
      ais.asLst.add(AspSuite.parse(s));
    }
    leaveParser("if stmt");
    return ais;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("if ");
    aeLst.get(0).prettyPrint();
    prettyWrite(": ");
    asLst.get(0).prettyPrint();

    if(aeLst.size() > 1){
      for(int i = 1; i <aeLst.size(); i++){
        prettyWrite("elif ");
        aeLst.get(i).prettyPrint();
        prettyWrite(": ");
        asLst.get(i).prettyPrint();
      }
    }
    if(asLst.size() > aeLst.size()){
      prettyWrite("else");
      prettyWrite(": ");

      asLst.get(asLst.size()-1).prettyPrint();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
     RuntimeValue v = null;

     trace("if");
     for(int i = 0; i < aeLst.size(); i++){ {
         AspExpr ae = aeLst.get(i);
         v = ae.eval(curScope);

         if (v.getBoolValue("ifStmt", this)) {
             AspSuite as = asLst.get(i);

             return v;
         }
     }


    if(asLst.size() > aeLst.size()){
        trace("else");
        return asLst.get(asLst.size()-1).eval(curScope);
    }

    return null;
  }
}
