package Bean;

import java.util.Date;
import java.util.Objects;

public class MessaggioBean {
	
	private String IDTicket;
	private String CF;
	private String contenuto;
	private Date data;
	
	public MessaggioBean(String iDTicket, String cF, String contenuto, Date data) {
		super();
		IDTicket = iDTicket;
		CF = cF;
		this.contenuto = contenuto;
		this.data = data;
	}
	
	public MessaggioBean() {}

	@Override
	public String toString() {
		return "MessaggioBeans [IDTicket=" + IDTicket + ", CF=" + CF + ", contenuto=" + contenuto + ", data=" + data
				+ "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MessaggioBean other = (MessaggioBean) obj;
		return Objects.equals(CF, other.CF) && Objects.equals(IDTicket, other.IDTicket)
				&& Objects.equals(contenuto, other.contenuto) && Objects.equals(data, other.data);
	}

	public String getIDTicket() {
		return IDTicket;
	}

	public String getCF() {
		return CF;
	}

	public String getContenuto() {
		return contenuto;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public Date getData() {
		return data;
	}

}
