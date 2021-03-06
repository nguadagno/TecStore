package bean;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Objects;

public class FotoBean {

	private String ID;
	private String IDArticolo;
	private byte[] foto;

	public FotoBean(String iD, String iDArticolo, InputStream foto) throws IOException {
		super();
		ID = iD;
		IDArticolo = iDArticolo;
		this.foto = foto.readAllBytes();
	}

	public FotoBean(String iD, String iDArticolo, Blob foto) throws SQLException {
		super();
		ID = iD;
		IDArticolo = iDArticolo;
		this.foto = foto.getBytes(1, (int) foto.length());
	}

	public FotoBean() {
	}

	@Override
	public String toString() {
		return "FotoBean [ID=" + ID + ", IDArticolo=" + IDArticolo + "]";
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
