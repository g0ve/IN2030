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
		this.defScope = defScope;

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

		// 	int fP = def.getAspNameList().size()-1;
		//
		// 	if(fP == actualParams.size()){
		// 		RuntimeScope scope = new RuntimeScope(defScope);
		//
		// 		for(int i = 0; i<actualParams.size(); i++){
		// 			scope.assign(def.getAspNameList().get(i+1).getTokenName(), actualParams.get(i));
		// 		}
		// 		try{
		// 			def.getSuite().eval(scope);
		// 		}catch(RuntimeReturnValue rrv){
		// 			return rrv.value;
		// 		}
		// 	}
		// 	else{
		// 		RuntimeValue.runtimeError("Wrong " + name + "!", where);
		// 	}
		// 	return null;
		// }


		ArrayList<AspName> anLst = def.getLstName();

		if(anLst.size()-1 == actualParams.size()){
			RuntimeScope newscope = new RuntimeScope(defScope);
			for (int i = 0; i < actualParams.size(); i++) {
				RuntimeValue v = actualParams.get(i);
				String id = anLst.get(i + 1).getTokenName();
				newscope.assign(id, v);
			}

			try {
				def.getSuite().eval(newscope);
			} catch(RuntimeReturnValue rrv) {
				return rrv.value;
			}
		}else{
			runtimeError("Error " + name, where);
		}

		return new RuntimeNoneValue();
	}

}
