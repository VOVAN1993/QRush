package ru.qrushtabs.app.utils;

import java.util.PriorityQueue;
import java.util.Queue;

import android.os.AsyncTask;

public class NobbTaskQueue 
{
	private static Queue<AsyncTask> queue = new PriorityQueue<AsyncTask>();
	public static void add(AsyncTask task)
	{
		queue.add(task);
	}
}
