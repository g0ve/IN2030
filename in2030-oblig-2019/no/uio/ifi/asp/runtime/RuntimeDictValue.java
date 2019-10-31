package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;


public class RuntimeDictValue extends RuntimeValue {

    HashMap<String, RuntimeValue> dict = new HashMap<>();
	boolean boolValue;

	public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
		dict = v;

		if(dict.isEmpty()){
			boolValue = false;
		}else{
			boolValue = true;
		}
	}


	@Override
	protected String typeName() {
		return "dict";
	}

	@Override
    public String showInfo(){
        return toString();
    }

	@Override
	public String toString() {
		return String.valueOf(dict);
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
