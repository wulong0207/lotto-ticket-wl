package com.hhly.ticket.service.ticket.dealer.ruilang.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@XStreamAlias("ticket")
public class CathecticTicket implements Converter{
	
    public CathecticTicket(String seq, String content) {
		this.seq = seq;
		this.content = content;
	}

	public CathecticTicket() {
	}

	private String seq;
    
    private String content;

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(this.getClass());  
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		CathecticTicket ticket = (CathecticTicket) source;  
        writer.addAttribute("seq", ticket.getSeq());  
        writer.setValue(ticket.getContent()); 
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		 CathecticTicket s = new CathecticTicket(reader.getAttribute("seq"),reader.getValue()); 
         return s; 
	}
    
    
}
