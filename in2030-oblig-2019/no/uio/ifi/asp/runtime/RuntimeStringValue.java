package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

import java.util.HashMap;


public class RuntimeStringValue extends RuntimeValue {

	String strValue;

	public RuntimeStringValue(String v) {
		strValue = v;
	}


	@Override
	protected String typeName() {
		return "string";
	}

	@Override
    public String showInfo(){
        if (strValue.charAt(0) == '\"') {
        	return "\"" + strValue + "\"";
        }else {
			return "\'" + strValue + "\'";
		}

    }

	@Override
	public String toString() {
		return strValue;
	}

	@Override
    public String getStringValue(String what, AspSyntax where) {
    	return strValue;
    }


	@Override
	public boolean getBoolValue(String what, AspSyntax where) {
		if(strValue.equals("")){
			return false;
		}else{
			return true;
		}
	}
	@Override
public long getIntValue(String what, AspSyntax where) {
	return Integer.parseInt(strValue);
}

	@Override
	public RuntimeValue evalLen(AspSyntax where){
		return new RuntimeIntValue(strValue.length());
	}

	@Override
	public RuntimeValue evalAdd(RuntimeValue v, AspSyntax where){
		if(v instanceof RuntimeFunc){
			//System.out.println("BItCH ");
		}
		if(v instanceof RuntimeStringValue){
			//System.out.println("NÅ KOMPIS");
			return new RuntimeStringValue(strValue + v.getStringValue("+", where));
		}
		runtimeError("'+' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalMultiply(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeIntValue) {
			long count = v.getIntValue("*", where);
			String tmp = "";

			for (long i = 0; i < count; i++) {
				tmp = tmp + strValue;
			}
			return new RuntimeStringValue(tmp);
		}

		runtimeError("'*' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String strValue2 = v.getStringValue("==", where);

			return new RuntimeBoolValue(strValue.equals(strValue2));
		}

		runtimeError("'==' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalNotEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String strValue2 = v.getStringValue("!=", where);

			return new RuntimeBoolValue(!strValue.equals(strValue2));
		}

		runtimeError("'!=' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalGreater(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String strValue2 = v.getStringValue(">", where);

			return new RuntimeBoolValue(strValue.length() > strValue2.length());
		}

		runtimeError("'>' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalGreaterEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String strValue2 = v.getStringValue(">=", where);

			return new RuntimeBoolValue(strValue.length() >= strValue2.length());
		}

		runtimeError("'>=' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalLess(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String strValue2 = v.getStringValue("<", where);

			return new RuntimeBoolValue(strValue.length() < strValue2.length());
		}

		runtimeError("'<' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}

	@Override
	public RuntimeValue evalLessEqual(RuntimeValue v, AspSyntax where){
		if (v instanceof RuntimeStringValue) {
			String strValue2 = v.getStringValue("<=", where);

			return new RuntimeBoolValue(strValue.length() <= strValue2.length());
		}

		runtimeError("'<=' undefined for "+typeName()+"!", where);
		return null;  // Required by the compiler!
	}
}
