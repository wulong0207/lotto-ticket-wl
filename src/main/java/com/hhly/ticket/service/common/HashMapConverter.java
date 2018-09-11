package com.hhly.ticket.service.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.ExtendedHierarchicalStreamWriterHelper;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

/**
 * @desc HashMap转换器转换为<{panel} {attribute} = "key">value</{panel}>
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class HashMapConverter implements Converter {
	private String attribute;
	
	private String panel;

	public HashMapConverter(String attribute, String panel) {
		this.attribute = attribute;
		this.panel = panel;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(HashMap.class);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Map map = (Map) source;
		for (Iterator iterator = map.entrySet().iterator(); iterator.hasNext();) {
			Entry entry = (Entry) iterator.next();
			ExtendedHierarchicalStreamWriterHelper.startNode(writer, panel, Entry.class);
			writer.addAttribute(attribute, entry.getKey().toString());
			writer.setValue(entry.getValue().toString());
			writer.endNode();
		}
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Map<Object, Object> map = new HashMap<>();
		while (reader.hasMoreChildren()) {
			reader.moveDown();
			Object key = reader.getAttribute(attribute);
			Object value = reader.getValue();
			map.put(key, value);
			reader.moveUp();
		}
		return map;
	}

}
