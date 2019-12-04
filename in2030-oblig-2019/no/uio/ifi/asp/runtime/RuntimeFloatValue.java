package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFloatValue extends RuntimeValue{
  double floatValue;

  public RuntimeFloatValue(double i){
    floatValue = i;
  }

  @Override
  protected String typeName() {
    return "float";
  }

  @Override
  public String toString(){
    return "" + floatValue;
  }

  @Override
  public String showInfo(){
    return toString();
  }

  @Override
  public double getFloatValue(String what, AspSyntax where) {
    return floatValue;
  }

  @Override
  public RuntimeValue evalNegate(AspSyntax where){
    return new RuntimeFloatValue(-floatValue);
  }

  @Override
  public RuntimeValue evalPositive(AspSyntax where){
    return new RuntimeFloatValue(+floatValue);
  }

  @Override
  public boolean getBoolValue(String what, AspSyntax where) {
    if (floatValue != 0.0){
      return true;
    }
    return false;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(floatValue == v.getIntValue("==", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue == v.getFloatValue("==", where));
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
      return new RuntimeBoolValue(floatValue != v.getIntValue("!=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue != v.getFloatValue("!=", where));
    }
    runtimeError("'!=' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(floatValue > v.getIntValue(">", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue > v.getFloatValue(">", where));
    }
    runtimeError("'>' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(floatValue >= v.getIntValue(">=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue >= v.getFloatValue(">=", where));
    }
    runtimeError("'>=' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {      
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(floatValue <= v.getIntValue("<=", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue <= v.getFloatValue("<=", where));
    }
    runtimeError("'<=' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(floatValue < v.getIntValue("<", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(floatValue < v.getFloatValue("<", where));
    }
    runtimeError("'<' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(floatValue + v.getIntValue("+", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue +  v.getFloatValue("+", where));
    }
    runtimeError("'+' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(floatValue - v.getIntValue("-", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue -  v.getFloatValue("-", where));
    }
    runtimeError("'-' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(floatValue * v.getIntValue("*", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue * v.getFloatValue("*", where));
    }
    runtimeError("'*' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(floatValue / v.getIntValue("/", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue /  v.getFloatValue("/", where));
    }
    runtimeError("'/' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }

  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeFloatValue(floatValue / v.getIntValue("//", where));

    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(floatValue / v.getFloatValue("//", where));

    }
    runtimeError("'//' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }


  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      long i = v.getIntValue("%", where);
      return new RuntimeFloatValue(floatValue - i * Math.floor(floatValue/i));
    }
    else if(v instanceof RuntimeFloatValue){
      double f = v.getFloatValue("%", where);
      return new RuntimeFloatValue(floatValue - f * Math.floor(floatValue/f));
    }
    runtimeError("'%' undefined for "+typeName()+"!", where);
    return null;  // Required by the compiler
  }


}
