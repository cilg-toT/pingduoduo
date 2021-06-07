package com.zyinf.service.dataFlow;

import com.zyinf.bean.PingDuoDuoResult;
import com.zyinf.bean.dataFlow.MYNotifyDataFlowReq;
import com.zyinf.bean.dataFlow.PDDDataFlowReq;
import com.zyinf.bean.dataFlow.PDDDataFlowRsp;
public interface DataFlowService {

	public PingDuoDuoResult<PDDDataFlowRsp> requstMYDataFlow(PDDDataFlowReq req);
	
	public String requstPDDDataFlowNotify(MYNotifyDataFlowReq myReq);


	
}
