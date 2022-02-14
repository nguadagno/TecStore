package Bean;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

public class TicketBean {

	private String IDTicket;
	private String IDCliente;
	private String tipologia;
	private String stato;
	private Date dataUltimoMessaggio;

	public TicketBean() {
	}

	public TicketBean(String iDTicket, String iDCliente, String tipologia, String stato, Date dataUltimoMessaggio) {
		super();
		IDTicket = iDTicket;
		IDCliente = iDCliente;
		this.tipologia = tipologia;
		this.stato = stato;
		this.dataUltimoMessaggio = dataUltimoMessaggio;
	}

	@Override
	public String toString() {
		return "TicketBean [IDTicket=" + IDTicket + ", IDCliente=" + IDCliente + ", tipologia=" + tipologia + ", stato="
				+ stato + ", dataUltimoMessaggio=" + dataUltimoMessaggio + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TicketBean other = (TicketBean) obj;
		return Objects.equals(IDCliente, other.IDCliente) && Objects.equals(IDTicket, other.IDTicket)
				&& Objects.equals(stato, other.stato) && Objects.equals(tipologia, other.tipologia);
	}

	public String getIDTicket() {
		return IDTicket;
	}

	public String getIDCliente() {
		return IDCliente;
	}

	public String getTipologia() {
		return tipologia;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		final Set<String> states = Set.of("InElaborazione", "InAttesa", "Chiuso", "InAttesaCliente");
		if (states.contains(stato))
			this.stato = stato;
	}

	public Date getDataUltimoMessaggio() {
		return dataUltimoMessaggio;
	}

}
