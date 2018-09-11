package com.hhly.ticket.service.ticket.dealer.tengshun.response;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamImplicit;

public class Rows {
 
	@XStreamImplicit
	private List<Row> rows;

	/**
	 * @return the rows
	 */
	public List<Row> getRows() {
		return rows;
	}

	/**
	 * @param rows
	 *            the rows to set
	 */
	public void setRows(List<Row> rows) {
		this.rows = rows;
	}
}
