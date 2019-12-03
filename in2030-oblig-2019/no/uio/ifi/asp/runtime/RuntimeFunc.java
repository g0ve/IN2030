package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.*;
import java.util.ArrayList;

public class RuntimeFunc extends RuntimeValue {
	public ArrayList<RuntimeValue> formalParam = new ArrayList<>();

	AspFuncDef def;
	RuntimeScope defScope;
	String name;

	public RuntimeFunc(AspFuncDef def, RuntimeScope defscope, String name){
    	this.def = def;
	  	this.defScope = defScope;
	  	this.name = name;

    }

	public RuntimeFunc(String name){
		this.name = name;
	}

    @Override
    protected String typeName() {
      return "function";
    }

    @Override
    public String toString(){
      return "function " + name;
    }

    @Override
    public RuntimeValue evalFuncCall(ArrayList<RuntimeValue> actualParams, AspSyntax where) {

		if(formalParam.size() == actualParams.size()){
			RuntimeScope newscope = new RuntimeScope(defScope);

			for (int i = 0; i < formalParam.size(); i++) {
				RuntimeValue v = actualParams.get(i);
				String id = formalParam.get(i).getStringValue("function call", where);
				newscope.assign(id, v);
			}

			try {
				def.runFunction(newscope);
			} catch(RuntimeReturnValue rrv) {
				return rrv.value;
			}
		}else{
			runtimeError("Error " + name, where);
		}

		return v;
	}

}
