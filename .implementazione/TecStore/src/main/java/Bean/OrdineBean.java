package Bean;

import java.util.Date;
import java.util.Objects;
import java.util.Set;

public class OrdineBean {
	private String ID;
	private String IDCliente;
	private String IDArticolo;
	private int quantita;
	private Date data;
	private String stato;
	private String codiceTracciamento;

	public OrdineBean(String iD, String iDCliente, String iDArticolo, int quantita, Date data, String stato,
			String codiceTracciamento) {
		super();
		ID = iD;
		IDCliente = iDCliente;
		IDArticolo = iDArticolo;
		this.quantita = quantita;
		this.data = data;
		this.stato = stato;
		this.codiceTracciamento = codiceTracciamento;
	}

	public OrdineBean() {
	}

	@Override
	public String toString() {
		return "OrdineBean [ID=" + ID + ", IDArticolo=" + IDArticolo + ", quantita=" + quantita + ", data=" + data
				+ ", stato=" + stato + ", codiceTracciamento=" + codiceTracciamento + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrdineBean other = (OrdineBean) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(IDArticolo, other.IDArticolo)
				&& Objects.equals(codiceTracciamento, other.codiceTracciamento) && Objects.equals(data, other.data)
				&& quantita == other.quantita && Objects.equals(stato, other.stato);
	}

	public String getID() {
		return ID;
	}

	public String getIDCliente() {
		return IDCliente;
	}

	public String getIDArticolo() {
		return IDArticolo;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Date getData() {
		return data;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		final Set<String> states = Set.of("InAttesa", "Spedito", "Rimborsato", "Annullato");
		if (states.contains(stato))
			this.stato = stato;
	}

	public String getCodiceTracciamento() {
		return codiceTracciamento;
	}

	public void setCodiceTracciamento(String codiceTracciamento) {
		this.codiceTracciamento = codiceTracciamento;
	}

}
