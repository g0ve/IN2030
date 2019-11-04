package no.uio.ifi.asp.parser;
import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

class AspAndTest extends AspSyntax {
  ArrayList<AspNotTest> antlst = new ArrayList<>();
  AspAndTest(int n) {
    super(n);
  }

  public static AspAndTest parse(Scanner s) {
    enterParser("and test");

    AspAndTest aat = new AspAndTest(s.curLineNum());
    boolean run = true;
    while (run) {
      aat.antlst.add(AspNotTest.parse(s));
      if (s.curToken().kind != TokenKind.andToken){
        run = false;
      }
      else{
        skip(s, TokenKind.andToken);
      }
    }

    leaveParser("and test");
    return aat;
  }

  @Override
  void prettyPrint() {
    int nPrinted = 0;

    for (AspNotTest ant: antlst) {
      if (nPrinted > 0){
        prettyWrite(" and ");
      }
      ant.prettyPrint();  ++nPrinted;
    }
  }
  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = antlst.get(0).eval(curScope);
    for (int i = 1; i < antlst.size(); ++i) {
      if (! v.getBoolValue("and operand",this))
        return v;
      v = antlst.get(i).eval(curScope);
    }
    return v;

  }
}
