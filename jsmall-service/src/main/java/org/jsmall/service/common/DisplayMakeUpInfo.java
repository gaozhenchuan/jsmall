package org.jsmall.service.common;

import java.io.Serializable;

public class DisplayMakeUpInfo
		implements Serializable {
	private static final long serialVersionUID = 7407250270611088230L;

	public String getScreenId() {
		return this.screenId;
	}

	public void setScreenId(String screenId) {
		this.screenId = screenId;
	}

	private String screenId = null;

	public String getScreenDisplayId() {
		return this.screenDisplayId;
	}

	public void setScreenDisplayId(String screenDisplayId) {
		this.screenDisplayId = screenDisplayId;
	}

	private String screenDisplayId = null;

	public String getProgramId() {
		return this.programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}

	private String programId = null;

	public String getBreadcrumbName() {
		return this.BreadcrumbName;
	}

	public void setBreadcrumbName(String BreadcrumbName) {
		this.BreadcrumbName = BreadcrumbName;
	}

	private String BreadcrumbName = null;

	public String getBreadcrumbDisplayFlag() {
		return this.breadcrumbDisplayFlag;
	}

	public void setBreadcrumbDisplayFlag(String breadcrumbDisplayFlag) {
		this.breadcrumbDisplayFlag = breadcrumbDisplayFlag;
	}

	private String breadcrumbDisplayFlag = null;

	public DisplayMakeUpInfo(String screenId, String screenDisplayId, String programId, String BreadcrumbName,
			String breadcrumbDisplayFlag) {
		this.screenId = screenId;
		this.screenDisplayId = screenDisplayId;
		this.programId = programId;
		this.BreadcrumbName = BreadcrumbName;
		this.breadcrumbDisplayFlag = breadcrumbDisplayFlag;
	}

	public DisplayMakeUpInfo(String screenId, String screenDisplayId, String programId, String BreadcrumbName) {
		this.screenId = screenId;
		this.screenDisplayId = screenDisplayId;
		this.programId = programId;
		this.BreadcrumbName = BreadcrumbName;
		//this.breadcrumbDisplayFlag = this.breadcrumbDisplayFlag;
	}
}
