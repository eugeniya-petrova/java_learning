package ru.learning.mantis.model;

public class MailMessage {
	
	public String to;
	public String text;

	public MailMessage(String to, String text) {
		this.to = to;
		this.text = text;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MailMessage that = (MailMessage) o;

		if (to != null ? !to.equals(that.to) : that.to != null) return false;
		return text != null ? text.equals(that.text) : that.text == null;
	}

	@Override
	public int hashCode() {
		int result = to != null ? to.hashCode() : 0;
		result = 31 * result + (text != null ? text.hashCode() : 0);
		return result;
	}
	
}
