package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.*;
import java.util.ArrayList;

public class RuntimeFunc extends RuntimeValue {
	public ArrayList<RuntimeValue> formalParam = new ArrayList<>();

	AspFuncDef afd;
	RuntimeScope defScope;
	String defId;

	public RuntimeFunc(AspFuncDef def, RuntimeScope rScope, String name){
    	afd = def;
	  	defScope = rScope;
	  	defId = name;

    }

	public RuntimeFunc(String name){
		defId = name;
		defScope = defScope;

	}

    @Override
    protected String typeName() {
      return "function";
    }

    @Override
    public String toString(){
      return "function " + defId;
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {

		if(formalParam.size() != actualParams.size()){
			runtimeError("Error in " + defId, where);
		}

		RuntimeScope newscope = new RuntimeScope(defScope);

		for (int i = 0; i < formalParam.size(); i++) {
			RuntimeValue v = actualParams.get(i);
			String id = formalParam.get(i).getStringValue("function call", where);
			newscope.assign(id, v);
		}

		try {
			afd.runFunction(newscope);
		} catch(RuntimeReturnValue rrv) {
			return rrv.value;
		}

		return v;
	}

}
