package po;

import java.util.Date;
public class Message {

	public Long from;
	public String fromName;
	public String fromId;
	public String to;
	public String text;
	public Date date;

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}
	
	public Long getFrom() {
		return from;
	}

	public void setFrom(Long from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "Message [from=" + from + ", fromName=" + fromName + ", fromId=" + fromId + ", to=" + to + ", text="
				+ text + ", date=" + date + "]";
	}

}
