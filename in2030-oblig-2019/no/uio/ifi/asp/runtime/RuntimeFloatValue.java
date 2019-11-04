package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue{
  double f;

  public RuntimeIntValue(int i){
    f = i;
  }

  @Override
  protected String typeName() {
    return "float";
  }

  @Override
  public boolean getFloatValue(String what, AspSyntax where) {
    return f;
  }

  @Override
  public boolean getFloatPositive(String what, AspSyntax where) {
    return new RuntimeIntValue(+f);
  }
  @Override
  public boolean getFloatNegate(String what, AspSyntax where) {
    return new RuntimeIntValue(-f);
  }

  @Override
  public int getBoolValue(String what, AspSyntax where) {
    if (f != 0.0){
      return true;
    }
    return false;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(f == v.getIntValue("== operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(f == v.getFloatValue("== operand", where));
    }
    else if(v instanceof RuntimeNoneValue){
      return new RuntimeBoolValue(false);
    }
    runtimeError("Type error for ==.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(f != v.getIntValue("!= operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(f != v.getFloatValue("!= operand", where));
    }
    runtimeError("Type error for !=.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(f > v.getIntValue("> operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(f > v.getFloatValue("> operand", where));
    }
    runtimeError("Type error for >.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(f >= v.getIntValue(">= operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(f >= v.getFloatValue(">= operand", where));
    }
    runtimeError("Type error for >=.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(f <= v.getIntValue("<= operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(f <= v.getFloatValue("<= operand", where));
    }
    runtimeError("Type error for <=.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(f < v.getIntValue("< operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(f < v.getFloatValue("< operand", where));
    }
    runtimeError("Type error for <.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(f + v.getIntValue("+ operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(f +  v.getFloatValue("+ operand", where));
    }
    runtimeError("Type error for +.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(f - v.getIntValue("- operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(f -  v.getFloatValue("- operand", where));
    }
    runtimeError("Type error for -.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(f * v.getIntValue("* operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(f * v.getFloatValue("* operand", where));
    }
    runtimeError("Type error for *.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(f / v.getIntValue("/ operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(f /  v.getFloatValue("/ operand", where));
    }
    runtimeError("Type error for /.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(Math.floor(f / v.getIntValue("// operand", where)));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeFloatValue(Math.floor(f / v.getFloatValue("// operand", where)));
    }
    runtimeError("Type error for -.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeIntValue(Math.floorMod(f, v.getIntValue("% operand", where)));
    }
    else if(v instanceof RuntimeFloatValue){
      double float_value = v.getFloatValue("% operand", where);
      return new RuntimeFloatValue(f - float_value * Math.floor(f/float_value));
    }
    runtimeError("Type error for %.", where);
    return null;  // Required by the compiler
  }


}
