package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;


public class RuntimeListValue extends RuntimeValue {

    ArrayList<RuntimeValue> lstValue = new ArrayList<>();

	public RuntimeListValue(ArrayList<RuntimeValue> v) {
		lstValue = v;
	}


	@Override
	protected String typeName() {
		return "list";
	}

	@Override
    public String showInfo(){
        return toString();
    }

	@Override
	public String toString() {
		return String.valueOf(lstValue);
	}


	@Override
	public boolean getBoolValue(String what, AspSyntax where) {
        if(lstValue.isEmpty()){
			return false;
		}else{
			return true;
		}
	}

	@Override
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		ArrayList<RuntimeValue> tmpLst = new ArrayList<>();
		if (v instanceof RuntimeIntValue) {
			int teller = 0;
			long times = v.getIntValue("*", where);
			while (teller < times) {
				tmpLst.addAll(lstValue);
				teller++;
			}
			return new RuntimeListValue(tmpLst);
		}
        runtimeError("Unary '-' undefined for "+typeName()+"!", where);
    	return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalLen(AspSyntax where){
		return new RuntimeIntValue(lstValue.size());
	}

	@Override
	public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

		if (v instanceof RuntimeIntValue) {
			int i = (int)v.getIntValue("sub", where);
            if(i > lstValue.size()-1){
                runtimeError("Type error for indeks "+typeName()+"!", where);
            }
			return lstValue.get(i);
		}

        runtimeError("Subscription '[...]' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler
	}

    @Override
	public void evalAssignElem(RuntimeValue inx, RuntimeValue value, AspSyntax where) {
		if(inx instanceof RuntimeIntValue){
			int i = (int)((RuntimeIntValue)inx).intValue;
			lstValue.set(i, value);
		}
    else{
      runtimeError("Assigning to an element not allowed for "+typeName()+"!", where);
    }
	}

}
