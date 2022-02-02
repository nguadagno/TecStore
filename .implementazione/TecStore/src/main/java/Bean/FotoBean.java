package Bean;

import java.util.Objects;

public class FotoBean {

	private String ID;
	private String IDArticolo;
	private byte[] foto;

	public FotoBean(String iD, String iDArticolo, byte[] foto) {
		super();
		ID = iD;
		IDArticolo = iDArticolo;
		this.foto = foto;
	}

	public FotoBean() {
	}

	@Override
	public String toString() {
		return "FotoBeans [ID=" + ID + ", IDArticolo=" + IDArticolo + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FotoBean other = (FotoBean) obj;
		return Objects.equals(ID, other.ID) && Objects.equals(IDArticolo, other.IDArticolo)
				&& Objects.equals(foto, other.foto);
	}

	public String getID() {
		return ID;
	}

	public String getIDArticolo() {
		return IDArticolo;
	}

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

}
