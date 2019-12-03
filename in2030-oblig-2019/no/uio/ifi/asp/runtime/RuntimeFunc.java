package no.uio.ifi.asp.runtime;

import no.uio.ifi.asp.main.*;
import no.uio.ifi.asp.parser.AspSyntax;
import no.uio.ifi.asp.parser.*;
import java.util.ArrayList;

public class RuntimeFunc extends RuntimeValue {
	//public ArrayList<RuntimeValue> args = new ArrayList<>();
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

		ArrayList<AspName> anLst = def.getLstName();
		RuntimeValue v = null;

		if(anLst.size()-1 == actualParams.size()){
			RuntimeScope newscope = new RuntimeScope(defScope);

			for (int i = 0; i < actualParams.size(); i++) {
				RuntimeValue v2 = defScope.find(actualParams.get(i).toString(), def);
				if(v2 == null){
					newscope.assign(anLst.get(i+1).getTokenName(), v2);
				}else{
					newscope.assign(anLst.get(i+1).getTokenName(), actualParams.get(i));
				}

				try {
					v = def.getSuite().eval(newscope);
				} catch(RuntimeReturnValue rrv) {
					return rrv.value;
				}
			}
		}else{
			runtimeError("Error " + name, where);
		}

		return new RuntimeNoneValue();
	}

}
