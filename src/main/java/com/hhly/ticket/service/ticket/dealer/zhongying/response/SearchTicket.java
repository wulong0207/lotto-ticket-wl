package com.hhly.ticket.service.ticket.dealer.zhongying.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@XStreamAlias("ticket")
public class SearchTicket implements Converter {
	private String matchineid;

	private String sp;

	private String externalid;

	private String printtime;

	private String value;

	public String getMatchineid() {
		return matchineid;
	}

	public void setMatchineid(String matchineid) {
		this.matchineid = matchineid;
	}

	public String getSp() {
		return sp;
	}

	public void setSp(String sp) {
		this.sp = sp;
	}

	public String getExternalid() {
		return externalid;
	}

	public void setExternalid(String externalid) {
		this.externalid = externalid;
	}

	public String getPrinttime() {
		return printtime;
	}

	public void setPrinttime(String printtime) {
		this.printtime = printtime;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(this.getClass());
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {

	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		SearchTicket m = new SearchTicket();
		m.setExternalid(reader.getAttribute("externalid"));
		m.setSp(reader.getAttribute("sp"));
		m.setMatchineid(reader.getAttribute("matchineid"));
		m.setPrinttime(reader.getAttribute("printtime"));
		m.setValue(reader.getValue());
		return m;
	}
}
