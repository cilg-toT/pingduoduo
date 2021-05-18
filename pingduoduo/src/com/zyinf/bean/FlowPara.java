package com.zyinf.bean;

import java.util.Arrays;


public class FlowPara {
	private FlowPara_RSP_REC[] RSP_REC;

	public FlowPara_RSP_REC[] getRSP_REC() {
		return RSP_REC;
	}
	public void setRSP_REC(FlowPara_RSP_REC[] rSPREC) {
		RSP_REC = rSPREC;
	}
	@Override
	public String toString() {
		return "FlowPara [RSP_REC=" + Arrays.toString(RSP_REC) + "]";
	}
	
}
