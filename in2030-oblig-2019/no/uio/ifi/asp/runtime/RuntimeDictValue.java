package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;
import java.util.Map;


public class RuntimeDictValue extends RuntimeValue {

    Map<String, RuntimeValue> dict = new HashMap<>();

	public RuntimeDictValue(HashMap<String, RuntimeValue> v) {
		dict = v;
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
                output = output + "\'" + key + "\': " + value.showInfo() + ", ";
            }else{
                output = output + "\'" + key + "\': " + value.showInfo();
            }

            teller = teller + 1;
        }
        output = output + '}';

        return output;
	}


	@Override
	public boolean getBoolValue(String what, AspSyntax where) {
        if(dict.isEmpty()){
            return false;
        }else{
            return true;
        }
	}

	@Override
	public RuntimeValue evalLen(AspSyntax where){
		return new RuntimeIntValue(dict.size());
	}

	@Override
	public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {
		if (v instanceof RuntimeStringValue) {
            String key = v.getStringValue("evalSubscription", where);
			if(dict.get(key) != null){
				return dict.get(key);
			}
			runtimeError("Key: " + key + " not found in dictionary.", where);
		}

        runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
        return null;  // Required by the compiler!
	}

    @Override
	public void evalAssignElem(RuntimeValue inx, RuntimeValue value, AspSyntax where) {

        String key = inx.getStringValue("assign elem dict", where);
        dict.put(key, value);

	}

}
