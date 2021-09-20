package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[add]\ntitle > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		sc.nextLine();
		System.out.println("enter the description");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[del]\nenter the title to be deleted > ");
		
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("deleted");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[edit]\nenter the title to be edited > ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("title doesn't exist");
			return;
		}

		System.out.println("new title > ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("title can't be duplicate");
			return;
		}
		
		sc.nextLine();
		System.out.println("new description > ");
		String new_description = sc.nextLine().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("edited");
			}
		}

	}

	public static void listAll(TodoList l) {
		System.out.println("[list]");
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try {
			FileWriter w = new FileWriter(filename);
			ArrayList<TodoItem> list = l.getList();
			for (TodoItem item : list) {
				w.write(item.toSaveString());
			}
			
			w.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(filename));
			String s;
			while((s = r.readLine()) != null) {
				StringTokenizer token = new StringTokenizer(s, "##");
				TodoItem item = new TodoItem(token.nextToken(), token.nextToken());
				item.setCurrent_date(token.nextToken());
				l.addItem(item);
			}
			
			r.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("todolist.txt 파일이 없습니다.");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
