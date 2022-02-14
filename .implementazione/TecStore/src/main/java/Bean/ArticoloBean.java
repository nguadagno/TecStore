package Bean;

import java.sql.Date;
import java.util.Objects;
import java.util.Set;

public class ArticoloBean {
	private String ID;
	private String nome;
	private String descrizione;
	private String IDVenditore;
	private int quantita;
	private float prezzo;
	private String stato;
	private String IDCentralinista;
	private Date data;
	private boolean rimborsabile;

	public ArticoloBean() {
	}

	public ArticoloBean(String iD, String nome, String descrizione, String iDVenditore, int quantita, float prezzo,
			String stato, String iDCentralinista, Date data, boolean rimborsabile) {
		super();
		ID = iD;
		this.nome = nome;
		this.descrizione = descrizione;
		IDVenditore = iDVenditore;
		this.quantita = quantita;
		this.prezzo = prezzo;
		this.stato = stato;
		IDCentralinista = iDCentralinista;
		this.data = data;
		this.rimborsabile = rimborsabile;
	}

	@Override
	public String toString() {
		return "ArticoloBean [ID=" + ID + ", nome=" + nome + ", descrizione=" + descrizione + ", IDVenditore="
				+ IDVenditore + ", quantita=" + quantita + ", prezzo=" + prezzo + ", stato=" + stato
				+ ", IDCentralinista=" + IDCentralinista + ", data=" + data + ", rimborsabile=" + rimborsabile + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ArticoloBean other = (ArticoloBean) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(IDCentralinista, other.IDCentralinista)
				&& Objects.equals(IDVenditore, other.IDVenditore) && Objects.equals(data, other.data)
				&& Objects.equals(descrizione, other.descrizione) && Objects.equals(nome, other.nome)
				&& Float.floatToIntBits(prezzo) == Float.floatToIntBits(other.prezzo) && quantita == other.quantita
				&& rimborsabile == other.rimborsabile && Objects.equals(stato, other.stato);
	}

	public String getID() {
		return ID;
	}
	public void setID(String id) {
		this.ID = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getIDVenditore() {
		return IDVenditore;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		if (quantita > 0)
			this.quantita = quantita;
	}

	public float getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(float prezzo) {
		if (prezzo > 0.0)
			this.prezzo = prezzo;
	}

	public String getStato() {
		return stato;
	}

	public void setStato(String stato) {
		final Set<String> states = Set.of("InAttesa", "InVendita", "Annullato");
		if (states.contains(stato))
			this.stato = stato;
	}

	public String getIDCentralinista() {
		return IDCentralinista;
	}

	public void setIDCentralinista(String iDCentralinista) {
		IDCentralinista = iDCentralinista;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public boolean isRimborsabile() {
		return rimborsabile;
	}

	public void setRimborsabile(boolean rimborsabile) {
		this.rimborsabile = rimborsabile;
	}

}