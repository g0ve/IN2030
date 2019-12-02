package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspExprStmt extends AspSmallStmt{

  AspExprStmt(int n){
    super(n);
  }
  AspExpr ae;

  public static AspExprStmt parse(Scanner s){
    enterParser("expr stmt");
    AspExprStmt aes = new AspExprStmt(s.curLineNum());
    aes.ae = AspExpr.parse(s);
    leaveParser("expr stmt");
    return aes;
  }
  @Override
  public void prettyPrint(){
    ae.prettyPrint();
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      trace("ExprStmt");
      return ae.eval(curScope);
  }
}
