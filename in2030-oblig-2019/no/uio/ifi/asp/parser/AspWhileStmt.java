package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspWhileStmt extends AspCompoundStmt{
  AspWhileStmt(int n){
    super(n);
  }

  AspExpr ae;
  AspSuite as;

  public static AspWhileStmt parse(Scanner s){
    enterParser("while stmt");
    AspWhileStmt aws = new AspWhileStmt(s.curLineNum());
    skip(s, TokenKind.whileToken);
    aws.ae = AspExpr.parse(s);
    skip(s, TokenKind.colonToken);
    aws.as = AspSuite.parse(s);
    leaveParser("while stmt");
    return aws;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("while ");
    ae.prettyPrint();
    prettyWrite(":");
    as.prettyPrint();
  }

  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
      RuntimeValue v  = null;
      boolean aeBool = true;

      aeBool = ae.eval(curScope).getBoolean("whileStmt", this);

      if(aeBool){
          v = as.eval(curScope);
      }

      return v;
  }
}
