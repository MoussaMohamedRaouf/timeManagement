package com.company.view;

import java.time.LocalDate;
import java.util.EventObject;

public class FormEvent extends EventObject  {
	
	private String name;
	private String type;
	private LocalDate start;
	private LocalDate end;
	public FormEvent(Object source) {
		super(source);
	}
	public FormEvent(Object source, String name, String type, LocalDate start, LocalDate end){
		super(source);
		this.name = name;
		this.type = type;
		this.start = start;
		this.end = end;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public LocalDate getStart() { return this.start;}
	public void setStart(LocalDate start) {
		this.start = start;
	}
	
	public LocalDate getEnd() { return this.end;}
	public void setEnd(LocalDate end) {
		this.end = end;
	}
	

}
