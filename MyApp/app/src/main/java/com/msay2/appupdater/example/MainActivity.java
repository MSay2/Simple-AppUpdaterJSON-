/*
 By Yoann Meclot (MSay2)
*/

package com.msay2.appupdater.example;

import android.app.*;
import android.os.*;

import com.msay2.appupdater.example.item_data.ItemData;

import android.support.v7.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.net.URLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;
import android.net.Uri;
import android.content.Intent;
import android.content.DialogInterface;
import android.widget.Button;
import android.widget.Toast;
import android.widget.TextView;
import android.view.View;
import android.view.LayoutInflater;

public class MainActivity extends AppCompatActivity 
{
	private AsyncTask<String, Void, Integer> getUpdate;
	private List<ItemData> itemsData;
	private ItemData item;
	private ProgressDialog progressDialog;
	private AlertDialog dialog;
	
	// FR - Cette ligne est pour le code version de votre application
	// US - This line is for the code version of your application
	public static final int version = 1;
	
	public static final int DIALOG_DOWNLOAD_PROGRESS = 1;
	public static final String TAG = "MainActivity";
	public static final String URL_JSON = "https://raw.githubusercontent.com/msay2/Simple-AppUpdaterJSON-/master/update.json";
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Button btn = (Button)findViewById(R.id.mainButton1);
		btn.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				// Méthode de mise à jour
				// Method of update
				getVersion();
			}
		});
    }
	
	// Méthode de mise à jour
	// Method of update
	private void getVersion()
	{
		getUpdate = new AsyncTask<String, Void, Integer>()
		{
			@Override
			protected void onPreExecute() 
			{
				// Ignorez cette méthode -> onPreExecute()
				// Ignore this method -> onPreExecute()
			}

			@Override
			protected Integer doInBackground(String... params)
			{
				Integer result = 0;
				HttpURLConnection urlConnection;
				try
				{
					// Cette ligne est pour le lien du fichier JSON
					// This line is for the JSON file link
					URL url = new URL(URL_JSON);
					
					urlConnection = (HttpURLConnection) url.openConnection();
					int statusCode = urlConnection.getResponseCode();

					if (statusCode == 200)
					{
						BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
						StringBuilder response = new StringBuilder();
						String line;

						while ((line = r.readLine()) != null)
						{
							response.append(line);
						}

						try 
						{
							JSONObject obj = new JSONObject(response.toString());
							item = new ItemData();
							item.setVersionName(obj.optString("versionName"));
							item.setVersionCode(obj.optString("versionCode"));
							item.setUrl(obj.optString("url"));
							JSONArray posts = obj.optJSONArray("release");
							itemsData = new ArrayList<>();
							if (posts != null)
							{
								StringBuilder builder = new StringBuilder();

								for (int i = 0; i < posts.length(); ++i) 
								{
									builder.append(posts.getString(i).trim());
									if (i != posts.length() - 1)
									{
										builder.append(System.getProperty("line.separator"));
									}
								}
								item.setRelease(String.format(getResources().getString(R.string.updater_available_description), item.getVersionName().toString()) + "\n" + builder.toString());
							}
							itemsData.add(item);
						}
						catch (JSONException e)
						{
							//Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_LONG).show();
							e.printStackTrace();
						}
						result = 1;
					}
					else 
					{
						result = 0;
					}
				} 
				catch (Exception e)
				{
					Log.d(TAG, e.getLocalizedMessage());
				}
				return result;
			}

			@Override
			protected void onPostExecute(Integer result)
			{
				if (result == 1) 
				{
					// Vous devez ecrire cette ligne (c'est très important, s'il vous plaît ecrivez cette ligne)
					// You have to write this line (this is very important, please write this line)
					int foo = 0;
					
					String text = item.getVersionCode().toString();
					String url = item.getUrl().toString();
					String release = item.getRelease().toString();

					// Cette ligne est pour convertir le String en Int
					// This line is for converting the String to Int
					foo = Integer.parseInt(text);

					// Cette ligne est le comparateur des deux nombres et voir qui est le plus grand ou plus petit
					// This line is the comparator of the two numbers and see which is larger or smaller
					if (version < foo)
					{
						// Cette ligne est pour afficher le dialogue de mise à jour disponible
						// This line is for displaying the available update dialog
						dialogYesUpdate(url, release);
					}
					else
					{
						// Cette ligne est pour afficher le dialogue de mise à jour non disponible
						// // This line is for displaying the update dialog not available
						dialogNoUpdate();
					}
				} 
				else 
				{
					// Cette ligne est pour une éventuelle erreur
					// This line is for any error
					Toast.makeText(MainActivity.this, "Érreur, un problème est survenu", Toast.LENGTH_LONG).show();
				}
			}

		}.execute();
	}
	
	// Dialogue pour la mise à jour et le choix des options Télécharger ou Annuler
	// Dialog for updating and choosing options Download or Cancel
	private void dialogYesUpdate(final String url, final String content_release)
	{
		// Dialogue personnalisée :D
		// This is custom dialog :D
		View view = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_update, null);

		TextView title = (TextView)view.findViewById(R.id.id_title);
		TextView content = (TextView)view.findViewById(R.id.id_content_text);

		title.setText(getResources().getString(R.string.updater_available_title));
		content.setText(content_release);

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
		    .setView(view)
		    .setCancelable(true)
		    .setPositiveButton(getResources().getString(R.string.updater_btn_download), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int position)
				{
					// Ligne d'execution pour le téléchargement
					// Execution line for download
					new DownloadAsyntask().execute(url);
				}
			})
		    .setNegativeButton(getResources().getString(R.string.updater_btn_back), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int position)
				{ }
			});

		dialog = builder.create();
		dialog.show();
	}
	
	// Dialogue pour aucune mise à jour disponible 
	// Dialog for no update available
	private void dialogNoUpdate()
	{
		// Dialogue personnalisée :D
		// This is custom dialog :D
		View view = MainActivity.this.getLayoutInflater().inflate(R.layout.dialog_update, null);

		TextView title = (TextView)view.findViewById(R.id.id_title);
		TextView content = (TextView)view.findViewById(R.id.id_content_text);

		title.setText(getResources().getString(R.string.updater_not_available_title));
		content.setText(String.format(getResources().getString(R.string.updater_not_available_description), getResources().getString(R.string.app_name)));

		AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
		    .setView(view)
		    .setCancelable(true)
		    .setPositiveButton(getResources().getString(R.string.updater_btn_ok), new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface dialog, int position)
				{ }
			});

		dialog = builder.create();
		dialog.show();
	}
	
	@Override
    protected ProgressDialog onCreateDialog(int id) 
	{
        switch (id)
		{
			case DIALOG_DOWNLOAD_PROGRESS:
				progressDialog = new ProgressDialog(this);
				progressDialog.setMessage(getResources().getString(R.string.updater_downloading));
				progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
				progressDialog.setCancelable(false);
				progressDialog.dismiss();
				progressDialog.show();
				return progressDialog;

			default:
				return null;
        }
    }
	
	// Cette classe est pour le téléchargement de l'application (AsyncTast)
	// This class is for downloading the application (AsyncTask)
	class DownloadAsyntask extends AsyncTask<String, String, String>
	{
		@Override
		protected void onPreExecute() 
		{
			super.onPreExecute();
			
			// Affiche votre dialogue de progression
			// Show your progress dialog
			showDialog(DIALOG_DOWNLOAD_PROGRESS);
		}

		@Override
		protected String doInBackground(String... aurl)
		{
			int count;
			try
			{
				URL url = new URL(aurl[0]);
				URLConnection conexion = url.openConnection();
				conexion.connect();

				int lenghtOfFile = conexion.getContentLength();
				Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

				InputStream input = new BufferedInputStream(url.openStream());
				
				// Ceci est pour nommer ou renommer le APK téléchargée, changez-le pour mettre le nom de votre application
				// This is to name or rename the downloaded APP, change it to put the name of your application
				OutputStream output = new FileOutputStream("/sdcard/myapp.apk");

				byte data[] = new byte[1024];

				long total = 0;

				while ((count = input.read(data)) != -1) 
				{
					total += count;
					publishProgress(""+(int)((total*100)/lenghtOfFile));
					output.write(data, 0, count);
				}

				output.flush();
				output.close();
				input.close();
			} 
			catch (Exception e)
			{ }

			return null;
		}

		protected void onProgressUpdate(String... progress) 
		{
			Log.d("ANDRO_ASYNC",progress[0]);
			
			// Ceci est pour adapter la barre de progression à la progression du téléchargement
			// This is to adapt the progress bar to the progress of the download
			progressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		@Override
		protected void onPostExecute(String unused) 
		{
			// Cachez ou enlevez votre dialogue de progression
			// Hide or remove your progress dialog
			dismissDialog(DIALOG_DOWNLOAD_PROGRESS);

			Intent intent = new Intent(Intent.ACTION_VIEW);
			
			// Ceci est pour chercher l'application dans votre mémoire
			// This is to look for the application in your memory
			File file = new File("/mnt/sdcard/myapp.apk");
			
			intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
			
			// Ceci est pour installer l'application directement après l'avoir téléchargée
			// This is to install the application directly after downloading it
			startActivity(intent);
		}
	}
}
