package OrdinalStatisticTree;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


public class Main {
	public static double max;
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		OrdinalStatisticRedBlackBTree tree = new OrdinalStatisticRedBlackBTree();
//		for (int i = 0; i < 10; i++) {
//			tree.insert(i);
//			tree.print();
//			System.out.println("---------------------");
//		}
		while(true) {
			System.out.println("1 - insert key\n2 - print tree\n3 - delete\n4 - get key by index\n5 - check is element in tree");
			int choose=scan.nextInt();
			switch (choose) {
			case 1:
				int key = scan.nextInt();
				tree.insert(key);
				break;
			case 2:
				tree.print();
				break;
			case 3:
				double key1 = scan.nextDouble();
				try {
					tree.delete(key1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case 4:
				int index = scan.nextInt();
				RB_Node result=null;
				try {
					result = tree.getKeyByIndex(index);
				} catch (Exception e) {
					//e.printStackTrace();
				}
				
				if (result==null) {
					System.out.println("Index of bound exception");
				}else {
					System.out.println(result.getKey());
				}
				break;
			case 5:
				double keyForSearch = scan.nextDouble();
				RB_Node result1=null;
				try {
					result1 = tree.search(keyForSearch);
				} catch (Exception e) {
					// TODO: handle exception
				}
				
				if (result1==null) {
					System.out.println("Missing");
				}else {
					System.out.println("Within");
				}
				break;
			default:
				break;
			}
		}

	}
	
}
