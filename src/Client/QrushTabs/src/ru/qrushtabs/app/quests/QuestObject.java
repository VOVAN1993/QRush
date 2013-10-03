package ru.qrushtabs.app.quests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import ru.qrushtabs.app.utils.ServerAPI;

import android.graphics.Bitmap;
import android.util.Log;

public class QuestObject {

	public static final String QUEST_PREFIX = "quest";
	public static final int ACTIVE = 1;
	public static final int NOT_ACTIVE = 0;
	public static final int MODERATING = 3;
	public static final int COMPLETED = 2;
	public String questId = "";
	public String deadline = "Ночь";
	public String name = "";
	public String url = "";
	public String prize = "";
	public int state = 0;
	public boolean isDaily = false;
	public int currentDay = 0;
	private QuestContentView questContentView = null;
	public static QuestObject currentQuestObject;
	private static ArrayList<QuestObject> activeQuests = new ArrayList<QuestObject>();
	private static ArrayList<QuestObject> allQuests = new ArrayList<QuestObject>();
	public TasksObject tasksProgress;
	// public static TreeMap<String, TasksObject> tasksProgress = new
	// TreeMap<String, TasksObject>();

	public TasksObject task;

	public static QuestObject parse(JSONObject o) throws NumberFormatException,
			JSONException {
		QuestObject rf = new QuestObject();
		rf.name = o.optString("name");
		rf.url = o.optString("url");
		rf.questId = o.optString("questid");
		rf.prize = o.optString("prize");
		rf.deadline = o.optString("deadline");
		rf.currentDay = o.optInt("currentDay");
		return rf;
	}
	public void setQuestContentView(QuestContentView qcv)
	{
		this.questContentView = qcv;
	}
	public QuestContentView getQuestContentView()
	{
		return this.questContentView;
	}
	public static JSONObject unparseProgress(QuestObject qo)
			throws NumberFormatException, JSONException {
 		JSONObject jo = new JSONObject();
 		jo.put("questId", qo.questId);
 		jo.put("tasks", TasksObject.unparse(qo.tasksProgress));
		return jo;
	}

	public TasksObject getTaskProgress(String questName) {
		if (tasksProgress != null)
			return tasksProgress;
		else
			return null;
	}

	public void putTaskProgress(TasksObject to) {
		tasksProgress = to;
	}

	public static boolean checkScanOnActiveQuests(String code) {
		boolean flag = false;
		for(int i = 0;i<activeQuests.size();i++)
		{
			flag |=activeQuests.get(i).checkScan(code);
			if(flag)
			{
				if(activeQuests.get(i).getQuestContentView()!=null)
				activeQuests.get(i).getQuestContentView().refreshProgress();
			}
		}
		return flag;
			
	}
	
	public static boolean checkRescanOnActiveQuests() {
		boolean flag = false;
		for(int i = 0;i<activeQuests.size();i++)
		{
			flag |=activeQuests.get(i).checkRescan();
			if(flag)
			{
				if(activeQuests.get(i).getQuestContentView()!=null)
				activeQuests.get(i).getQuestContentView().refreshProgress();
			}
		}
		return flag;
			
	}
	public boolean checkScan(String code) {
		if (tasksProgress != null)
			return tasksProgress.checkScan(code);
		else
			return false;
	}

	public boolean checkRescan() {
		if (tasksProgress != null)
			return tasksProgress.checkRescan();
		else
			return false;
	}

	public static void addQuest(QuestObject qo)
	{
		int l = allQuests.size();
		
		for(int i = 0;i<l;i++)
		{
			if(allQuests.get(i).questId.equals(qo.questId))
			{
				Log.d("quest", "conflict, rewrite "+ allQuests.get(i).name +" "+qo.name);
//				activeQuests.remove(i);
//				activeQuests.add(qo);
				allQuests.set(i, qo);
				if(qo.state==QuestObject.ACTIVE)
					addActiveQuest(qo);
				return;
			}
		}
 		allQuests.add(qo);
		if(qo.state==QuestObject.ACTIVE)
			addActiveQuest(qo);
			
	}
	public static void removeQuest(QuestObject qo)
	{
		allQuests.remove(qo);
 		if(qo.state==QuestObject.ACTIVE)
			removeActiveQuest(qo);
			
	}
	public static void setAllQuests(ArrayList<QuestObject> qo)
	{
		allQuests = qo;
 		for(int i = 0;i<qo.size();i++)
			if(allQuests.get(i).state==QuestObject.ACTIVE)
				addActiveQuest(allQuests.get(i));
	}
	public static ArrayList<QuestObject> getAllQuests()
	{
		return allQuests;
	}
	private static void addActiveQuest(QuestObject qo)
	{
		int l = activeQuests.size();
		
		for(int i = 0;i<l;i++)
		{
			if(activeQuests.get(i).questId.equals(qo.questId))
			{
				Log.d("quest", "conflict, rewrite "+ activeQuests.get(i).name +" "+qo.name);
//				activeQuests.remove(i);
//				activeQuests.add(qo);
				activeQuests.set(i, qo);
				return;
			}
		}
		activeQuests.add(qo);
	}
	private static void removeActiveQuest(QuestObject qo)
	{
		 activeQuests.remove(qo);
 	}
	public static QuestObject getActiveQuest(String questId2) {
		for (int i = 0; i < activeQuests.size(); i++)
			if (activeQuests.get(i).questId.equals(questId2))
				return activeQuests.get(i);
		return null;

	}
	
	public static  ArrayList<QuestObject> getActiveQuests() {
		return activeQuests;

	}
	public static void checkTasks() {
		int l = activeQuests.size();
		
		for(int i = 0;i<l;i++)
		{
			if(activeQuests.get(i).tasksProgress==null)
			{
				TasksObject tasks;
				try {
					tasks = ServerAPI.getQuestTasks(ServerAPI.getServerURL() + activeQuests.get(i).url+"tasks.json");
					activeQuests.get(i).tasksProgress = tasks;
					if(activeQuests.get(i).getQuestContentView()!=null)
					    activeQuests.get(i).getQuestContentView().refreshProgress(); 
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		}
		return;
		
	}
	public boolean canComplete() {
		return tasksProgress.canComplete();
	 
	}

}
