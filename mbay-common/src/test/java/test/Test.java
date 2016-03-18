package test;

import org.sz.mbay.common.interfaces.def.ErrorCode;
import org.sz.mbay.common.interfaces.def.ResultCode;
import org.sz.mbay.common.util.Smart2000Util;
import org.sz.mbay.common.util.Smart2000Util.Smart2000Data;

public class Test {
	
	public static void main(String[] args) {
		System.out.println(ErrorCode.getDesc("global", "0"));
		System.out.println(ResultCode.getDesc("702"));
		
		Smart2000Data data = Smart2000Util.getData(Smart2000Data.MODULE
				| Smart2000Data.UID
				| Smart2000Data.FILE_STORAGE
				| Smart2000Data.PAGE_STORAGE);
		System.out.println(data.getFlag());
		System.out.println(data.getUID());
		System.out.println(data.getModuleString(0));
		System.out.println(data.getFileStorageString());
		System.out.println(data.getPageStorageString(0));
	}
}
