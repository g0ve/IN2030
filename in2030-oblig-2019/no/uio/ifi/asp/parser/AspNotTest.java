package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

import java.util.ArrayList;

public class AspNotTest extends AspSyntax{
  AspNotTest(int n){
    super(n);
  }

  AspComparison comparison;
  boolean isNot = false;

  public static AspNotTest parse(Scanner s){
    enterParser("not test");
    AspNotTest ant = new AspNotTest(s.curLineNum());

    if(s.curToken().kind == notToken){
      ant.isNot = true;
      skip(s, notToken);
    }
    ant.comparison = AspComparison.parse(s);

    leaveParser("not test");
    return ant;
  }

  @Override
  public void prettyPrint(){
    if(isNot){
      prettyWrite(" not ");
    }
    comparison.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    return null;
  }
}
