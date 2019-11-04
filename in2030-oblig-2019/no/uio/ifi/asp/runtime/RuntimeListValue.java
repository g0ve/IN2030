package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.ArrayList;


public class RuntimeListValue extends RuntimeValue {

    ArrayList<RuntimeValue> lst = new ArrayList<>();
	boolean boolValue;

	public RuntimeListValue(ArrayList<RuntimeValue> v) {
		lst = v;

		if(lst.isEmpty()){
			boolValue = false;
		}else{
			boolValue = true;
		}
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
		return String.valueOf(lst);
	}


	@Override
	public boolean getBoolValue(String what, AspSyntax where) {
		return boolValue;
	}

	@Override
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		ArrayList<RuntimeValue> tmpLst = new ArrayList<>();

		if (v instanceof RuntimeIntValue) {
			int teller = 0;
			long times = v.getIntValue("*", where);
			while (teller < times) {
				tmpLst.addAll(lst);
				teller++;
			}
			return new RuntimeListValue(tmpLst);
		}
		runtimeError("Type error for *.", where);
		return null;  // Required by the compiler
	}

	@Override
	public RuntimeValue evalLen(AspSyntax where){
		return new RuntimeIntValue(lst.size());
	}

	@Override
	public RuntimeValue evalSubscription(RuntimeValue v, AspSyntax where) {

		if (v instanceof RuntimeIntValue) {
			int i = (int)v.getIntValue("sub", where);
			return lst.get(i);
		}

		runtimeError("Type error for list key.", where);
		return null;  // Required by the compiler
	}

	public void evalAssignElem(RuntimeValue inx, RuntimeValue value, AspSyntax where) {
		if(inx instanceof RuntimeIntValue){
			int i = (int)((RuntimeIntValue)inx).integer;
			lst.set(i, value);
		}
		runtimeError("Type error for list key.", where);
	}

}
