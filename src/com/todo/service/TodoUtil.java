package com.todo.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc, category, due_date;
		Scanner sc = new Scanner(System.in);
		
		System.out.print("[add]\ncategory > ");
		category = sc.next();
		sc.nextLine();
		
		System.out.print("title > ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("title can't be duplicate");
			return;
		}
		sc.nextLine();
		System.out.print("enter the description > ");
		desc = sc.nextLine().trim();
		
		System.out.print("enter the due date > ");
		due_date = sc.next();
		sc.nextLine();
		
		TodoItem t = new TodoItem(category, title, desc, due_date);
		list.addItem(t);
		System.out.println("added");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[del]\nenter the item number to be deleted > ");
		
		int num = sc.nextInt();
		int i = 0;
		
		for (TodoItem item : l.getList()) {
			i++;
			if (num == i) {
				System.out.print(item.toString() + "\nDo you want to delete the item listed above? (y/n) > ");
				String answer = sc.next();
				if(answer.equals("y")) {
					l.deleteItem(item);
					System.out.println("deleted");
					break;
				}
				else
					break;
			}
		}
		
		if(num != i)
			System.out.println("The item number does not exist. Please try again.");
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[edit]\nenter the item number to be edited > ");
		int num = sc.nextInt();
		int i = 0;

		for (TodoItem item : l.getList()) {
			i++;
			if (num == i) {
				System.out.println(item.toString());
				
				System.out.println("new title > ");
				String new_title = sc.next().trim();
				if (l.isDuplicate(new_title)) {
					System.out.println("title can't be duplicate");
					return;
				}
				
				sc.nextLine();
				
				String new_category = sc.next().trim();
				sc.nextLine();
				
				System.out.print("new description > ");
				String new_description = sc.nextLine().trim();
				
				System.out.print("new due date > ");
				String new_due_date = sc.next();
				sc.nextLine();
				
				TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date);
				l.editItem(item, t);
				
				System.out.println("edited");
				break;
			}
		}
		
		if(num != i)
			System.out.println("The item number does not exist. Please try again.");

	}

	public static void listAll(TodoList l) {
		int num = 0;
		
		System.out.println("[list, " + l.getItemTotalNumber() + " items total]");
		for (TodoItem item : l.getList()) {
			num++;
			System.out.println(num + ". " + item.toString());
		}
	}
	
	public static void listCategory(TodoList l) {
		HashSet<String> category = new HashSet<>();
		int i = 0;
		for(TodoItem item : l.getList()) {
			category.add(item.getCategory());
		}
		for(String cate : category) {
			i++;
			System.out.println("- " + cate);
		}
		System.out.println("총 " + i + "개의 카테고리 등록되어 있습니다.");
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
				TodoItem item = new TodoItem(token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken());
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
	
	public static void findItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		String keyword = sc.next();
		int num = 0;
		int i = 0;
		
		for (TodoItem item : l.getList()) {
			num++;
			if(item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {
				System.out.println(num + ". " + item.toString());
				i++;
			}
		}
		
		System.out.println("총 " + i + "개의 항목을 찾았습니다.");
	}
	
	public static void findCategory(TodoList l) {
		Scanner sc = new Scanner(System.in);
		String category = sc.next();
		int num = 0;
		int i = 0;
		
		for (TodoItem item : l.getList()) {
			num++;
			if(item.getCategory().contains(category)) {
				System.out.println(num + ". " + item.toString());
				i++;
			}
		}
		System.out.println("총 " + i + "개의 항목을 찾았습니다.");
	}
}
