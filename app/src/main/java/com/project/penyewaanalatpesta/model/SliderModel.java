package com.project.penyewaanalatpesta.model;

import android.widget.ImageView;

public class SliderModel {

	String imageUrl = null;
	int imagePath = 0;
	String title = null;
	ImageView.ScaleType scaleType = null;



	public SliderModel(int imagePath, String title, ImageView.ScaleType scaleType) {
		this.imagePath = imagePath;
		this.title = title;
		this.scaleType = scaleType;
	}
}
