package com.project.penyewaanalatpesta.model.keranjangmodel;

public class Keranjang_model {
	String totalstok,keybarangs,key, idpenyewa,idpenjual,namapenyewa,alamatpenyewa,namabarang,nohppenyewa,gambarbarang,totalharga,status,jenis,stok;

	public Keranjang_model() {
	}

	public Keranjang_model(String totalstok,String keybarangs,String stok,String idpenyewa, String idpenjual, String namapenyewa, String alamatpenyewa, String namabarang, String nohppenyewa, String gambarbarang, String totalharga, String status, String jenis) {
		this.idpenyewa = idpenyewa;
		this.idpenjual = idpenjual;
		this.namapenyewa = namapenyewa;
		this.alamatpenyewa = alamatpenyewa;
		this.namabarang = namabarang;
		this.nohppenyewa = nohppenyewa;
		this.gambarbarang = gambarbarang;
		this.totalharga = totalharga;
		this.status = status;
		this.jenis = jenis;
		this.stok = stok;
		this.keybarangs = keybarangs;
		this.totalstok = totalstok;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIdpenyewa() {
		return idpenyewa;
	}

	public void setIdpenyewa(String idpenyewa) {
		this.idpenyewa = idpenyewa;
	}

	public String getIdpenjual() {
		return idpenjual;
	}

	public void setIdpenjual(String idpenjual) {
		this.idpenjual = idpenjual;
	}

	public String getNamapenyewa() {
		return namapenyewa;
	}

	public void setNamapenyewa(String namapenyewa) {
		this.namapenyewa = namapenyewa;
	}

	public String getAlamatpenyewa() {
		return alamatpenyewa;
	}

	public void setAlamatpenyewa(String alamatpenyewa) {
		this.alamatpenyewa = alamatpenyewa;
	}

	public String getNamabarang() {
		return namabarang;
	}

	public void setNamabarang(String namabarang) {
		this.namabarang = namabarang;
	}

	public String getNohppenyewa() {
		return nohppenyewa;
	}

	public void setNohppenyewa(String nohppenyewa) {
		this.nohppenyewa = nohppenyewa;
	}

	public String getGambarbarang() {
		return gambarbarang;
	}

	public void setGambarbarang(String gambarbarang) {
		this.gambarbarang = gambarbarang;
	}

	public String getTotalharga() {
		return totalharga;
	}

	public void setTotalharga(String totalharga) {
		this.totalharga = totalharga;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getJenis() {
		return jenis;
	}

	public void setJenis(String jenis) {
		this.jenis = jenis;
	}

	public String getStok() {
		return stok;
	}

	public void setStok(String stok) {
		this.stok = stok;
	}

	public String getKeybarangs() {
		return keybarangs;
	}

	public void setKeybarangs(String keybarangs) {
		this.keybarangs = keybarangs;
	}

	public String getTotalstok() {
		return totalstok;
	}

	public void setTotalstok(String totalstok) {
		this.totalstok = totalstok;
	}
}