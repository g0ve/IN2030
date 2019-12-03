package no.uio.ifi.asp.parser;

import java.util.ArrayList;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.runtime.*;
import no.uio.ifi.asp.scanner.*;
import static no.uio.ifi.asp.scanner.TokenKind.*;

public class AspForStmt extends AspCompoundStmt{

  AspForStmt(int n){
    super(n);
  }
  AspExpr ae;
  AspSuite as;
  AspName an;

  public static AspForStmt parse(Scanner s){
    enterParser("for stmt");
    AspForStmt afs = new AspForStmt(s.curLineNum());
    skip(s, TokenKind.forToken);

    afs.an = AspName.parse(s);
    skip(s, TokenKind.inToken);

    afs.ae = AspExpr.parse(s);
    skip(s, TokenKind.colonToken);

    afs.as = AspSuite.parse(s);

    leaveParser("for stmt");
    return afs;
  }

  @Override
  public void prettyPrint(){
    prettyWrite("for ");
    an.prettyPrint();
    prettyWrite(" in ");
    ae.prettyPrint();
    prettyWrite(":");
    as.prettyPrint();
  }


  @Override
  public RuntimeValue eval(RuntimeScope curScope) throws RuntimeReturnValue {
    RuntimeValue v = ae.eval(curScope);
    /*
    HVis den er en runtimelist - hent RuntimeListValuegå igjennom hvert element
    assign name til hvert element i curscope og eval suite.

    hvis den ikke er runtimelist: feilmelding
    også returnerer dere runtimevalue som i alle evalene.
    */
    if(v instanceof RuntimeListValue){
      ArrayList<RuntimeValue> aeLst = v.getListValue("For stmt", this);

      for (RuntimeValue rv : aeLst) {
        curScope.assign(an.toString(), rv);
      }

      v = as.eval(curScope);
    }else{
      RuntimeValue.runtimeError("ForStmt - Expr is not a list", this);
    }
    return v;
  }
}
