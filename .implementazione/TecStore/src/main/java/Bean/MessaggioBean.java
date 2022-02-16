package Bean;

import java.util.Date;
import java.util.Objects;

public class MessaggioBean {

	private String IDTicket;
	private String CF;
	private String contenuto;
	private Date data;
	private UtenteBean autore;

	public MessaggioBean(String iDTicket, String cF, String contenuto, Date data, UtenteBean autore) {
		super();
		IDTicket = iDTicket;
		CF = cF;
		this.contenuto = contenuto;
		this.data = data;
		this.autore = autore;
	}

	@Override
	public String toString() {
		return "MessaggioBean [IDTicket=" + IDTicket + ", CF=" + CF + ", contenuto=" + contenuto + ", data=" + data
				+ ", autore=" + autore + "]";
	}

	public UtenteBean getAutore() {
		return autore;
	}

	public void setAutore(UtenteBean autore) {
		this.autore = autore;
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
				&& Objects.equals(autore, other.autore) && Objects.equals(contenuto, other.contenuto)
				&& Objects.equals(data, other.data);
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

	public void setIDTicket(String iDTicket) {
		IDTicket = iDTicket;
	}

	public void setCF(String cF) {
		CF = cF;
	}

	public void setContenuto(String contenuto) {
		this.contenuto = contenuto;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getData() {
		return data;
	}

}
