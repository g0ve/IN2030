package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspPass extends AspSmallStmt{

  AspPass(int n){
    super(n);
  }

  public static AspPass parse(Scanner s){
    enterParser("pass stmt");
    AspPass apass = new AspPass(s.curLineNum());
    skip(s, TokenKind.passToken);

    leaveParser("pass stmt");
    return apass;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("pass");
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
