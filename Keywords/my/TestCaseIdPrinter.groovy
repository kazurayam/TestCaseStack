package my

import com.kms.katalon.core.annotation.Keyword

import internal.GlobalVariable

public class TestCaseIdPrinter {

	@Keyword
	static void print() {
		println "top-level TestCaseId : ${GlobalVariable.CURRENT_TESTCASE_ID}"
		//
		Stack<String> tcStack = (Stack<String>)GlobalVariable.TESTCASE_STACK
		String peek = "empty"
		if (!tcStack.empty()) {
			peek = tcStack.peek()
		}
		println "callee TestCaseId    : ${peek}"
	}
}
