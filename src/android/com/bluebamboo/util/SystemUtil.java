package com.bluebamboo.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;


public class SystemUtil{

	private Context context;
	public FileOperation fe;
	
	public SystemUtil(Context context){
		this.context = context;
		fe = new FileOperation(context);
	}
	
	//check services is running
	public boolean isWorkedService(String servicename) {
		ActivityManager manager=(ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
		ArrayList<RunningServiceInfo> runningService = (ArrayList<RunningServiceInfo>) manager.getRunningServices(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).service.getClassName().toString().equals(servicename)) {
				return true;
			}
		}
		return false;
	}
	
	//check  activity is running
	public  boolean isWorkedActivity(String activityname) {
		ActivityManager myManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
		ArrayList<RunningTaskInfo> runningService = (ArrayList<RunningTaskInfo>) myManager.getRunningTasks(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).baseActivity.getClassName().toString().equals(activityname)){
				return true;
			}
		}
		return false;
	}
	
	public void closeActivity(String activityname){
		ActivityManager myManager = (ActivityManager)context.getSystemService(context.ACTIVITY_SERVICE);
		ArrayList<RunningTaskInfo> runningService = (ArrayList<RunningTaskInfo>) myManager.getRunningTasks(30);
		for (int i = 0; i < runningService.size(); i++) {
			if (runningService.get(i).baseActivity.getClassName().toString().equals(activityname)){
				myManager.restartPackage(activityname);
			}
		}

	}
	
	//create dir
	public boolean creatDir(String dirname){
		 File sdDir = null; 
		 String sdroot = null;
		 boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);   //�ж�sd���Ƿ����� 
		 if(sdCardExist)   
		 {                               
			 sdDir = Environment.getExternalStorageDirectory();//��ȡ��Ŀ¼ 
		 }   
		 
		 if(sdDir != null){
			 sdroot = sdDir.toString();
		 }
		 
		 File newdir = new File(sdroot + "/" + dirname);
		// Log.i("dir name===",newdir.toString());
		 if(!newdir.exists()){
			 Log.i("makdir====","make dir");
			 newdir.mkdir();
			 return true;//create succes
		 }else{
			 return false;
		 }
	}
	
	//����sd����Ŀ¼
	public String getSdRoot(){
		 File sdDir = null; 
		 String sdroot = null;
		 boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);   //�ж�sd���Ƿ����� 
		 if(sdCardExist)   
		 {                               
			 sdDir = Environment.getExternalStorageDirectory();//��ȡ��Ŀ¼ 

		 }   
		 
		 if(sdDir != null){
			 sdroot = sdDir.toString();
		 }
		 return sdroot;
	}
	
	//read all file dir
	public String[] getAllFile(String dirname){
		File file = new File(dirname);
		if (file.exists()){
			String filelist[] = file.list();
			return filelist;
		}else{
			return null;
		}
	}
	
	public String getNandRoot(){
		  File path = Environment.getDataDirectory();    
	      return path.toString();

	}
	
	//create nand dir
	public boolean creatDirNand(String dirname){
		// File sdDir = null; 
		 String sdroot = getNandRoot();
		 
		 File newdir = new File(sdroot + "/" + dirname);
		 Log.i("dir name===",newdir.toString());
		 if(!newdir.exists()){
			 Log.i("makdir====","make dir");
			 newdir.mkdir();
			 return true;//create succes
		 }else{
			 return false;
		 }
	}
	
	//get image from net work

   public Bitmap getImageFromNetwork(String imageUrl) { 
    	
 		URL myFileUrl = null; 
		Bitmap bitmap = null; 
		HttpURLConnection conn = null;
		try { 
			myFileUrl = new URL(imageUrl); 
		} catch (MalformedURLException e) { 
			e.printStackTrace(); 
		} 
		try {
			
			conn = (HttpURLConnection) myFileUrl.openConnection(); 
			conn.setDoInput(true); 
				conn.connect(); 
				InputStream is = conn.getInputStream(); 
				bitmap = BitmapFactory.decodeStream(is); 
				is.close();
				conn.disconnect();
				
		} catch (IOException e) {
			e.printStackTrace(); 
			bitmap = null;
		}finally{
			if(conn != null){
				conn.disconnect();
				conn = null;
			}
		}
		return bitmap; 
	}
   
   //get image from local file
   public Bitmap getImageFromLocalFile(String filename){
	   try{
		   File fl = context.getFileStreamPath( filename );
		   if(fl.exists()){
			   Bitmap bm = BitmapFactory.decodeFile(fl.getAbsolutePath());
			 //  Log.i("bitmap path", fl.getAbsolutePath() + "\\" + filename);
			   return bm;
		   }else{
			   return null;
		   }
		   
	   }catch(Exception e){
		//   e.printStackTrace();
		   return null;
	   }
   }
   
   
   //load remote page
   
   public String downloadPageFile(String url){
	   BufferedReader in = null;
	   URLConnection connect = null;
	   DataInputStream dis = null;
	   try{
		   URL newUrl=new URL(url);
		   connect=newUrl.openConnection();
		   connect.setRequestProperty ("Content-type","application/x-www-form-urlencoded");
		   connect.connect();
		   
		   dis=new DataInputStream(connect.getInputStream());
		   in = new BufferedReader(new InputStreamReader(dis,"UTF-8"));
		   String html=null;
		   String readLine=null;
		   while((readLine=in.readLine())!=null)
		   {
			   html=html+readLine;
		   }
		   	in.close();
		   	return html;
		   }catch(MalformedURLException me){
			   //System.out.println("MalformedURLException"+me);
			   me.printStackTrace();
			   return null;
		   }
		   catch(IOException ioe){
			  // System.out.println("ioeException"+ioe);
			   ioe.printStackTrace();
			   return null;
		   }finally{
			   if(in != null){
				   try {
					in.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
			   if(dis != null){
				   try {
					dis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			   }
		   }
   }
   
   //save xml page
   public boolean saveXmlPage(String urla,String filename){
	   InputStream is = null;
	   try{
		   URL url = new URL(urla);
		   URLConnection uc = url.openConnection();
		   uc.setRequestProperty ("Content-type","application/x-www-form-urlencoded");
		   uc.connect();
		   is = uc.getInputStream();
		
		   byte[] buffer = new byte[uc.getContentLength()];
		   int length=0;
		   int file = 0;
		   
		   while((length=is.read())!=-1)
		   { 
			   buffer[file]=(byte)length;   
			   file ++;
	   	   }
		    File fl = context.getFileStreamPath( filename );
			
			if(!fl.exists()){
				FileOutputStream stream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
				stream.write(buffer);
				stream.flush();
				stream.close();
				buffer=null;
			}else{
				//already have file
				fl.delete();
				FileOutputStream stream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
				stream.write(buffer);
				stream.flush();
				stream.close();
				buffer=null;
				
			}
		   buffer = null;
		   Log.i("save xml file to local success!",filename);
		   return true;
	   }catch(Exception e){
		   e.printStackTrace();
		   return false;
	   }finally{
		   if(is != null){
			   try {
				is.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		   }
	   }
   }
   
   //save picture to phone
   public boolean savePicturetoLocal(Bitmap bitmap,String filename,String remoteurl){
	  
	   try{
		    File fl = context.getFileStreamPath( filename );
			if(!fl.exists()){
				FileOutputStream stream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
				bitmap.copy(bitmap.getConfig(), false);

		        if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream))
		        {
		         //fos.notifyAll();
		 
		        	stream.flush();
		         
		        	stream.close();
		         
		        //	bitmap.recycle();
		        	Log.i("save picture file to local success","save picture file to local success");
		        	return true;
		        }
		        else
		        {
		        	stream.close();
		         
		        //	bitmap.recycle();
		         
		         return false;
		        }

				
			}else{
				//compare with old file
//				Log.i("picture file exists!","picture file exists!");
//				//compare file
//				if(!fe.comparefileSize(filename, remoteurl) == true){
//					
//					FileOutputStream stream = context.openFileOutput(filename, Context.MODE_WORLD_WRITEABLE);
//					bitmap.copy(bitmap.getConfig(), false);
//
//			        if(bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream))
//			        {
//			         //fos.notifyAll();
//			 
//			        	stream.flush();
//			         
//			        	stream.close();
//			         
//			        //	bitmap.recycle();
//			        	Log.i("save picture file to local success","save picture file to local success");
//			        	return true;
//			        }
//			        else
//			        {
//			        	stream.close();
//			         
//			        //	bitmap.recycle();
//			         
//			        	return false;
//			        }
//
//				}
			}
		   return true;
	   }catch(Exception e){
		   e.printStackTrace();
		   return false;
	   }
   }
   
   
   
   
   //get last modify 
   public long getFileLength(String url){
	   try{
		   URLConnection connect = null;
		   URL newUrl=new URL(url);
		   connect=newUrl.openConnection();
		   connect.setRequestProperty ("Content-type","application/x-www-form-urlencoded");
		   connect.connect();
		   return connect.getContentLength();
	   }catch(Exception e){
		   e.printStackTrace();
		   return 0;
	   }
   }
   
   
	
	

}
