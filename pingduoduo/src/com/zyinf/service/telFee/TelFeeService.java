package com.zyinf.service.telFee;

import com.zyinf.bean.GetPackageResp;
import com.zyinf.bean.OrderDataFlowResp;
import com.zyinf.bean.PingDuoDuoResult;
import com.zyinf.bean.PostOrderDataFlowReq;
import com.zyinf.bean.telFee.MYNotifyReq;
import com.zyinf.bean.telFee.MYOrderTelFeeByPhoneReq;
import com.zyinf.bean.telFee.MYOrderTelFeeByPhoneRsp;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneReq;
import com.zyinf.bean.telFee.PDDOrderTelFeeByPhoneRsp;
import com.zyinf.bean.telFee.PDDTelFeeNotifyReq;
public interface TelFeeService {
	

	public PDDOrderTelFeeByPhoneRsp getPDDOrderTelFeeByPhoneRsp(MYOrderTelFeeByPhoneReq mYOrderTelFeeByPhoneReq,MYOrderTelFeeByPhoneRsp mYOrderTelFeeByPhoneRsp);
	
	public PingDuoDuoResult<PDDOrderTelFeeByPhoneRsp> requstMYTelFee(PDDOrderTelFeeByPhoneReq req);
	
	public String requstPDDTelNotify(MYNotifyReq myReq);
}
