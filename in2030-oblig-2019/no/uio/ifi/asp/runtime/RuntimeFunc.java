package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;

public class RuntimeFunc extends RuntimeValue {
	AspFuncDef def;
	RuntimeScope defScope;
	String name;

	public RuntimeFunc(AspFuncDef def, RuntimeScope defscope, String name){
      this.def = def;
	  this.defScope = defScope;
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
		RuntimeScope defScope = new RuntimeScope(defScope);

		for (RuntimeValue v : actualParams) {
			String id = v.getStringsValues("def", where);
			defScope.assign(id, v);
		}

		try {
			def.as.eval(defScope);
		} catch(RuntimeReturnValue rrv) {
			return rrv.value;
		}

		return new RuntimeNoneValue();
	}

}
