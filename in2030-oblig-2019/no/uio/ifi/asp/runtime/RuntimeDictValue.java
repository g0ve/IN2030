package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;
import java.util.Map;


public class RuntimeDictValue extends RuntimeValue {

    Map<String, RuntimeValue> dict = new HashMap<>();
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
		String output = "";
        int teller = 0;

        output = output + "{";
        for (Map.Entry<String, RuntimeValue> entry : dict.entrySet()) {
            String key = entry.getKey();
            RuntimeValue value = entry.getValue();
            if(teller < dict.size()-1){
                output = output + "\'" + key + "\': " + value.toString() + ", ";
            }else{
                output = output + "\'" + key + "\': " + value.toString();
            }

            teller = teller + 1;
        }
        output = output + '}';

        return output;
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

    @Override
	public void evalAssignElem(RuntimeValue inx, RuntimeValue value, AspSyntax where) {
		if(inx instanceof RuntimeIntValue){
			String key = inx.getStringValue("assign elem dict", where);
			dict.put(key, value);
		}
		runtimeError("Type error for dictionary key.", where);
	}

}
