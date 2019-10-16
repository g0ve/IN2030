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
    ArrayList<AspSuite> suites = new ArrayList<>();
    ArrayList<AspExpr> exprs = new ArrayList<>();

  public static AspIfStmt parse(Scanner s){
    enterParser("if stmt");

    AspIfStmt ais = new AspIfStmt(s.curLineNum());
    skip(s, TokenKind.ifToken);

    boolean run = true;
    while(run){
      ais.exprs.add(AspExpr.parse(s));
      skip(s, TokenKind.colonToken);
      ais.suites.add(AspSuite.parse(s));
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
      ais.suites.add(AspSuite.parse(s));
    }
    leaveParser("if stmt");
    return ais;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("if ");
    exprs.get(0).prettyPrint();
    prettyWrite(": ");
    suites.get(0).prettyPrint();

    if(exprs.size() > 1){
      for(int i = 1; i <exprs.size(); i++){
        prettyWrite("elif ");
        exprs.get(i).prettyPrint();
        prettyWrite(": ");
        suites.get(i).prettyPrint();
      }
    }
    if(suites.size() > exprs.size()){
      prettyWrite("else");
      prettyWrite(": ");

      suites.get(suites.size()-1).prettyPrint();
    }
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
