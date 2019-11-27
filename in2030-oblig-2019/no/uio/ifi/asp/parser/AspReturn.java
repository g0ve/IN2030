package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspReturn extends AspSmallStmt{
  AspReturn(int n){
    super(n);
  }
  AspExpr ae;

  public static AspReturn parse(Scanner s){
    enterParser("return stmt");
    AspReturn aret = new AspReturn(s.curLineNum());

    skip(s, TokenKind.returnToken);
    aret.ae = AspExpr.parse(s);

    leaveParser("return stmt");
    return aret;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("return ");
    ae.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue rv = ae.eval(curScope);
    trace("return" + rv.showInfo());
    throw new RuntimeReturnValue(rv);
  }
}
