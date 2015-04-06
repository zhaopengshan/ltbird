package com.leadtone.mas.bizplug.mms.bean;

import java.util.List;

public class MbnMmsFrameVO extends MbnMmsFrame {
	private List<MbnMmsAttachment> mbnMmsAttachment;

	public List<MbnMmsAttachment> getMbnMmsAttachment() {
		return mbnMmsAttachment;
	}

	public void setMbnMmsAttachment(List<MbnMmsAttachment> mbnMmsAttachment) {
		this.mbnMmsAttachment = mbnMmsAttachment;
	}
}
