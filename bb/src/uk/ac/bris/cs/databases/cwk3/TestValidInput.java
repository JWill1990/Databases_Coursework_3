package uk.ac.bris.cs.databases.cwk3;

public class TestValidInput {

    static Boolean  isProperString(String string) {
    	int UvalueSpace= string.indexOf(' ');
    	int UvalueQuote= string.indexOf('"');
    	int UvalueDot= string.indexOf('.');
    	
    	if (UvalueQuote >= 0 || UvalueSpace >=0 || UvalueDot>=0){
    		return false;
    	}
    	else{
    		return true;
    	}
    }

    public static Boolean Validator(String string) {

       return isProperString(string);

    }
}
