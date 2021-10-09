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
		due_date = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(category, title, desc, due_date, 0);
		if(list.addItem(t) > 0) {
			System.out.println("added");
		}
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[del]\nenter the item number to be deleted > ");
		
		int num = sc.nextInt();
		
		System.out.print("\nAre you sure you want to delete the item listed above? (y/n) > ");
		String answer = sc.next();
		if(answer.equals("y")) {
			if(l.deleteItem(num) > 0){
				System.out.println("deleted");
			}
			else {
				System.out.println("The item number does not exist. Please try again later.");
			}
		}
	}


	public static void updateItem(TodoList l) {
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[edit]\nenter the item number to be edited > ");
		int num = sc.nextInt();
		
		System.out.println("new category > ");
		String new_category = sc.next();
		sc.nextLine();
		
		System.out.println("new title > ");
		String new_title = sc.nextLine().trim();
//				if (l.isDuplicate(new_title)) {
//					System.out.println("title can't be duplicate");
//					return;
//				}
		
		System.out.print("new description > ");
		String new_description = sc.nextLine().trim();
		
		System.out.print("new due date > ");
		String new_due_date = sc.nextLine().trim();
		
		System.out.print("new status (1 for completed, otherwise 0) > ");
		int new_is_completed = sc.nextInt();
		
		TodoItem t = new TodoItem(new_category, new_title, new_description, new_due_date, new_is_completed);
		t.setId(num);
		if(l.updateItem(t)>0) {
			System.out.println("edited");
		}
		else {
			System.out.println("The item number does not exist. Please try again later.");
		}

	}

	public static void listAll(TodoList l, String orderby, int ordering) {
		System.out.printf("[list, total %d items]\n", l.getCount());
		for(TodoItem item : l.getOrderedList(orderby, ordering)) {
			System.out.println(item.toString());
		}
	}
	public static void listAll(TodoList l) {
		int num = 0;
		
		System.out.println("[list, " + l.getCount() + " items total]");
		for (TodoItem item : l.getList()) {
			num++;
			System.out.println(num + ". " + item.toString());
		}
	}
	
	public static void listAll(TodoList l, int comp) {
		int num = 0;
		
		for (TodoItem item : l.getList(comp)) {
			num++;
			System.out.println(item.getId() + ". " + item.toString());
		}
		System.out.println("[list, " + num + " items total]");
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
		System.out.println("total " + i + " category/categories added.");
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
	
//	public static void loadList(TodoList l, String filename) {
//		try {
//			BufferedReader r = new BufferedReader(new FileReader(filename));
//			String s;
//			while((s = r.readLine()) != null) {
//				StringTokenizer token = new StringTokenizer(s, "##");
//				TodoItem item = new TodoItem(token.nextToken(), token.nextToken(), token.nextToken(), token.nextToken());
//				item.setCurrent_date(token.nextToken());
//				l.addItem(item);
//			}
//			
////			r.close();
//			
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			System.out.println("todolist.txt file not exist.");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	public static void findList(TodoList l, String keyword) {
		int count = 0;
		for (TodoItem item : l.getList(keyword)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("total %d items found.\n", count);
	}
//	public static void findItem(TodoList l, String keyword) {
//		int num = 0;
//		int i = 0;
//		
//		for (TodoItem item : l.getList()) {
//			num++;
//			if(item.getTitle().contains(keyword) || item.getDesc().contains(keyword)) {
//				System.out.println(num + ". " + item.toString());
//				i++;
//			}
//		}
//		
//		System.out.println("총 " + i + "개의 항목을 찾았습니다.");
//	}
	
	public static void listCateAll(TodoList l) {
		int count = 0;
		for (String item : l.getCategories()) {
			System.out.print(item + " ");;
			count++;
		}
		System.out.printf("\ntotal %d categories added.\n", count);
	}
	
	public static void findCateList(TodoList l, String cate) {
		int count = 0;
		for (TodoItem item : l.getListCategory(cate)) {
			System.out.println(item.toString());
			count++;
		}
		System.out.printf("\ntotal %d items found.\n", count);
	}
	
	public static void completeItem(TodoList l, String num) {
		int id = Integer.parseInt(num);
		int check = 0;
		for (TodoItem item : l.getList()) {
			if(item.getId() == id) {
				check = 1;
				item.setIs_completed(1);
				System.out.println("the item has been completed.");
				break;
			}
		}
		if (check != 1)
			System.out.println("an item with the id does not exist");
	}
	
//	public static void findCategory(TodoList l, String category) {
//		int num = 0;
//		int i = 0;
//		
//		for (TodoItem item : l.getList()) {
//			num++;
//			if(item.getCategory().contains(category)) {
//				System.out.println(num + ". " + item.toString());
//				i++;
//			}
//		}
//		System.out.println("총 " + i + "개의 항목을 찾았습니다.");
//	}
}
