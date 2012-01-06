package oliver.test.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class FastFailedTest {

	@Test
	public void test1() {
//		List<String> list = new ArrayList<String>();
//		for (int i = 0; i < 10; i++) {
//			list.add("list_" + i);
//		}
//		for (String str : list) {
//			list.remove(str);
//		}
	}
	@Test
	public void test2() {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			list.add("list_" + i);
		}
		int iCount = list.size()-1;
		for (String str : list) {
			System.out.println(str);
			if(iCount==1){
				list.remove(str);
			}
			iCount--;
			
		}
		System.out.println(list.size());
	}

}
