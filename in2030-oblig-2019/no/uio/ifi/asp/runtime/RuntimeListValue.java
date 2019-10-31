package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.Arraylist;


public class RuntimeListValue extends RuntimeValue {

    Arraylist<RuntimeValue> lst = new Arraylist<>();
	boolean boolValue;

	public RuntimeListValue(Arraylist<RuntimeValue> v) {
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
		Arraylist<RuntimeValue> tmpLst = new Arraylist<>();

		if (v instanceof RuntimeIntValue) {
			int teller = 0;
			long times = v.getIntValue("*", where);
			while (teller < times) {
				tmpLst.addAll(lst);
				teller++;
			}
			return new RuntimeListValue(tmp);
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
			long i = v.getIntValue("sub", where);
			return lst.get(i);
		}

		runtimeError("Type error for list key.", where);
		return null;  // Required by the compiler
	}

	public void evalAssignElem(RuntimeValue i, RuntimeValue value, AspSyntax where) {
		if(i instanceof RuntimeIntValue){
			int i = (int)((RuntimeIntValue)i).intValue;
			lst.set(i, value);
		}
		runtimeError("Type error for list key.", where);
		return null;  // Required by the compiler
	}

}
