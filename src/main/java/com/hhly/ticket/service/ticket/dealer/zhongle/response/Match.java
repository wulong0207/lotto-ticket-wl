package com.hhly.ticket.service.ticket.dealer.zhongle.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@XStreamAlias("match")
public class Match implements Converter{
	
	private String id;

	private String playid;

	private String rq;

	private String value;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayid() {
		return playid;
	}

	public void setPlayid(String playid) {
		this.playid = playid;
	}

	public String getRq() {
		return rq;
	}

	public void setRq(String rq) {
		this.rq = rq;
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
		Match m = new Match();
		m.setId(reader.getAttribute("id"));
		m.setPlayid(reader.getAttribute("playid"));
		m.setRq(reader.getAttribute("rq"));
		m.setValue(reader.getValue());
		return m;
	}

}
