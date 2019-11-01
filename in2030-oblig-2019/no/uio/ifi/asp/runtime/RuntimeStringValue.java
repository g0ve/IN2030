package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;


public class RuntimeStringValue extends RuntimeValue {

	String str;
	boolean boolValue;

	public RuntimeStringValue(HashMap<String, RuntimeValue> v) {
		str = v;

		if(str.equals("")){
			boolValue = false;
		}else{
			boolValue = true;
		}
	}


	@Override
	protected String typeName() {
		return "String";
	}

	@Override
    public String showInfo(){
        if (str.charAt(0) == '\"') {
        	return "\"" + str + "\"";
        }

		if (str.charAt(0) == '\'') {
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
		return new RuntimeIntValue(dict.size());
	}

	@Override
	public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeStringValue) {
			if(dict.get(v.toString()) != null){
				return dict.get(v.toString());
			}
			runtimeError("Key not found in dictionary.", where);
		}
		if (v instanceof RuntimeIntValue) {
			if(dict.get(v.toString()) != null){
				return dict.get(v.toString());
			}
			runtimeError("Key not found in dictionary.", where);
		}
		if (v instanceof RuntimeFloatValue) {
			if(dict.get(v.toString()) != null){
				return dict.get(v.toString());
			}
			runtimeError("Key not found in dictionary.", where);
		}
		runtimeError("Type error for dictionary key.", where);
		return null;  // Required by the compiler
	}

	public void evalAssignElem(RuntimeValue i, RuntimeValue value, AspSyntax where) {
		if(i instanceof RuntimeIntValue){
			int i = (int)((RuntimeIntValue)i).intValue;
			dict.put(i, value);
		}
		runtimeError("Type error for dictionary key.", where);
	}

}
