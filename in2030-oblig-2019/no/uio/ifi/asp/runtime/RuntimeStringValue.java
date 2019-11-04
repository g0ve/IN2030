package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;


public class RuntimeStringValue extends RuntimeValue {

	String str;
	boolean boolValue;

	public RuntimeStringValue(String v) {
		str = v;

		if(str.equals("")){
			boolValue = false;
		}else{
			boolValue = true;
		}
	}


	@Override
	protected String typeName() {
		return "string";
	}

	@Override
    public String showInfo(){
        if (str.charAt(0) == '\"') {
        	return "\"" + str + "\"";
        }else {
			return "\'" + str + "\'";
		}

    }

	@Override
	public String toString() {
		return str;
	}


	@Override
	public boolean getBoolValue(String what, AspSyntax where) {
		return boolValue;
	}

	@Override
	public RuntimeValue evalLen(AspSyntax where){
		return new RuntimeIntValue(str.length());
	}

	@Override
	public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
		if(v instanceof RuntimeStringValue){
			return new RuntimeStringValue(str + v);
		}

		runtimeError("Type error for +", where);
		return null;
	}

	@Override
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue) {
			long count = v.getIntValue("*", where);
			String tmp = "";

			for (long i = 0; i < count; i++) {
				tmp = tmp + str;
			}
			return new RuntimeStringValue(tmp);
		}

		runtimeError("Type error for *", where);
		return null;
	}

	@Override
	public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String str2 = v.getStringValue("==", where);

			return new RuntimeBoolValue(str.equals(str2));
		}

		runtimeError("Type error for ==", where);
		return null;
	}

	@Override
	public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String str2 = v.getStringValue("!=", where);

			return new RuntimeBoolValue(!str.equals(str2));
		}

		runtimeError("Type error for !=", where);
		return null;
	}

	@Override
	public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String str2 = v.getStringValue(">", where);

			return new RuntimeBoolValue(str.length() > str2.length());
		}

		runtimeError("Type error for >", where);
		return null;
	}

	@Override
	public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String str2 = v.getStringValue(">=", where);

			return new RuntimeBoolValue(str.length() >= str2.length());
		}

		runtimeError("Type error for >=", where);
		return null;
	}

	@Override
	public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String str2 = v.getStringValue("<", where);

			return new RuntimeBoolValue(str.length() < str2.length());
		}

		runtimeError("Type error for <", where);
		return null;
	}

	@Override
	public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String str2 = v.getStringValue("<=", where);

			return new RuntimeBoolValue(str.length() <= str2.length());
		}

		runtimeError("Type error for <=", where);
		return null;
	}
}
