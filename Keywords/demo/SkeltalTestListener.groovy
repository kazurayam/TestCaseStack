package demo

import com.kms.katalon.core.annotation.AfterTestCase
import com.kms.katalon.core.annotation.BeforeTestCase
import com.kms.katalon.core.context.TestCaseContext


class MyTestListener {

	/**
	 * Executes before every test case starts.
	 * @param testCaseContext related information of the executed test case.
	 */
	@BeforeTestCase
	def beforeTestCase(TestCaseContext testCaseContext) {
		println("before TestCase " + testCaseContext.getTestCaseId())
	}

	@AfterTestCase
	def afterTestCase(TestCaseContext testCaseContext) {
		println("after TestCase " + testCaseContext.getTestCaseId())
	}
}
