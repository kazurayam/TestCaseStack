package my

import com.kms.katalon.core.annotation.Keyword

import internal.GlobalVariable

public class TestCaseIdPrinter {

	@Keyword
	static void print() {
		println "top-level TestCaseId : ${GlobalVariable.CURRENT_TESTCASE_ID}"
		//
		Stack<String> tcStack = (Stack<String>)GlobalVariable.TESTCASE_STACK
		if (!tcStack.empty()) {
			println "callee TestCaseId    : ${tcStack.peek()}"
			if (tcStack.size() == 1) {
				println "parent caller was    : ${GlobalVariable.CURRENT_TESTCASE_ID}"
			} else {
				String popped = tcStack.pop()
				println "parent caller was    : ${tcStack.peek()}"
				tcStack.push(popped)   // push the peek back
			}
			
		}
		
	}
}
