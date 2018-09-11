package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import com.hhly.ticket.service.ticket.dealer.ruilang.AbstractXml;
import com.thoughtworks.xstream.annotations.XStreamAlias;
/**
 * 彩期查询
 * @desc
 * @author jiangwei
 * @date 2017年5月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("body")
public class Issue extends AbstractXml{
	
	private IsuueLoto loto;

	public IsuueLoto getLoto() {
		return loto;
	}

	public void setLoto(IsuueLoto loto) {
		this.loto = loto;
	}	

}
