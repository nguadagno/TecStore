package it.unisa.model;

import java.util.Objects;

public class UtenteBean {
	String CF;
	String nome;
	String cognome;
	String email;
	String password;
	String via;
	int numeroCivico;
	String citta;
	String provincia;
	int CAP;
	int tipologia;
			
	public UtenteBean() {}
	
	public UtenteBean(String CF, String nome, String cognome, String email, String password, String via, int numeroCivico, String citta, String provincia, int CAP, int tipologia) {
		this.CF=CF;
		this.nome=nome;
		this.cognome=cognome;
		this.email=email;
		this.password=password;
		this.via=via;
		this.numeroCivico=numeroCivico;
		this.citta=citta;
		this.provincia=provincia;
		this.CAP=CAP;
		this.tipologia=tipologia;
	
	}
	
	public boolean checkCf(String CF) {
		//Reference: https://web.archive.org/web/20201102045911/http://blog.marketto.it/2016/01/regex-validazione-codice-fiscale-con-omocodia/ 
		return CF.matches("^(?:[A-Z][AEIOU][AEIOUX]|[AEIOU]X{2}|[B-DF-HJ-NP-TV-Z]{2}[A-Z]){2}(?:[\\dLMNP-V]{2}(?:[A-EHLMPR-T](?:[04LQ][1-9MNP-V]|[15MR][\\dLMNP-V]|[26NS][0-8LMNP-U])|[DHPS][37PT][0L]|[ACELMRT][37PT][01LM]|[AC-EHLMPR-T][26NS][9V])|(?:[02468LNQSU][048LQU]|[13579MPRTV][26NS])B[26NS][9V])(?:[A-MZ][1-9MNP-V][\\dLMNP-V]{2}|[A-M][0L](?:[1-9MNP-V][\\dLMNP-V]|[0L][1-9MNP-V]))[A-Z]$");
	}
	
	@Override
	public String toString() {
		return "UtenteBean [CF=" + CF + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", password="
				+ password + ", via=" + via + ", numeroCivico=" + numeroCivico + ", citta=" + citta + ", provincia="
				+ provincia + ", CAP=" + CAP + ", tipologia=" + tipologia + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UtenteBean other = (UtenteBean) obj;
		return CAP == other.CAP && Objects.equals(CF, other.CF) && Objects.equals(citta, other.citta)
				&& Objects.equals(cognome, other.cognome) && Objects.equals(email, other.email)
				&& Objects.equals(nome, other.nome) && numeroCivico == other.numeroCivico
				&& Objects.equals(password, other.password) && Objects.equals(provincia, other.provincia)
				&& tipologia == other.tipologia && Objects.equals(via, other.via);
	}
			
	public String getCF() {
		return CF;
	}
	
	public void setCF(String cF) {
		CF = cF;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getCognome() {
		return cognome;
	}
	
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getVia() {
		return via;
	}
	
	public void setVia(String via) {
		this.via = via;
	}
	
	public int getNumeroCivico() {
		return numeroCivico;
	}
	
	public void setNumeroCivico(int numeroCivico) {
		this.numeroCivico = numeroCivico;
	}
	
	public String getCitta() {
		return citta;
	}
	
	public void setCitta(String citta) {
		this.citta = citta;
	}
	
	public String getProvincia() {
		return provincia;
	}
	
	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}
	
	public int getCAP() {
		return CAP;
	}
	
	public void setCAP(int cAP) {
		CAP = cAP;
	}
	
	public int getTipologia() {
		return tipologia;
	}
	
	public void setTipologia(int tipologia) {
		this.tipologia = tipologia;
	}
	
}
