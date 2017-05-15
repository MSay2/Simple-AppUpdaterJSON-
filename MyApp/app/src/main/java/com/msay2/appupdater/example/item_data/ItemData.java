package com.msay2.appupdater.example.item_data;

/*
 By Yoann Meclot (MSay2)
*/

/*
 FR - Besoin de ItemData pour importer tous les textes
 US - Need the ItemData for the import alls texts
*/

public class ItemData
{
	private String versionName, versionCode, url, release;

	// Constructeur publique
	// Public constructor
	public ItemData()
	{ }
	//

	// Pour le nom de version
	// For the Version Name
	public String getVersionName()
	{
		return versionName;
	}

	public void setVersionName(String versionName)
	{
		this.versionName = versionName;
	}

	// Pour le code de version
	// For the Version Code
	public String getVersionCode()
	{
		return versionCode;
	}

	public void setVersionCode(String versionCode)
	{
		this.versionCode = versionCode;
	}

	// Pour l'URL - le téléchargement de l'application
	// For the URL - downloading the application
	public String getUrl()
	{
		return url;
	}

	public void setUrl(String url)
	{
		this.url = url;
	}

	// Pour la nouvelle version - Pour les nouvelles fonctionnalités
	// for the new release - For new features
	public String getRelease()
	{
		return release;
	}

	public void setRelease(String release)
	{
		this.release = release;
	}
}
