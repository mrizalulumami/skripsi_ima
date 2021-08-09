package com.project.penyewaanalatpesta.model;

public class History_model {

		String key,imagehistory,namabarang,namapenyewa,alamat,idpenyewa,jumlahsewa,totalharga;

	public History_model() {
	}

	public History_model(String imagehistory, String namabarang, String namapenyewa, String alamat, String idpenyewa, String jumlahsewa, String totalharga) {
		this.imagehistory = imagehistory;
		this.namabarang = namabarang;
		this.namapenyewa = namapenyewa;
		this.alamat = alamat;
		this.idpenyewa = idpenyewa;
		this.jumlahsewa = jumlahsewa;
		this.totalharga = totalharga;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getImagehistory() {
		return imagehistory;
	}

	public void setImagehistory(String imagehistory) {
		this.imagehistory = imagehistory;
	}

	public String getNamabarang() {
		return namabarang;
	}

	public void setNamabarang(String namabarang) {
		this.namabarang = namabarang;
	}

	public String getNamapenyewa() {
		return namapenyewa;
	}

	public void setNamapenyewa(String namapenyewa) {
		this.namapenyewa = namapenyewa;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getIdpenyewa() {
		return idpenyewa;
	}

	public void setIdpenyewa(String idpenyewa) {
		this.idpenyewa = idpenyewa;
	}

	public String getJumlahsewa() {
		return jumlahsewa;
	}

	public void setJumlahsewa(String jumlahsewa) {
		this.jumlahsewa = jumlahsewa;
	}

	public String getTotalharga() {
		return totalharga;
	}

	public void setTotalharga(String totalharga) {
		this.totalharga = totalharga;
	}
}
