package com.project.penyewaanalatpesta.model.modelbarang;

public class Barang_model {
	String key;
	String nama_barang, harga, stok, gambar_barang, jenis_barang,idpenjual;

	public Barang_model() {
	}

	public Barang_model(String nama_barang, String harga, String stok, String gambar_barang, String jenis_barang, String idpenjual) {
		this.nama_barang = nama_barang;
		this.harga = harga;
		this.stok = stok;
		this.gambar_barang = gambar_barang;
		this.jenis_barang = jenis_barang;
		this.idpenjual = idpenjual;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getNama_barang() {
		return nama_barang;
	}

	public void setNama_barang(String nama_barang) {
		this.nama_barang = nama_barang;
	}

	public String getHarga() {
		return harga;
	}

	public void setHarga(String harga) {
		this.harga = harga;
	}

	public String getStok() {
		return stok;
	}

	public void setStok(String stok) {
		this.stok = stok;
	}

	public String getGambar_barang() {
		return gambar_barang;
	}

	public void setGambar_barang(String gambar_barang) {
		this.gambar_barang = gambar_barang;
	}

	public String getJenis_barang() {
		return jenis_barang;
	}

	public void setJenis_barang(String jenis_barang) {
		this.jenis_barang = jenis_barang;
	}

	public String getIdpenjual() {
		return idpenjual;
	}

	public void setIdpenjual(String idpenjual) {
		this.idpenjual = idpenjual;
	}
}
