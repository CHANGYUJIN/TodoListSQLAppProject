package com.todo.menu;
public class Menu {

    public static void displaymenu()
    {
        System.out.println();
        System.out.println("<TodoList commands>");
        System.out.println("add - add an item");
        System.out.println("del - delete an item");
        System.out.println("edit - edit an item");
        System.out.println("ls - list all the items");
        System.out.println("ls_name_asc - sort items by name in ascending order");
        System.out.println("ls_name_desc - sort items by name in descending order");
        System.out.println("ls_date - sort items by date");
        System.out.println("exit - exit the program");
    }
    
    public static void prompt() {
    	System.out.print("\ncommand > ");
    }
}
