package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeIntValue extends RuntimeValue{
  int integer;

  public RuntimeIntValue(int i){
    integer = i;
  }

  @Override
  protected String typeName() {
    return "int";
  }

  @Override
  public boolean getIntValue(String what, AspSyntax where) {
    return integer;
  }

  @Override
  public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer == v.getIntValue("== operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer == v.getFloatValue("== operand", where));
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
      return new RuntimeBoolValue(integer != v.getIntValue("!= operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer != v.getFloatValue("!= operand", where));
    }
    runtimeError("Type error for !=.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer > v.getIntValue("> operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer > v.getFloatValue("> operand", where));
    }
    runtimeError("Type error for >.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer >= v.getIntValue(">= operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer >= v.getFloatValue(">= operand", where));
    }
    runtimeError("Type error for >=.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer <= v.getIntValue("<= operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer <= v.getFloatValue("<= operand", where));
    }
    runtimeError("Type error for <=.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalLess(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer < v.getIntValue("< operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer < v.getFloatValue("< operand", where));
    }
    runtimeError("Type error for <.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer + v.getIntValue("+ operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer +  v.getFloatValue("+ operand", where));
    }
    runtimeError("Type error for +.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalSubtract(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer - v.getIntValue("- operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer -  v.getFloatValue("- operand", where));
    }
    runtimeError("Type error for -.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer * v.getIntValue("* operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer * v.getFloatValue("* operand", where));
    }
    runtimeError("Type error for *.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer / v.getIntValue("/ operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer /  v.getFloatValue("/ operand", where));
    }
    runtimeError("Type error for /.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalIntDivide(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(Math.floor(integer / v.getIntValue("// operand", where)));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(Math.floor(integer / v.getFloatValue("// operand", where)));
    }
    runtimeError("Type error for -.", where);
    return null;  // Required by the compiler
  }

  @Override
  public RuntimeValue evalModulo(RuntimeValue v, AspSyntax where) {
    if (v instanceof RuntimeIntValue) {
      return new RuntimeBoolValue(integer % v.getIntValue("/ operand", where));
    }
    else if(v instanceof RuntimeFloatValue){
      return new RuntimeBoolValue(integer /  v.getFloatValue("/ operand", where));
    }
    runtimeError("Type error for /.", where);
    return null;  // Required by the compiler
  }
}