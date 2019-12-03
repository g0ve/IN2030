package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue{
  long intValue;

  public RuntimeIntValue(long i){
    intValue = i;
  }

  @Override
  protected String typeName() {
    return "int";
  }

  @Override
  public String toString(){
    return "" +intValue;
  }

  @Override
  public String showInfo(){
    return toString();
  }

  @Override
  public long getIntValue(String what, AspSyntax where) {
    //System.out.println("hjer nåp");
    return intValue;
  }


  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if (intValue != 0){
      return true;
    }
    return false;
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where){
    return new RuntimeIntValue(-intValue);
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where){
    return new RuntimeIntValue(+intValue);
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(intValue == v.getIntValue("==", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue == v.getFloatValue("==", where));
    }
    else if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(false);
    }
    runtimeError("'==' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(intValue != v.getIntValue("!=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue != v.getFloatValue("!=", where));
    }
    runtimeError("'!=' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(intValue > v.getIntValue(">", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue > v.getFloatValue(">", where));
    }
    runtimeError("'>' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(intValue >= v.getIntValue(">=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue >= v.getFloatValue(">=", where));
    }
    runtimeError("'>=' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(intValue <= v.getIntValue("<=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue <= v.getFloatValue("<=", where));
    }
    runtimeError("'<=' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(intValue < v.getIntValue("<", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(intValue < v.getFloatValue("<", where));
    }
    runtimeError("'<' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(intValue + v.getIntValue("+", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue +  v.getFloatValue("+", where));
    }
    runtimeError("'+' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(intValue - v.getIntValue("-", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue -  v.getFloatValue("-", where));
    }
    runtimeError("'-' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(intValue * v.getIntValue("*", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue * v.getFloatValue("*", where));
    }
    runtimeError("'*' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(intValue / v.getIntValue("/", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(intValue /  v.getFloatValue("/", where));
    }
    runtimeError("'/' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(Math.floor(intValue / v.getIntValue("//", where)));

    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(Math.floor(intValue / v.getFloatValue("//", where)));

    }
    runtimeError("'//' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(Math.floorMod(intValue, v.getIntValue("%", where)));
    }
    else if(v instanceof RuntimeFloatValue){
      double float_value = v.getFloatValue("%", where);
      return new RuntimeFloatValue(intValue - float_value * Math.floor(intValue/float_value));

    }
    runtimeError("'%' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }


}
