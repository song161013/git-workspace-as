package com.android.tool;

public class CaseInfo
{
	private String name;
	private String assetPath;
	private String detail;
	
	public CaseInfo(String name, String assetPath)
	{
		this.name = name;
		this.assetPath = assetPath;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAssetPath()
	{
		return assetPath;
	}

	public void setAssetPath(String assetPath)
	{
		this.assetPath = assetPath;
	}

	public String getDetail()
	{
		return detail;
	}

	public void setDetail(String detail)
	{
		this.detail = detail;
	}
}
